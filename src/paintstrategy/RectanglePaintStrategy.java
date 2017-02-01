package paintstrategy;

import java.awt.geom.AffineTransform;

import shape.RectangleShapeFactory;

/**
 * Paint strategy to paint an rectangle shape
 */
public class RectanglePaintStrategy extends ShapePaintStrategy {

	/**
	 * No parameter constructor that creates a prototype rectangle that has
	 * twice the width as height but an average radius of 1. An AffineTranform
	 * for internal use is instantiated.
	 */
	public RectanglePaintStrategy() {
		this(new AffineTransform(), 0, 0, 4.0 / 3.0, 2.0 / 3.0);
	}

	/**
	 * Constructor that allows the specification of the location, x-radius and
	 * y-radius of the prototype rectangle. The AffineTransform to use is given.
	 * 
	 * @param at
	 *            The AffineTransform to use for internal calculations
	 * @param x
	 *            floating point x-coordinate of center of circle
	 * @param y
	 *            floating point y-coordinate of center of circle
	 * @param width
	 *            floating point x-radius of the circle (rectangle)
	 * @param height
	 *            floating point y-radius of the circle (rectangle)
	 */
	public RectanglePaintStrategy(AffineTransform at, double x, double y, double width, double height) {
		super(at, RectangleShapeFactory.Singleton.makeShape(x, y, width, height));
	}
}
