package org.vadere.meshing.mesh.triangulation.plots;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.vadere.util.math.IDistanceFunction;
import org.vadere.meshing.mesh.gen.AFace;
import org.vadere.meshing.mesh.gen.AHalfEdge;
import org.vadere.meshing.mesh.gen.AMesh;
import org.vadere.meshing.mesh.gen.AVertex;
import org.vadere.meshing.mesh.inter.IMeshSupplier;
import org.vadere.meshing.mesh.inter.ITriangulation;
import org.vadere.util.geometry.shapes.VRectangle;
import org.vadere.util.geometry.shapes.VShape;
import org.vadere.meshing.mesh.inter.IPointConstructor;
import org.vadere.meshing.mesh.triangulation.adaptive.IEdgeLengthFunction;
import org.vadere.meshing.mesh.triangulation.improver.EikMeshPoint;
import org.vadere.meshing.mesh.triangulation.improver.EikMeshPanel;
import org.vadere.meshing.mesh.triangulation.triangulator.UniformRefinementTriangulatorSFC;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * Created by bzoennchen on 15.03.18.
 */
public class SierpinskyPlot {
	private static final Logger log = LogManager.getLogger(RunTimeCPU.class);

	/**
	 * Each geometry is contained this bounding box.
	 */
	private static final VRectangle bbox = new VRectangle(-11, -11, 22, 22);
	private static IEdgeLengthFunction uniformEdgeLength = p -> 1.0;
	private static IPointConstructor<EikMeshPoint> pointConstructor = (x, y) -> new EikMeshPoint(x, y, false);
	private static double initialEdgeLength = 0.3;

	/**
	 * A circle with radius 10.0 meshed using a uniform mesh.
	 */
	private static void uniformCircle(final double initialEdgeLength) {
		IMeshSupplier<EikMeshPoint, AVertex<EikMeshPoint>, AHalfEdge<EikMeshPoint>, AFace<EikMeshPoint>> supplier = () -> new AMesh<>(pointConstructor);
		IDistanceFunction distanceFunc = p -> Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY()) - 10;
		List<VShape> obstacles = new ArrayList<>();
		IEdgeLengthFunction edgeLengthFunc = p -> 1.0 + (Math.abs(distanceFunc.apply(p)) * Math.abs(distanceFunc.apply(p)));

		UniformRefinementTriangulatorSFC<EikMeshPoint, AVertex<EikMeshPoint>, AHalfEdge<EikMeshPoint>, AFace<EikMeshPoint>> uniformRefinementTriangulation = new UniformRefinementTriangulatorSFC(
				supplier,
				bbox,
				obstacles,
				edgeLengthFunc,
				initialEdgeLength,
				distanceFunc,
				new ArrayList<>());

		ITriangulation<EikMeshPoint, AVertex<EikMeshPoint>, AHalfEdge<EikMeshPoint>, AFace<EikMeshPoint>> triangulation = uniformRefinementTriangulation.init();
		EikMeshPanel<EikMeshPoint, AVertex<EikMeshPoint>, AHalfEdge<EikMeshPoint>, AFace<EikMeshPoint>> panel = new EikMeshPanel<>(triangulation.getMesh(), f -> false, 1000, 800, bbox);
		JFrame frame = panel.display();
		frame.setVisible(true);

		while (!uniformRefinementTriangulation.isFinished()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			uniformRefinementTriangulation.step();
			log.info("step");
			panel.repaint();
		}

		log.info("end");

	}

	public static void main(String[] args) {
		uniformCircle(initialEdgeLength );
	}
}
