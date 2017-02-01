package paintstrategy;

import java.awt.geom.AffineTransform;

import shape.Fish1PolygonFactory;

/**
 * A paint strategy that draws a cute little fish
 */
public class Fish1PaintStrategy extends ShapePaintStrategy {

	/**
	 * No-argument constructor that initializes a fish with the default values
	 * and a new AffineTransform
	 */
	public Fish1PaintStrategy() {
		this(new AffineTransform(), 0, 0, .1, .1);
	}

	/**
	 * Constructor that allows specification of an offset from the Ball center
	 * 
	 * @param x
	 *            The x offset from the ball's center
	 * @param y
	 *            The y offset from the ball's center
	 */
	public Fish1PaintStrategy(int x, int y) {
		this(new AffineTransform(), x, y, .1, .1);
	}

	/**
	 * @param at
	 *            The AffineTransform used to draw this shape
	 * @param x
	 *            The x value representing the ball's position
	 * @param y
	 *            The y value representing the ball's position
	 * @param xScale
	 *            The amount by which to scale the x-axis size of the fish
	 * @param yScale
	 *            The amount by which to scale the y-axis size of the fish
	 */
	public Fish1PaintStrategy(AffineTransform at, double x, double y, double xScale, double yScale) {
		super(at, Fish1PolygonFactory.Singleton.makeShape(x, y, xScale, yScale));
	}
}
