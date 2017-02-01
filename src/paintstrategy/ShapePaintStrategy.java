package paintstrategy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import model.Ball;

/**
 * @author johnking A paint strategy that draws a Shape to the canvas
 */
public class ShapePaintStrategy extends APaintStrategy {

	/**
	 * The shape to be drawn
	 */
	Shape shape;

	/**
	 * @param at
	 *            The AffineTransform applied to the shape
	 * @param shape
	 *            The shape to be drawn
	 */
	public ShapePaintStrategy(AffineTransform at, Shape shape) {
		super(at);
		this.shape = shape;
	}

	/**
	 * Performs the affine transform on the shape
	 */
	@Override
	public void paintXfrm(Graphics g, Ball host, AffineTransform at) {
		((Graphics2D) g).fill(at.createTransformedShape(shape));
	}
}
