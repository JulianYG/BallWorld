package updatestrategy;

import model.Ball;
import model.IBallCmd;
import util.IDispatcher;

/**
 * A strategy that makes the ball changing in diameter
 */
public class BreathingStrategy implements IUpdateStrategy<IBallCmd> {

	/**
	 * Boolean that says whether expanding or contracting
	 */
	boolean expanding;
	/**
	 * The counter used for timings
	 */
	int counter = 0;

	/**
	 * Starts the strategy off expanding
	 */
	public BreathingStrategy() {
		expanding = true;
	}

	/**
	 * Decides when to stop expanding or contracting
	 */
	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		// Expand or contract the ball one step
		if (expanding) {
			context.setRadius(context.getRadius() + 1);
		} else {
			context.setRadius(context.getRadius() - 1);
		}

		// Every ~2sec, we switch from expanding to contracting
		if (counter % 20 == 0) {
			if (expanding)
				expanding = false;
			else
				expanding = true;
		}

		// But also don't let the ball get too small
		if (context.getRadius() < 10) {
			expanding = true;
			counter = 0;
		} else if (context.getRadius() > 100) {
			expanding = false;
			counter = 0;
		}
		counter++;
	}

	@Override
	public void init(Ball context) {
		counter = 0;
		expanding = true;
	}
}
