package updatestrategy;

import model.Ball;
import model.IBallCmd;
import util.IDispatcher;
import util.Randomizer;

/**
 * Makes the ball wander.
 */
public class WanderStrategy implements IUpdateStrategy<IBallCmd> {

	/**
	 * Changes the velocity by a random amount
	 */
	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		context.setxVel(context.getxVel() + Randomizer.Singleton.randomInt(-1, 1));
		context.setyVel(context.getxVel() + Randomizer.Singleton.randomInt(-1, 1));
	}

	@Override
	public void init(Ball context) {
	}
}
