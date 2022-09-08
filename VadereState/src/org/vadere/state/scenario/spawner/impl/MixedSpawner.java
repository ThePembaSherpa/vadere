package org.vadere.state.scenario.spawner.impl;

import org.vadere.state.attributes.spawner.AttributesMixedSpawner;
import org.vadere.state.scenario.spawner.VSpawner;

import java.util.List;

public class MixedSpawner extends VSpawner<AttributesMixedSpawner> {

    List<Double> switchpoints;
    List<VSpawner<?>> spawners;

    private int currentInterval = 0;

    @Override
    public int getEventElementCount(double timeCurrentEvent) {
        return getSpawnerByTime(timeCurrentEvent).getEventElementCount(timeCurrentEvent);
    }

    @Override
    public int getRemainingSpawnAgents() {
        return spawners.get(currentInterval).getRemainingSpawnAgents();
    }

    @Override
    public void setRemainingSpawnAgents(int remainingAgents) {
        spawners.get(currentInterval).setRemainingSpawnAgents(remainingAgents);
    }

    @Override
    public void update(double simTimeInSec) {

    }

    @Override
    protected boolean isQueueEmpty() {
        return false;
    }


    private VSpawner<?> getSpawnerByTime(double timeCurrentEvent) {
        while (!(currentInterval > switchpoints.size() - 1) && timeCurrentEvent >= switchpoints.get(currentInterval)
                && !(timeCurrentEvent > switchpoints.get(switchpoints.size() - 1))) {
            currentInterval++;
        }

        return spawners.get(currentInterval);
    }

    public VSpawner<?> getCurrentSpawner() {
        return spawners.get(currentInterval);
    }


}
