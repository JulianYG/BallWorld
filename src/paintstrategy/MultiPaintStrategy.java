package paintstrategy;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import model.Ball;

/**
 * @author johnking A paint strategy that composes one or more other
 *         APaintStrategies
 */
public class MultiPaintStrategy extends APaintStrategy {

	/**
	 * All of the composed APaintStrategies
	 */
	APaintStrategy[] pStrats;

	/**
	 * @param at
	 *            The AffineTransform shared by the composed strategies
	 * @param pStrats
	 *            All of the composed APaintStrategies
	 */
	public MultiPaintStrategy(AffineTransform at, APaintStrategy... pStrats) {
		super(at);
		this.pStrats = pStrats;
	}

	/**
	 * Performs the same transform on all the composed strategies
	 */
	@Override
	public void paintXfrm(Graphics g, Ball host, AffineTransform at) {
		for (APaintStrategy pStrat : pStrats) {
			pStrat.paintXfrm(g, host, at);
		}
	}

	/**
	 * Initializes all of the composed strategies
	 */
	@Override
	public void init(Ball context) {
		for (APaintStrategy pStrat : pStrats) {
			pStrat.init(context);
		}
	}
}
