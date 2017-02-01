package updatestrategy;

import model.Ball;
import model.IBallCmd;
import util.IDispatcher;
import util.Randomizer;

/**
 * A strategy to set the ball to randomly changing colors.
 */
public class ColorStrategy implements IUpdateStrategy<IBallCmd> {

	/**
	 * Resets the color to a random value
	 */
	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		context.setColor(Randomizer.Singleton.randomColor());
	}

	@Override
	public void init(Ball context) {
	}
}
