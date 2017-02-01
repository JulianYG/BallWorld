package shape;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * A factory that produces Rectangle2D
 */
public class RectangleShapeFactory implements IShapeFactory {

	/**
	 * A singleton used to avoid users having to initialize a RectangleShapeFactory
	 */
	public static RectangleShapeFactory Singleton = new RectangleShapeFactory();

	/**
	 * Creates the rectangle
	 */
	@Override
	public Shape makeShape(double x, double y, double width, double height) {
		return new Rectangle2D.Double(x - width / 2, y - height / 2, width, height);
	}

}
