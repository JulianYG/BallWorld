package updatestrategy;

import model.Ball;
import model.IBallCmd;
import util.IDispatcher;

/**
 * The strategy that makes the ball move in curves. 
 */
public class CurveStrategy implements IUpdateStrategy<IBallCmd> {

	/**
	 * The angle of rotation of the velocity vector
	 */
	double theta = 0.5;

	/**
	 * Changes the xVel and yVel in a curve fashion
	 */
	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {

		// Following the formula for curving ball
		double xVel = Math.round(context.getxVel() * Math.cos(theta) - context.getyVel() * Math.sin(theta));
		double yVel = Math.round(context.getyVel() * Math.cos(theta) + context.getxVel() * Math.sin(theta));

		context.setxVel((int) xVel);
		context.setyVel((int) yVel);
	}

	@Override
	public void init(Ball context) {
		theta = .5;
	}
}
