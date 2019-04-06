package org.vadere.meshing.mesh.triangulation.triangulator.gen;

import org.jetbrains.annotations.NotNull;
import org.vadere.meshing.mesh.inter.IFace;
import org.vadere.meshing.mesh.inter.IHalfEdge;
import org.vadere.meshing.mesh.inter.IIncrementalTriangulation;
import org.vadere.meshing.mesh.inter.IMesh;
import org.vadere.meshing.mesh.inter.IMeshSupplier;
import org.vadere.meshing.mesh.inter.IVertex;
import org.vadere.meshing.mesh.triangulation.triangulator.inter.IRefiner;
import org.vadere.util.geometry.GeometryUtils;
import org.vadere.util.geometry.shapes.IPoint;
import org.vadere.util.geometry.shapes.VLine;
import org.vadere.util.geometry.shapes.VPoint;
import org.vadere.util.geometry.shapes.VPolygon;
import org.vadere.util.geometry.shapes.VTriangle;
import org.vadere.util.logging.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Implementation of the Voronoi-vertex point insertion method from [1] (rebay-1993).
 *
 * <ol>
 *     <li>Efficient Unstructured Mesh Generation by Means of Delaunay Triangulation and Bowyer-Watson Algorithm</li>
 * </ol>
 *
 * @param <P> the type of the points (containers)
 * @param <CE> the type of container of the half-edges
 * @param <CF> the type of the container of the faces
 * @param <V> the type of the vertices
 * @param <E> the type of the half-edges
 * @param <F> the type of the faces
 */
public class GenVoronoiVertexInsertion<P extends IPoint, CE, CF, V extends IVertex<P>, E extends IHalfEdge<CE>, F extends IFace<CF>> implements IRefiner<P, CE, CF, V, E, F> {

	private static Logger logger = Logger.getLogger(GenVoronoiVertexInsertion.class);
	private VPolygon bound;
	private final Collection<VPolygon> constrains;
	private final GenConstrainedDelaunayTriangulator<P, CE, CF, V, E, F> cdt;

	// Improvements: maybe mark edges which should not be flipped instead of using a Set is slower.
	private Set<E> segments;

	private boolean initialized;
	private boolean generated;

	// Improvements: use multiple unsorted queues to improve performance
	private PriorityQueue<F> heap;

	// Improvements: maybe save VTriangle inside the face container
	private Map<F, VTriangle> triangles;

	private boolean createHoles;
	private double minRadius;
	private Function<IPoint, Double> circumRadiusFunc;
	private final static int MAX_POINTS = 200_000;
	private DelaunayPlacement<P, CE, CF, V, E, F> placementStrategy;

	public GenVoronoiVertexInsertion(@NotNull final VPolygon bound,
	                                 @NotNull final Collection<VPolygon> constrains,
	                                 @NotNull final IMeshSupplier<P, CE, CF, V, E, F> meshSupplier,
	                                 final boolean createHoles,
	                                 @NotNull Function<IPoint, Double> circumRadiusFunc) {
		this.bound = bound;
		this.constrains = constrains;
		this.segments = new HashSet<>();
		this.initialized = false;
		this.generated = false;
		this.heap = new PriorityQueue<>(new FaceCircumradiusComparator());
		this.triangles = new HashMap<>();
		this.createHoles = createHoles;
		this.minRadius = minRadius;
		this.circumRadiusFunc = circumRadiusFunc;
		List<VLine> lines = generateLines();

		/**
		 * This prevent the flipping of constrained edges
		 */
		Predicate<E> canIllegal = e -> !segments.contains(e) && !segments.contains(getMesh().getTwin(e));
		this.cdt = new GenConstrainedDelaunayTriangulator<>(meshSupplier, GeometryUtils.boundRelative(bound.getPoints(), 10), lines, Collections.EMPTY_SET, canIllegal);
		this.placementStrategy = new DelaunayPlacement<>(cdt.getMesh());
	}

