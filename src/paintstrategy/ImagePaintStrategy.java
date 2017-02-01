package paintstrategy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

import model.Ball;

/**
 * @author johnking A paint strategy class that draws a given file onto the
 *         cavas
 */
public class ImagePaintStrategy extends APaintStrategy {

	/**
	 * The canvas the image is drawn on
	 */
	ImageObserver imageObs;
	/**
	 * The image to be loaded and draw
	 */
	Image image;
	/**
	 * The amount by which to scale the image when drawing
	 */
	double scaleFactor = 1.0;
	/**
	 * I'm not quite sure what this is
	 */
	double fillFactor = 1.0;
	/**
	 * The local affine transform used to do additional transformation of the
	 * image
	 */
	protected AffineTransform localAT = new AffineTransform();

	/**
	 * @param at
	 *            An AffineTransform that is used for something idk
	 * @param filename
	 *            What do you think
	 * @param fillFactor
	 *            I already said I don't know what this is
	 */
	public ImagePaintStrategy(AffineTransform at, String filename, double fillFactor) {
		super(at);
		this.fillFactor = fillFactor;
		try {
			image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(filename));
		} catch (Exception e) {
			System.err.println("ImagePaintStrategy: Error reading file: " + filename + "\n" + e);
		}
	}

	/**
	 * Does intitialization stuff
	 */
	@Override
	public void init(Ball host) {
		imageObs = host.getCanvas();
		MediaTracker mt = new MediaTracker(host.getCanvas());
		mt.addImage(image, 1);
		try {
			mt.waitForAll();
		} catch (Exception e) {
			System.out.println("ImagePaintStrategy.init(): Error waiting for image.  Exception = " + e);
		}

		scaleFactor = 2.0 / (fillFactor * (image.getWidth(imageObs) + image.getHeight(imageObs)) / 2.0);
	}

	/**
	 * Performs an additional transform on the image
	 * 
	 * @param g
	 *            The canvas to draw on
	 * @param host
	 *            The ball using this strategy
	 * @param at
	 *            The AffineTransform object used
	 * 
	 */
	public void paintXfrm(Graphics g, Ball host, AffineTransform at) {
		localAT.setToScale(scaleFactor, scaleFactor);
		localAT.translate(-image.getWidth(imageObs) / 2.0, -image.getHeight(imageObs) / 2.0);
		localAT.preConcatenate(at);
		((Graphics2D) g).drawImage(image, localAT, imageObs);
	}

	/**
	 * Keeps the image upright
	 * 
	 * @param host
	 *            The ball using this strategy
	 * @param g
	 *            The canvas to draw on
	 */
	protected void paintCfg(Graphics g, Ball host) {
		super.paintCfg(g, host);
		if (Math.abs(Math.atan2(host.getyVel(), host.getxVel())) > Math.PI / 2.0) {
			at.scale(1.0, -1.0);
		}
	}

}
