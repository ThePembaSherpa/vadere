package org.vadere.state.scenario.distribution.parameter;

import org.vadere.state.attributes.distributions.AttributesDistribution;

/**
 * This is the parameter structure used with a negative exponential distribution.
 * @author Lukas Gradl (lgradl@hm.edu), Ludwig Jaeck
 */
public class AttributesNegativeExponentialDistribution extends AttributesDistribution {
	Double mean;

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getMean() {
		return mean;
	}
}
