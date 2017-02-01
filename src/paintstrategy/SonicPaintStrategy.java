package paintstrategy;

import java.awt.geom.AffineTransform;

/**
 * A paint strategy that draws sonic
 */
public class SonicPaintStrategy extends ImagePaintStrategy {

	/**
	 * Loads the image and specifies scale factor
	 */
	public SonicPaintStrategy() {
		super(new AffineTransform(), "image/Sonic.gif", 0.5);
	}
}
