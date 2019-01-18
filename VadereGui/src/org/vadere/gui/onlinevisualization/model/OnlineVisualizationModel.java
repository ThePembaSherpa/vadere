package org.vadere.gui.onlinevisualization.model;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.vadere.gui.components.model.DefaultSimulationConfig;
import org.vadere.gui.components.model.SimulationModel;
import org.vadere.gui.onlinevisualization.OnlineVisualization;
import org.vadere.simulator.models.potential.fields.IPotentialField;
import org.vadere.state.scenario.*;
import org.vadere.util.geometry.shapes.VPoint;
import org.vadere.util.voronoi.VoronoiDiagram;

public class OnlineVisualizationModel extends SimulationModel<DefaultSimulationConfig> {

	/**
	 * Lists for thread safe data exchange between main and draw thread.
	 */
	private LinkedList<VoronoiDiagram> voronoiSnapshots;
	private LinkedList<OnlineVisualization.ObservationAreaSnapshotData> observationAreaSnapshots;

	/**
	 * Latest snapshot of the potential field to be displayed. This is a certain
	 * pontetial field of a certain pedestrian. See 'Simulation' for more
	 * information. For debug purposes. Updated by popDrawData().
	 */
	private IPotentialField potentialFieldTarget = null;

	private IPotentialField potentialField = null;

	private Agent agent = null;

	/**
	 * Latest snapshot of the jts diagram to be displayed. Updated by
	 * popDrawData().
	 */
	private VoronoiDiagram voronoiDiagram = null;

	private double simTimeInSec;

	private int simulationId;

	private boolean drawArrows;

	/**
	 * Synchronizer object used to control access to the simulation data
	 * exchange structures to avoid threading issues.
	 */
	private Object drawDataSynchronizer;


	/**
	 * The observation area to display. Updated by popDrawData() with the latest
	 * observation area snapshot.
	 */
	private Topography topography;

	public OnlineVisualizationModel() {
		super(new DefaultSimulationConfig());
		this.drawDataSynchronizer = new Object();
		this.voronoiSnapshots = new LinkedList<>();
		this.observationAreaSnapshots = new LinkedList<>();
	}

	@Override
	public Collection<Agent> getAgents() {
		if (topography == null) {
			return new ArrayList<>();
		}
		Collection<Agent> result = new LinkedList<>();
		result.addAll(topography.getElements(Agent.class));
		return result;
	}

	@Override
	public int getTopographyId() {
		return 0;
	}

	@Override
	public Topography getTopography() {
		return topography;
	}

	@Override
	public Iterator<ScenarioElement> iterator() {
		if (topography == null) {
			return new ArrayList<ScenarioElement>().iterator();
		}
		return new TopographyIterator(topography);
	}

	/**
	 * Retrieve latest simulation data from data exchange structures. As these
	 * structures may be accessed by the main thread at the same time, access is
	 * controlled by drawDataSynchronizer.
	 */
	public boolean popDrawData() {
		synchronized (drawDataSynchronizer) {
			if (observationAreaSnapshots.isEmpty()) {
				return false;
			}

			OnlineVisualization.ObservationAreaSnapshotData observationAreaSnapshot =
					observationAreaSnapshots.getFirst();
			simTimeInSec = observationAreaSnapshot.simTimeInSec;
			simulationId = observationAreaSnapshot.simulationId;

			// potentialFieldTarget might be null!
            potentialFieldTarget = observationAreaSnapshot.potentialFieldTarget;
			potentialField = observationAreaSnapshot.potentialField;
			agent = observationAreaSnapshot.selectedAgent;

			/*
			 * if(topography == null ||
			 * !topography.getBounds().equals(observationAreaSnapshot.scenario.getBounds())) {
			 * setViewportBound(observationAreaSnapshot.scenario.getBounds());
			 * }
			 */

			if (topography == null) {
				topography = observationAreaSnapshot.scenario;
				fireChangeViewportEvent(new Rectangle2D.Double(topography.getBounds().x, topography.getBounds().y,
						topography.getBounds().width, topography.getBounds().height));
			} else {
				topography = observationAreaSnapshot.scenario;
			}

			if (getSelectedElement() instanceof Car) {
				int carId = getSelectedElement().getId();
				Car car = topography.getElement(Car.class, carId);
				setSelectedElement(car);
			} else if (getSelectedElement() instanceof Pedestrian) {
				int pedId = getSelectedElement().getId();
				Pedestrian ped = topography.getElement(Pedestrian.class, pedId);
				setSelectedElement(ped);
			}

			if (isVoronoiDiagramAvailable() && isVoronoiDiagramVisible()) {
				getVoronoiDiagram().computeVoronoiDiagram(topography.getPedestrianDynamicElements().getElements()
								.stream()
								.map(ped -> ped.getPosition())
								.collect(Collectors.toList()));
			}
			return true;
		}
	}

	public int getSimulationId() {
		return simulationId;
	}

	@Override
	public boolean isTopgraphyAvailable() {
		return topography != null;
	}

	public void pushObservationAreaSnapshot(final OnlineVisualization.ObservationAreaSnapshotData observationAreaSnapshotData) {
        if (observationAreaSnapshots.size() > 0) {
            observationAreaSnapshots.pop();
        }
        observationAreaSnapshots.push(observationAreaSnapshotData);
        setChanged();
	}

	public void reset() {
		voronoiSnapshots.clear();
		observationAreaSnapshots.clear();
		selectedElement = null;

		voronoiDiagram = null;
		topography = null;
		simTimeInSec = 0.0;
	}

	/**
	 * Returns the data synchronization object, used for thread safe data
	 * exchange between main thread and draw thread. Ensure that there is no
	 * simultaneous access to the shared data structures.
	 */
	public Object getDataSynchronizer() {
		return drawDataSynchronizer;
	}

	/**
	 * Returns the list of jts diagram snapshots. Used for thread safe data
	 * exchange between main thread and draw thread.
	 */
	public LinkedList<VoronoiDiagram> getVoronoiSnapshots() {
		return voronoiSnapshots;
	}

	@Override
	public Function<VPoint, Double> getPotentialField() {
	    Function<VPoint, Double> f = pos -> 0.0;

	    if(potentialField != null && config.isShowPotentialField() && agent.equals(getSelectedElement())) {
	    	f = pos -> potentialField.getPotential(pos, agent);
	    }
		else if(potentialFieldTarget != null && config.isShowTargetPotentialField()) {
            if(getSelectedElement() instanceof Agent) {
                Agent selectedAgent = (Agent)getSelectedElement();
                f = pos -> potentialFieldTarget.getPotential(pos, selectedAgent);
            }
        }

		return f;
	}

	@Override
	public double getGridResolution() {
		return config.getGridWidth();
	}

	@Override
	public boolean isFloorFieldAvailable() {
		return true;
	}

	public double getSimTimeInSec() {
		return simTimeInSec;
	}
}
