package org.vadere.simulator.models.osm.updateScheme;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.vadere.simulator.models.osm.PedestrianOSM;
import org.vadere.simulator.models.osm.opencl.CLOptimalStepsModel;
import org.vadere.state.attributes.models.AttributesPotentialCompact;
import org.vadere.state.scenario.Pedestrian;
import org.vadere.state.scenario.Topography;
import org.vadere.util.io.ListUtils;
import org.vadere.util.opencl.OpenCLException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Benedikt Zoennchen
 */
public class UpdateSchemeCLParallel extends UpdateSchemeParallel {

	private CLOptimalStepsModel clOptimalStepsModel;

	private int counter = 0;
	private Logger logger = LogManager.getLogger(UpdateSchemeCLParallel.class);

	public UpdateSchemeCLParallel(@NotNull final Topography topography, @NotNull final CLOptimalStepsModel clOptimalStepsModel) {
		super(topography);
		this.clOptimalStepsModel = clOptimalStepsModel;
	}

	/*
	pedestrian.setTimeCredit(pedestrian.getTimeCredit() + timestamp);
		pedestrian.setDurationNextStep(pedestrian.getStepSize() / pedestrian.getDesiredSpeed());

		if (pedestrian.getTimeCredit() > pedestrian.getDurationNextStep()) {
			pedestrian.updateNextPosition();
			movedPedestrians.add(pedestrian);
		}
	 */

	@Override
	public void update(double timeStepInSec, double currentTimeInSec) {
		try {
			movedPedestrians.clear();
			List<PedestrianOSM> pedestrianOSMList = ListUtils.select(topography.getElements(Pedestrian.class), PedestrianOSM.class);
			// CallMethod.SEEK runs on the GPU

			List<CLOptimalStepsModel.PedestrianOpenCL> pedestrians = new ArrayList<>();

			double maxStepSize = -1.0;
			for(int i = 0; i < pedestrianOSMList.size(); i++) {
				PedestrianOSM pedestrianOSM = pedestrianOSMList.get(i);
				CLOptimalStepsModel.PedestrianOpenCL pedestrian = new CLOptimalStepsModel.PedestrianOpenCL(
						pedestrianOSM.getPosition(),
						(float)pedestrianOSM.getStepSize());
				pedestrians.add(pedestrian);
				maxStepSize = Math.max(maxStepSize, pedestrianOSM.getStepSize());
			}

			double cellSize = new AttributesPotentialCompact().getPedPotentialWidth() + maxStepSize;
			List<CLOptimalStepsModel.PedestrianOpenCL> result = clOptimalStepsModel.getNextSteps(pedestrians, cellSize);

			for(int i = 0; i < pedestrians.size(); i++) {
				//logger.info("not equals for index = " + i + ": " + result.get(i).position + " -> " + result.get(i).newPosition);
				PedestrianOSM pedestrian = pedestrianOSMList.get(i);
				pedestrian.clearStrides();

				pedestrian.setTimeCredit(pedestrian.getTimeCredit() + timeStepInSec);
				pedestrian.setDurationNextStep(pedestrian.getStepSize() / pedestrian.getDesiredSpeed());

				if (pedestrian.getTimeCredit() > pedestrian.getDurationNextStep()) {
					pedestrian.setNextPosition(result.get(i).newPosition);
					movedPedestrians.add(pedestrian);
				}
			}

			// these call methods run on the CPU
			CallMethod[] callMethods = {CallMethod.MOVE, CallMethod.CONFLICTS, CallMethod.STEPS};
			List<Future<?>> futures;

			for (CallMethod callMethod : callMethods) {
				futures = new LinkedList<>();
				for (final PedestrianOSM pedestrian : pedestrianOSMList) {
					Runnable worker = () -> update(pedestrian, timeStepInSec, currentTimeInSec, callMethod);
					futures.add(executorService.submit(worker));
				}
				collectFutures(futures);
			}

			counter++;

		} catch (OpenCLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void updateParallelConflicts(@NotNull final PedestrianOSM pedestrian) {
		pedestrian.refreshRelevantPedestrians();
		super.updateParallelConflicts(pedestrian);
	}
}
