package paintstrategy;

import java.awt.Graphics;
import java.awt.geom.*;

import model.Ball;

/**
 * Represents an IPaintStrategy that uses an AffineTransform
 */
public abstract class APaintStrategy implements IPaintStrategy {

	/**
	 * An AffineTransform used for drawing whatever image the ball uses
	 */
	protected AffineTransform at;

	/**
	 * Constructor that initializes the class's AffineTransform
	 * 
	 * @param at Affine transform object
	 */
	public APaintStrategy(AffineTransform at) {
		this.at = at;
	}

	/**
	 * Performs any necessary initialization of the Ball
	 */
	@Override
	public void init(Ball context) {

	}

	/**
	 * Sets up the AffineTransform and calls the paint methods
	 */
	@Override
	public void paint(Ball host, Graphics g) {
		double scale = host.getRadius() * 2;
		at.setToTranslation(host.getxPos(), host.getyPos());
		at.scale(scale, scale);
		at.rotate(Math.atan2(host.getyVel(), host.getxVel()));
		g.setColor(host.getColor());
		paintCfg(g, host);
		paintXfrm(g, host, at);
	}

	/**
	 * Protected method to be used by subclasses to perform additional
	 * customization
	 * 
	 * @param g
	 *            A graphics object to be used for painting
	 * @param host
	 *            The ball that this strategy belongs to
	 */
	protected void paintCfg(Graphics g, Ball host) {
	}

	/**
	 * Similar to paintCfg, but allows sharing of the AffineTransform
	 * 
	 * @param g
	 *            A graphics object to be used for painting
	 * @param host
	 *            The ball that this strategy belongs to
	 * @param at
	 *            The class's AffineTransform
	 */
	public abstract void paintXfrm(Graphics g, Ball host, AffineTransform at);

}
