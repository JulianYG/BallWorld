package updatestrategy;

import model.Ball;
import model.IBallCmd;
import util.IDispatcher;
import util.Randomizer;

/**
 * Makes the ball moves like drunk.
 */
public class DrunkenStrategy implements IUpdateStrategy<IBallCmd> {

	/**
	 * Sets the velocity to some random thing
	 */
	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		context.setxVel(Randomizer.Singleton.randomInt(-15, 15));
		context.setyVel(Randomizer.Singleton.randomInt(-15, 15));
	}

	@Override
	public void init(Ball context) {
	}
}
