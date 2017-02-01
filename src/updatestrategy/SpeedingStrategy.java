package updatestrategy;

import model.IBallCmd;
import util.IDispatcher;
import model.Ball;

/**
 * A SpeedingStrategy slowly increases the speed and then rapidly decreases the
 * speed of balls that it is applied to
 */
public class SpeedingStrategy implements IUpdateStrategy<IBallCmd> {

	/**
	 * The maximum speed that a SpeedingStrategy will increase a ball's speed to
	 */
	private final int MAX_SPEED = 200;

	/**
	 * If the ball is speeding up or slowing down
	 */
	private boolean speedingUp = true;

	@Override
	/**
	 * Slowly increase the speed up till MAX_SPEED, then rapidly decrease it to 0
	 * and repeat
	 */
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		// Speed up or not
		int acceleration = speedingUp ? 1 : -1;

		// Save for convenience
		int vx = context.getxVel();
		int vy = context.getyVel();

		// Change velocities
		context.setxVel((vx < 0) ? vx - acceleration : vx + acceleration);
		context.setyVel((vy < 0) ? vy - acceleration : vy + acceleration);

		// Stop speeding up eventually
		if (Math.abs(vx) > MAX_SPEED && Math.abs(vy) > MAX_SPEED) {
			speedingUp = false;
		} else if (Math.abs(vx) < 2 && Math.abs(vy) < 2) {
			speedingUp = true;
		}
	}

	@Override
	public void init(Ball context) {
	}
}
