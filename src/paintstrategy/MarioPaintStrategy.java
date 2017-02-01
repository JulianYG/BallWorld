package paintstrategy;

import java.awt.geom.AffineTransform;

/**
 * A paint strategy that draws mario
 */
public class MarioPaintStrategy extends ImagePaintStrategy {

	/**
	 * Loads the image and specifies scale factor
	 */
	public MarioPaintStrategy() {
		super(new AffineTransform(), "image/Mario.gif", 0.5);
	}
}
