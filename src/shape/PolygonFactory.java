package shape;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 * A factory that produces polygons
 */
public class PolygonFactory implements IShapeFactory {

	/**
	 * The AffineTransform applied to the Polygon
	 */
	private AffineTransform at;
	/**
	 * The amount to scale the polygon by
	 */
	private double scaleFactor;
	/**
	 * A Java Polygon object to be rendered
	 */
	private Polygon poly;

	/**
	 * Creates a polygon with the given points 
	 * @param at			The AffineTransform applied to the Polygon
	 * @param scaleFactor	The amount to scale the polygon by
	 * @param pts 			The points defining the polygon
	 */
	public PolygonFactory(AffineTransform at, double scaleFactor, Point... pts) {
		this.at = at;
		this.scaleFactor = scaleFactor;
		poly = new Polygon();
		for (Point pt : pts) {
			poly.addPoint(pt.x, pt.y);
		}
	}

	/**
	 * Transforms and returns the Polygon
	 */
	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		at.setToTranslation(x, y);
		at.scale(xScale * scaleFactor, yScale * scaleFactor); // optional rotation can be added as well
		return at.createTransformedShape(poly);
	}
}
