package updatestrategy;

import model.Ball;
import util.IDispatcher;

/**
 * The regular.
 */
public class StraightStrategy<IBallCmd> implements IUpdateStrategy<IBallCmd> {

	/**
	 * Nops
	 */
	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
	}

	@Override
	public void init(Ball context) {
	}
}
