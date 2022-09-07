package org.vadere.state.scenario.distribution.impl;

import org.apache.commons.math3.random.RandomGenerator;
import org.vadere.state.attributes.Attributes;
import org.vadere.state.attributes.distributions.AttributesEmpiricalDistribution;
import org.vadere.state.scenario.distribution.VDistribution;
import org.vadere.state.scenario.distribution.registry.RegisterDistribution;

/**
 * @author Aleksandar Ivanov(ivanov0@hm.edu), Lukas Gradl (lgradl@hm.edu)
 */
@RegisterDistribution(name = "empirical", parameter = AttributesEmpiricalDistribution.class)
public class EmpiricalDistribution extends VDistribution<AttributesEmpiricalDistribution> {
	private Attributes empiricalAttributes;
	private org.apache.commons.math3.random.EmpiricalDistribution distribution;

	public EmpiricalDistribution(){
		// Do not remove this constructor. It is us used through reflection.
		super();
		this.empiricalAttributes = new AttributesEmpiricalDistribution();
	}
	public EmpiricalDistribution(AttributesEmpiricalDistribution parameter,RandomGenerator randomGenerator)
	        throws Exception {
		super(parameter, randomGenerator);
	}

	@Override
	protected void setValues(AttributesEmpiricalDistribution parameter, RandomGenerator randomGenerator) {
		distribution = new org.apache.commons.math3.random.EmpiricalDistribution(randomGenerator);
		this.empiricalAttributes = parameter;
	}
	@Override
	public double getNextSpawnTime(double timeCurrentEvent) {
		return timeCurrentEvent + distribution.sample();
	}

}
