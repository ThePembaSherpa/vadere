package org.vadere.simulator.projects.dataprocessing.processor;

import org.vadere.annotation.factories.dataprocessors.DataProcessorClass;
import org.vadere.meshing.mesh.gen.PFace;
import org.vadere.meshing.mesh.gen.PHalfEdge;
import org.vadere.meshing.mesh.gen.PVertex;
import org.vadere.meshing.mesh.inter.IIncrementalTriangulation;
import org.vadere.meshing.mesh.inter.IMesh;
import org.vadere.simulator.control.simulation.SimulationState;
import org.vadere.simulator.projects.dataprocessing.ProcessorManager;
import org.vadere.simulator.projects.dataprocessing.datakey.TimestepFaceIdKey;
import org.vadere.state.attributes.processor.AttributesMeshDensityCountingProcessor;
import org.vadere.state.scenario.MeasurementArea;
import org.vadere.state.scenario.Pedestrian;

import java.util.Collection;

@DataProcessorClass(label = "MeshDensityCountingProcessor")
public class MeshDensityCountingProcessor extends DataProcessor<TimestepFaceIdKey, Integer> {

	private final static String propertyNameNumberOfPedestrians = "numberOfPedestrians";
	private MeshProcessor meshProcessor;

	public MeshDensityCountingProcessor() {
		super("meshDensityCounting");
		setAttributes(new AttributesMeshDensityCountingProcessor());
	}

	@Override
	public void init(ProcessorManager manager) {
		super.init(manager);
		this.meshProcessor = (MeshProcessor) manager.getProcessor(getAttributes().getMeshProcessorId());
	}

	private IMesh<PVertex, PHalfEdge, PFace> getMesh() {
		return meshProcessor.getTriangulation().getMesh();
	}

	private MeasurementArea getMeasurementArea() {
		return meshProcessor.getMeasurementArea();
	}

	private IIncrementalTriangulation<PVertex, PHalfEdge, PFace> getTriangulation() {
		return meshProcessor.getTriangulation();
	}

	@Override
	protected void doUpdate(SimulationState state) {
		Collection<Pedestrian> peds = state.getTopography().getElements(Pedestrian.class);

		// reset count
		for(PFace f : getMesh().getFaces()) {
			getMesh().setIntegerData(f, propertyNameNumberOfPedestrians, 0);
		}

		// compute count
		for(Pedestrian ped : peds) {

			if(getMeasurementArea().asPolygon().contains(ped.getPosition())) {
				PFace f = getTriangulation().locate(ped.getPosition(), ped).get();
				int n = getMesh().getIntegerData(f, propertyNameNumberOfPedestrians) + 1;
				getMesh().setIntegerData(f, propertyNameNumberOfPedestrians, n);
				assert !getMesh().isBoundary(f);
			}
		}

		// write count
		int faceId = 1;
		for(PFace f : getMesh().getFaces()) {
			int n = getMesh().getIntegerData(f, propertyNameNumberOfPedestrians);
			this.putValue(new TimestepFaceIdKey(state.getStep(), faceId), n);
			faceId++;
		}
	}

	@Override
	public AttributesMeshDensityCountingProcessor getAttributes() {
		if(super.getAttributes() == null) {
			setAttributes(new AttributesMeshDensityCountingProcessor());
		}
		return (AttributesMeshDensityCountingProcessor)super.getAttributes();
	}
}
