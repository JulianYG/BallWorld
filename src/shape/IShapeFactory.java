package shape;

import java.awt.Shape;

/**
 * Encapsulate and hide the construction details of the shape prototypes to produce
 * Shape objects fast and easily.
 */
public interface IShapeFactory {

	/**
	 * Create the Shape object defined by input string shape name
	 * @param x The x lcoation of the shape on the canvas
	 * @param y The y lcoation of the shape on the canvas
	 * @param xScale The amount by which to scale the x-axis component of the shape
	 * @param yScale The amount by which to scale the x-axis component of the shape
	 * @return the Shape object
	 */
	public Shape makeShape(double x, double y, double xScale, double yScale);

}
