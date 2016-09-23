package org.vadere.simulator.models.seating;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.Pair;
import org.apache.log4j.Logger;
import org.vadere.simulator.control.ActiveCallback;
import org.vadere.simulator.models.Model;
import org.vadere.simulator.models.seating.trainmodel.Compartment;
import org.vadere.simulator.models.seating.trainmodel.Seat;
import org.vadere.simulator.models.seating.trainmodel.SeatGroup;
import org.vadere.simulator.models.seating.trainmodel.TrainModel;
import org.vadere.state.attributes.Attributes;
import org.vadere.state.attributes.models.AttributesSeating;
import org.vadere.state.attributes.models.seating.SeatFacingDirection;
import org.vadere.state.attributes.models.seating.SeatRelativePosition;
import org.vadere.state.attributes.models.seating.SeatSide;
import org.vadere.state.attributes.scenario.AttributesAgent;
import org.vadere.state.scenario.Pedestrian;
import org.vadere.state.scenario.Topography;
import org.vadere.state.scenario.TrainGeometry;
import org.vadere.util.math.TruncatedNormalDistribution;
import org.vadere.util.reflection.DynamicClassInstantiator;

/**
 * This model can only be used with train scenarios complying with scenarios generated by Traingen.
 * 
 * To enable this model, add this model's class name to the main model's submodel list and
 * load a train topography.
 *
 */
public class SeatingModel implements ActiveCallback, Model {

	private static final int[] SEAT_INDEXES = {0, 1, 2, 3};

	private final Logger log = Logger.getLogger(SeatingModel.class);
	
	private AttributesSeating attributes;
	private TrainModel trainModel;
	private Topography topography;
	private Random random;
	/** Used for distributions from Apache Commons Math. */
	private RandomGenerator rng;

	@Override
	public void initialize(List<Attributes> attributesList, Topography topography,
			AttributesAgent attributesPedestrian, Random random) {
		this.attributes = Model.findAttributes(attributesList, AttributesSeating.class);
		this.topography = topography;
		
		DynamicClassInstantiator<TrainGeometry> instantiator = new DynamicClassInstantiator<>();
		TrainGeometry trainGeometry = instantiator.createObject(attributes.getTrainGeometry());
		// TODO catch exception of TrainModel constructor when train scenario is corrupt
		this.trainModel = new TrainModel(topography, trainGeometry);
		this.random = random;
		this.rng = new JDKRandomGenerator(random.nextInt());
	}

	@Override
	public void preLoop(double simTimeInSec) {
		// before simulation
	}

	@Override
	public void postLoop(double simTimeInSec) {
		// after simulation
	}

	@Override
	public void update(double simTimeInSec) {
		final Collection<Pedestrian> pedestrians = trainModel.getPedestrians();
		
		// choose compartment for those peds without a target
		pedestrians.stream()
				.filter(this::hasNoTargetAssigned)
				.forEach(this::assignCompartmentTarget);
		
		// choose seat group and seat for those peds that wait at their interim target
		pedestrians.stream()
				.filter(this::hasJustReachedItsFirstTarget) // the interim target
				.forEach(this::assignSeatTarget);
	}
	
	private void assignCompartmentTarget(Pedestrian p) {
		final int entranceAreaIndex = trainModel.getEntranceAreaIndexForPerson(p);
//		final Compartment compartment = chooseCompartment(p, entranceAreaIndex); // TODO choose algorithm
		final Compartment compartment = chooseCompartmentByRandom(p, entranceAreaIndex);
		logDebug("Assigning compartment %d to pedestrian %d", compartment.getIndex(), p.getId());
		p.addTarget(compartment.getInterimTarget());
	}

	private void assignSeatTarget(Pedestrian p) {
		final Compartment compartment = trainModel.getCompartment(p);
		final SeatGroup seatGroup = chooseSeatGroup(compartment);
		final Seat seat = chooseSeat(seatGroup);
		logDebug("Assigning seat %d.%d to pedestrian %d", compartment.getIndex(),
				seat.getSeatNumberWithinCompartment(), p.getId());
		p.addTarget(seat.getAssociatedTarget());
	}
	
	private void assignRandomSeat(Pedestrian p) {
		final List<Seat> availableSeats = trainModel.getSeats().stream()
				.filter(Seat::isAvailable)
				.collect(Collectors.toList());
		final Seat seat = drawRandomElement(availableSeats);
		logDebug("Assigning seat %d to pedestrian %d",
				seat.getSeatNumberWithinCompartment(), p.getId());
		p.addTarget(seat.getAssociatedTarget());
	}
	
	private boolean hasNoTargetAssigned(Pedestrian p) {
		return p.getTargets().isEmpty();
	}
	
	private boolean hasJustReachedItsFirstTarget(Pedestrian p) {
		return p.getTargets().size() == 1 && !p.hasNextTarget();
	}

	public TrainModel getTrainModel() {
		return trainModel;
	}

	public Compartment chooseCompartmentByRandom(Pedestrian person, int entranceAreaIndex) {
		int index = random.nextInt(getCompartmentCount() - 1); // 2 half-compartments are counted as one
		if (index == 0 && random.nextBoolean()) // 50 percent chance to switch to other half-compartment
			index = getCompartmentCount() - 1;
		return trainModel.getCompartment(index);
	}

