package paintstrategy;

import java.awt.geom.AffineTransform;

/**
 * @author johnking
 *	A paint strategy that draws a gif of a hummingbird 
 */
public class HumbirdPaintStrategy extends ImagePaintStrategy {

	/**
	 * Provides the filename and scale factor to the super constructor 
	 */
	public HumbirdPaintStrategy() {
		super(new AffineTransform(), "image/humbird.gif", 0.5);
	}

}
