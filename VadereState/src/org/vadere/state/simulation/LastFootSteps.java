package org.vadere.state.simulation;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Store the last foot steps of an agent to calculate the agent's average speed during simulation.
 *
 * TODO: Maybe, rename to "FootStepHistory".
 */
public class LastFootSteps {

    // Variables
    private int capacity;
    private ArrayList<FootStep> footSteps;

    // Constructors
    public LastFootSteps(int capacity) {
        this.capacity = capacity;
        this.footSteps = new ArrayList<>(capacity);
    }

    // Getters
    public int getCapacity() { return capacity; }
    public ArrayList<FootStep> getFootSteps() { return footSteps; }

    // Methods
    public boolean add(FootStep footStep) {
        if (footSteps.size() >= capacity) {
            footSteps.remove(0);
        }

        boolean successful = footSteps.add(footStep);

        return successful;
    }

    public double getAverageSpeedInMeterPerSecond() {
        double speed = Double.NaN;

        if (footSteps.size() > 0) {
            // Speed is length divided by time.
            double distance =  footSteps.stream().mapToDouble(footStep -> footStep.length()).sum();
            // This approach works also if "footSteps.size() == 1"
            double time = getYoungestFootStep().getEndTime() - getOldestFootStep().getStartTime();

            speed = distance / time;
        }

        return speed;
    }

    public FootStep getOldestFootStep() {
        FootStep oldestFootStep = null;

        if (footSteps.size() > 0) {
            oldestFootStep = footSteps.get(0);
        }

        return oldestFootStep;
    }

    public FootStep getYoungestFootStep() {
        FootStep youngestFootStep = null;

        if (footSteps.size() > 0) {
            youngestFootStep = footSteps.get(footSteps.size() - 1);
        }

        return youngestFootStep;
    }

    @Override
    public String toString() {
        String footStepPrefix = String.format("Last Footseps (%d): ", footSteps.size());

        String footStepString = footSteps.stream().map(footStep -> footStep.toString()).collect(Collectors.joining(" -> "));

        return footStepPrefix + footStepString;
    }
}
