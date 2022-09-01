package org.vadere.state.scenario.distribution.parameter;


import org.vadere.state.attributes.distributions.AttributesDistribution;

/**
 * This is the parameter structure used with a poisson distribution.
 * @author Lukas Gradl (lgradl@hm.edu), Ludwig Jaeck
 */

public class AttributesPoissonDistribution extends AttributesDistribution {
	public double getNumberPedsPerSecond() {
		return numberPedsPerSecond;
	}

	public void setNumberPedsPerSecond(double numberPedsPerSecond) {
		this.numberPedsPerSecond = numberPedsPerSecond;
	}

	Double numberPedsPerSecond;
}
