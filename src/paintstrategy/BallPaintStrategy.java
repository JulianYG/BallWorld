package paintstrategy;

import java.awt.Graphics;

import model.Ball;

/**
 * A paint strategy that just draws a normal ball shape
 */
public class BallPaintStrategy implements IPaintStrategy {

	/**
	 * Paints a square on the given graphics context using the color and radius
	 * provided by the host. param g The Graphics context that will be paint on
	 * param host The host Ball that the required information will be pulled
	 * from.
	 * 
	 * @param host
	 *            The Ball that this paint strategy is visually representing
	 * @param g
	 *            The graphics object that we're drawing onto
	 */
	public void paint(Ball host, Graphics g) {
		g.setColor(host.getColor());
		g.fillOval(host.getxPos() - host.getRadius(), host.getyPos() - host.getRadius(), 2 * host.getRadius(), 2 * host.getRadius());
	}

	/**
	 * By default, do nothing for initialization.
	 * 
	 * @param context
	 *            The Ball that this paint strategy is visually representing
	 */
	public void init(Ball context) {
	}
}
