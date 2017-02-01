package shape;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * @author johnking
 *	A factory that produces an Ellipse!
 */
public class EllipseShapeFactory implements IShapeFactory {

	/**
	 * A singleton to let users not have to instantiate one of these
	 */
	public static IShapeFactory Singleton = new EllipseShapeFactory();

	/**
	 * Creates a new Ellipse2D 
	 * @param x The x lcoation of the shape on the canvas
	 * @param y The y lcoation of the shape on the canvas
	 * @param width The amount by which to scale the x-axis component of the shape
	 * @param height The amount by which to scale the x-axis component of the shape
	 * @return An Ellipse2D
	 */
	public Shape makeShape(double x, double y, double width, double height) {
		return new Ellipse2D.Double(x - width / 2, y - height / 2, width, height);
	}

}
