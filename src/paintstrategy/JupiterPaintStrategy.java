package paintstrategy;

import java.awt.geom.AffineTransform;

/**
 * A paint strategy that draws jupiter
 */
public class JupiterPaintStrategy extends ImagePaintStrategy {

	/**
	 * Loads the image and specifies scale factor
	 */
	public JupiterPaintStrategy() {
		super(new AffineTransform(), "image/Jupiter.png", 0.5);
	}
}
