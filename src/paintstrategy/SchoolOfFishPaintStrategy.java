package paintstrategy;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import model.Ball;

/**
 * @author johnking A paint strategy that composes multiple Fish1PaintStrategies
 */
public class SchoolOfFishPaintStrategy extends MultiPaintStrategy {

	/**
	 * Initializes the AffineTransform an three Fish1PaintStrategies
	 */
	public SchoolOfFishPaintStrategy() {
		super(new AffineTransform(), new Fish1PaintStrategy(1, 0), new Fish1PaintStrategy(-1, 1), new Fish1PaintStrategy(0, -1));
	}

	/**
	 * Keeps the composed fish upright
	 * 
	 * @param host
	 *            The ball using this strategy
	 * @param g
	 *            The canvas to draw on
	 */
	protected void paintCfg(Graphics g, Ball host) {
		if (Math.abs(Math.atan2(host.getyVel(), host.getxVel())) > Math.PI / 2.0) {
			at.scale(1.0, -1.0);
		}
	}

}
