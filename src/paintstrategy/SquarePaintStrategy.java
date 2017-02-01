package paintstrategy;

import java.awt.*;

import model.Ball;

/**
 * Paint strategy that paints a filled square with the Ball's radius. This
 * functionality is duplicated by the RectanglePaintStrategy. The class
 * demonstrates a direct implementation of IPaintStrategy.
 */
public class SquarePaintStrategy implements IPaintStrategy {

	/**
	 * No parameter constructor for the class
	 */
	public SquarePaintStrategy() {
	}

	/**
	 * Paints a square on the given graphics context using the color and radius
	 * provided by the host.
	 * 
	 * @param host
	 *            The ball using this strategy
	 * @param g
	 * 			Graphics object
	 */
	public void paint(Ball host, Graphics g) {
		int halfSide = host.getRadius();
		g.setColor(host.getColor());
		g.fillRect(host.getxPos() - halfSide, host.getyPos() - halfSide, 2 * halfSide, 2 * halfSide);
	}

	/**
	 * By default, do nothing for initialization.
	 * 
	 * @param context
	 *            The ball using this strategy
	 */
	public void init(Ball context) {
	}
}
