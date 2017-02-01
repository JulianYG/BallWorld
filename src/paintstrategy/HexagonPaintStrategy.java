package paintstrategy;

import java.awt.Point;
import java.awt.geom.AffineTransform;

import shape.PolygonFactory;

/**
 * A paint strategy that draws a hexagon
 */
public class HexagonPaintStrategy extends ShapePaintStrategy {

	/**
	 * No parameter constructor that creates a prototype rectangle that has
	 * twice the width as height but an average radius of 1. An AffineTranform
	 * for internal use is instantiated.
	 */
	public HexagonPaintStrategy() {
		this(new AffineTransform(), 2.0, 0, 0, .3, .5, new Point(-1, 1), new Point(1, 1), new Point(2, 0), new Point(1, -1),
				new Point(-1, -1), new Point(-2, 0));
	}

	/**
	 * Constructor that allows the specification of the location, x-radius and
	 * y-radius of the prototype rectangle. The AffineTransform to use is given.
	 * 
	 * @param at
	 *            The AffineTransform to use for internal calculations
	 * @param scaleFactor
	 *            The amount by which to scale the size of the shape
	 * @param x
	 *            floating point x-coordinate of center of circle
	 * @param y
	 *            floating point y-coordinate of center of circle
	 * @param xScale
	 *            floating point x-radius of the circle
	 * @param yScale
	 *            floating point y-radius of the circle
	 * @param pts
	 *            a vararg of Points that represent the vertices of the hexagon
	 */
	public HexagonPaintStrategy(AffineTransform at, double scaleFactor, double x, double y, double xScale, double yScale, Point... pts) {
		super(at, new PolygonFactory(new AffineTransform(), 1.0, pts).makeShape(x, y, xScale, yScale));
	}

}
