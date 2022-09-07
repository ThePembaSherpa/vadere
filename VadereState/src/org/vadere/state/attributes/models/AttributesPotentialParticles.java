package org.vadere.state.attributes.models;

import org.vadere.annotation.factories.attributes.ModelAttributeClass;
import org.vadere.state.attributes.Attributes;

/**
 * Provides potential attributes for pedestrians and obstacles in the Social Force Model.
 * 
 */
@ModelAttributeClass
public class AttributesPotentialParticles extends Attributes {

	private final double pedestrianBodyPotential = 2.72;
	private final double pedestrianRecognitionDistance = 0.3;

	private final double obstacleBodyPotential = 20.1;
	private final double obstacleRepulsionStrength = 0.25;

	// Getters...

	public double getPedestrianBodyPotential() {
		return pedestrianBodyPotential;
	}

	public double getPedestrianRecognitionDistance() {
		return pedestrianRecognitionDistance;
	}

	public double getObstacleBodyPotential() {
		return obstacleBodyPotential;
	}

	public double getObstacleRepulsionStrength() {
		return obstacleRepulsionStrength;
	}
}
