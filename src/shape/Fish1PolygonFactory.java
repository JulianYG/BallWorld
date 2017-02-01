package shape;

import java.awt.Point;
import java.awt.geom.AffineTransform;

/**
 * @author johnking
 *	A factory that produces a polygon for the shape
 */
public class Fish1PolygonFactory extends PolygonFactory {

	/**
	 * A singleton used to avoid users having to initialize a Randomizer
	 */
	public static IShapeFactory Singleton = new Fish1PolygonFactory();

	/**
	 * Initializes the AffineTransform and the points defining the fish
	 */
	public Fish1PolygonFactory() {
		this(new AffineTransform(), 1.0, new Point(-5, -4), new Point(-3, -1), new Point(-2, -3), new Point(0, -4), new Point(0, -6),
				new Point(2, -4), new Point(4, -3), new Point(5, -1), new Point(4, 0), new Point(5, 1), new Point(4, 3), new Point(2, 4),
				new Point(0, 4), new Point(-2, 3), new Point(-3, 1), new Point(-5, 4));
	}

	/**
	 * @param at			The AffineTransform used on the shape
	 * @param scaleFactor	The amount by which to scale the shape
	 * @param pts			The points defining the fish
	 */
	public Fish1PolygonFactory(AffineTransform at, double scaleFactor, Point... pts) {
		super(at, scaleFactor, pts);
	}
}