	@Override
	public void refine() {
		if(!initialized) {
			cdt.generate(true);
			segments = new HashSet<>(cdt.getConstrains());
			getTriangulation().getMesh()
					.streamFaces()
					.filter(f -> bound.contains(getMesh().toTriangle(f).midPoint()))
					.forEach(f -> add(f));
			initialized = true;
		}
		else if(!refinementFinished()) {
			F face = heap.poll();

			//logger.info("#points = " + getMesh().getNumberOfVertices());
			//logger.info("" + getMesh().isDestroyed(face));
			//VTriangle triangle = getMesh().toTriangle(face);
			VTriangle triangle = triangles.get(face);
			VPoint circumcenter = this.placementStrategy.computePlacement(getMesh().getEdge(face), triangle);
			Optional<F> locatedFace = locateFace(circumcenter.getX(), circumcenter.getY(), face);

			if(locatedFace.isPresent()) {
				//logger.info("insert" + circumcenter);
				E edge = getTriangulation().splitTriangle(locatedFace.get(), getMesh().createPoint(circumcenter.getX(), circumcenter.getY()));
				V v = getMesh().getVertex(edge);

				// update triangles
				for(F f : getMesh().getFaceIt(v)) {
					VTriangle tri = getTriangulation().getMesh().toTriangle(f);
					if(bound.contains(tri.midPoint())) {
						heap.remove(f);
						add(f);
					}
				}
			}
		} else if(!isFinished()) {
			finish(true);
		} else {
			logger.info("finished");
		}
	}

	private Optional<F> locateFace(final double x, final double y, @NotNull final F face) {
		/*for(F f : getTriangulation().getMesh().getFaces()) {
			VTriangle triangle = getTriangulation().getMesh().toTriangle(f);
			if(triangle.contains(x, y)) {
				return Optional.of(f);
			}
		}
		return Optional.empty();*/
		Optional<F> optFace = getTriangulation().locateFace(x, y, face);
		if(optFace.isPresent() && !getMesh().isBoundary(optFace.get())) {
			return optFace;
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public boolean isFinished() {
		return generated;
	}

	private boolean refinementFinished() {
		return initialized == true && (heap.isEmpty() || getMesh().getNumberOfVertices() >= MAX_POINTS);
	}

	@Override
	public IIncrementalTriangulation generate() {
		return generate(true);
	}

	@Override
	public IIncrementalTriangulation<P, CE, CF, V, E, F> generate(boolean finalize) {
		if(!generated) {
			while (!refinementFinished()) {
				refine();
			}
			finish(finalize);
		}
		return getTriangulation();
	}

	private void finish(boolean finalize) {
		generated = true;
		if(finalize) {
			getTriangulation().finish();
			removeTriangles();
		}
	}

	public void removeTriangles() {
		if(createHoles) {
			for(VPolygon hole : constrains) {
				Predicate<F> mergeCondition = f -> hole.contains(getMesh().toTriangle(f).midPoint());
				Optional<F> optFace = getMesh().streamFaces().filter(mergeCondition).findAny();
				if(optFace.isPresent()) {
					getTriangulation().createHole(optFace.get(), mergeCondition, true);
				}
			}
			Predicate<F> mergeCondition = f -> !bound.contains(getMesh().toTriangle(f).midPoint());
			getTriangulation().shrinkBorder(mergeCondition, true);
		}
	}

	public IIncrementalTriangulation<P, CE, CF, V, E, F> getTriangulation() {
		return cdt.getTriangulation();
	}

	@Override
	public IMesh<P, CE, CF, V, E, F> getMesh() {
		return cdt.getMesh();
	}

	private void add(@NotNull final F face) {
		VTriangle triangle = getMesh().toTriangle(face);
		double radius = triangle.getCircumscribedRadius();
		VPoint circumcenter = triangle.getCircumcenter();
		if(radius > minRadius && triangle.getCircumscribedRadius() > circumRadiusFunc.apply(circumcenter)) {
			triangles.put(face, triangle);
			heap.add(face);
		}
	}

	// TODO duplicated code
	private List<VLine> generateLines() {
		List<VLine> polyLines = new ArrayList<>();

		polyLines.addAll(bound.getLinePath());

		for(VPolygon polygon : constrains) {
			polyLines.addAll(polygon.getLinePath());
		}

		List<VLine> lines = new ArrayList<>();
		for(VLine line : polyLines) {
			List<VLine> splitLines = new ArrayList<>();
			splitLines.add(line);
			while (!splitLines.isEmpty()) {
				List<VLine> newSplitLines = new ArrayList<>();
				for(VLine splitLine : splitLines) {
					VPoint midPoint = splitLine.midPoint();
					double desiredLen = circumRadiusFunc.apply(midPoint) * Math.sqrt(3);
					double len = splitLine.length();
					if(len >  desiredLen) {
						newSplitLines.add(new VLine(splitLine.getVPoint1(), midPoint));
						newSplitLines.add(new VLine(midPoint, splitLine.getVPoint2()));
					} else {
						lines.add(splitLine);
					}
				}
				splitLines = newSplitLines;
			}
		}

		return lines;
	}

	private final class FaceCircumradiusComparator implements Comparator<F> {

		@Override
		public int compare(F o1, F o2) {
			return Double.compare(-triangles.get(o1).getCircumscribedRadius(), -triangles.get(o2).getCircumscribedRadius());
		}
	}
}
