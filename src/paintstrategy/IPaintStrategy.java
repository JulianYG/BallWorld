package paintstrategy;

import java.awt.Graphics;

import model.Ball;

/**
 * A Strategy used by a Ball to handle all painting on the
 * canvas
 */
public interface IPaintStrategy {

	/**
	 * Performs any necessary initializations of the strategy
	 * 
	 * @param host the host ball
	 */
	public void init(Ball host);

	/**
	 * Handles the painting of whatever it is onto the canvas
	 * 
	 * @param context
	 *            The ball using this strategy
	 * @param g
	 *            The canvas to draw on
	 */
	public void paint(Ball context, Graphics g);
	
	public static final IPaintStrategy NULL_STRATEGY = new IPaintStrategy() {
		public void init(Ball host) {}
		public void paint(Ball context, Graphics g) {
		}
	};
}
