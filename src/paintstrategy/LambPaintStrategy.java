package paintstrategy;

import java.awt.geom.AffineTransform;

/**
 * A paint strategy that draws a lamb
 */
public class LambPaintStrategy extends ImagePaintStrategy {

	/**
	 * Loads the image and specifies scale factor
	 */
	public LambPaintStrategy() {
		super(new AffineTransform(), "image/lamb.gif", 1);
	}

}