	public Compartment chooseCompartment(Pedestrian person, int entranceAreaIndex) {
		// entrance areas:    0   1   2   3
		// compartments:    0   1   2   3   4
		// left- and rightmost compartments are "half-compartments"
		
		final int entranceAreaCount = trainModel.getEntranceAreaCount();

		final double distributionMean = entranceAreaIndex + 0.5;
		final double distributionSd = entranceAreaCount / 2.0;
		final RealDistribution distribution = new TruncatedNormalDistribution(rng, distributionMean, distributionSd,
				0, entranceAreaCount, 100);

		final double value = distribution.sample();
		final int compartmentIndex = (int) Math.round(value);
		return trainModel.getCompartment(compartmentIndex);
	}
	
	public SeatGroup chooseSeatGroup(Compartment compartment) {
		final List<Pair<Boolean, Double>> valuesAndProbabilities = attributes.getSeatGroupChoice();
		final EnumeratedDistribution<Boolean> distribution = new EnumeratedDistribution<>(rng, valuesAndProbabilities);
		
		List<SeatGroup> seatGroups = compartment.getSeatGroups().stream()
				.filter(sg -> sg.getPersonCount() < 4)
				.collect(Collectors.toList());

		if (seatGroups.isEmpty()) {
			throw new IllegalStateException("No seats available in given compartment.");
		}
		
		while (seatGroups.size() > 1) {
			final int minPersonCount = getSeatGroupMinPersonCount(seatGroups);

			if (allSeatGroupPersonCountsEquals(seatGroups)) {
				return drawRandomElement(seatGroups);
			}

			if (distribution.sample()) {
				// choice for seat group with minimal number of other passengers
				final List<SeatGroup> minSeatGroups = seatGroups.stream()
						.filter(sg -> sg.getPersonCount() == minPersonCount)
						.collect(Collectors.toList());
				return drawRandomElement(minSeatGroups);
			} else {
				seatGroups = seatGroups.stream()
						.filter(sg -> sg.getPersonCount() != minPersonCount)
						.collect(Collectors.toList());
			}
		}
		
		return seatGroups.get(0);
	}

	private boolean allSeatGroupPersonCountsEquals(List<SeatGroup> result) {
		return result.stream().mapToInt(SeatGroup::getPersonCount).distinct().count() == 1;
	}

	private int getSeatGroupMinPersonCount(List<SeatGroup> result) {
		return result.stream()
				.mapToInt(SeatGroup::getPersonCount)
				.min().getAsInt();
	}
	
	private <T> T drawRandomElement(List<T> list) {
		return list.get(random.nextInt(list.size()));
	}

	public Seat chooseSeat(SeatGroup seatGroup) {
		final int personsSitting = seatGroup.getPersonCount();
		switch (personsSitting) {
		case 0:
			return chooseSeat0(seatGroup);

		case 1:
			return chooseSeat1(seatGroup);

		case 2:
			return chooseSeat2(seatGroup);

		case 3:
			return chooseSeat3(seatGroup);

		default:
			assert personsSitting == 4;
			throw new IllegalStateException("Seat group is already full. This method should not have been called!");
		}
	}

	private Seat chooseSeat0(SeatGroup seatGroup) {
		final double[] probabilities = attributes.getSeatChoice0();
		final EnumeratedIntegerDistribution distribution = new EnumeratedIntegerDistribution(rng, SEAT_INDEXES, probabilities);
		return seatGroup.getSeat(distribution.sample());
	}

	private Seat chooseSeat1(final SeatGroup seatGroup) {
		final EnumeratedDistribution<SeatRelativePosition> distribution = new EnumeratedDistribution<>(rng, attributes.getSeatChoice1());
		final SeatRelativePosition relativePosition = distribution.sample();
		return seatGroup.seatRelativeTo(seatGroup.getTheOccupiedSeat(), relativePosition);
	}

	private Seat chooseSeat2(final SeatGroup seatGroup) {
		if (seatGroup.onlySideChoice()) {
			// choice only between window/aisle
			final EnumeratedDistribution<SeatSide> distribution = new EnumeratedDistribution<>(rng, attributes.getSeatChoice2Side());
			SeatSide side = distribution.sample();
			return seatGroup.availableSeatAtSide(side);

		} else if (seatGroup.onlyFacingDirectionChoice()) {
			// choice only between forward/backward
			final EnumeratedDistribution<SeatFacingDirection> distribution = new EnumeratedDistribution<>(rng, attributes.getSeatChoice2FacingDirection());
			SeatFacingDirection facingDirection = distribution.sample();
			return seatGroup.availableSeatAtFacingDirection(facingDirection);

		} else {
			// choice between both window/aisle and forward/backward
			return seatGroup.getTheTwoAvailableSeats().get(random.nextInt(2));
		}
	}

	private Seat chooseSeat3(final SeatGroup seatGroup) {
		return seatGroup.getTheAvailableSeat();
	}

	private void logDebug(String formatString, Object... args) {
		log.debug(String.format(formatString, args));
	}
	
	// TODO test
	private double mapEntranceAreaIndexToRange01(int index) {
		double compartmentIntervalWidth = 1.0 / getCompartmentCount();
		return (index + 1) * compartmentIntervalWidth;
	}
	
	// TODO test
	private int mapRange01ToCompartmentIndex(double value) {
		return (int) Math.floor(value * getCompartmentCount());
	}
	
	private int getCompartmentCount() {
		return trainModel.getEntranceAreaCount() + 1;
	}

}
