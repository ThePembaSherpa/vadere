package org.vadere.state.scenario.distribution.parameter;

import org.vadere.state.attributes.distributions.AttributesDistribution;

/**
 * This is the parameter structure used with an empirical distribution.
 * @author Lukas Gradl (lgradl@hm.edu), Ludwig Jaeck
 */

public class AttributesLinearInterpolationDistribution extends AttributesDistribution {

	Double spawnFrequency;
	Double[] xValues;
	Double[] yValues;


	public double getSpawnFrequency() {
		return spawnFrequency;
	}

	public void setSpawnFrequency(double spawnFrequency) {
		this.spawnFrequency = spawnFrequency;
	}


	public Double[] getxValues() {
		return xValues;
	}

	public void setxValues(Double[] xValues) {
		this.xValues = xValues;
	}


	public Double[] getyValues() {
		return yValues;
	}

	public void setyValues(Double[] yValues) {
		this.yValues = yValues;
	}


}
