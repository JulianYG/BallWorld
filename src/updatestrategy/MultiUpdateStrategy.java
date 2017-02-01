package updatestrategy;

import model.Ball;
import model.IBallCmd;
import util.IDispatcher;

/**
 * The strategy that merge multiple strategies together.
 */
public class MultiUpdateStrategy<MultiCmd> implements IUpdateStrategy<IBallCmd> {

	/**
	 * The first strategy used being composed
	 */
	IUpdateStrategy<IBallCmd> strategy1;
	/**
	 * The second strategy being composed
	 */
	IUpdateStrategy<IBallCmd> strategy2;

	/**
	 * @param strategy1 The first strategy used being composed
	 * @param strategy2 The second strategy being composed
	 */
	public MultiUpdateStrategy(IUpdateStrategy<IBallCmd> strategy1, 
			IUpdateStrategy<IBallCmd> strategy2) {
		this.strategy1 = strategy1;
		this.strategy2 = strategy2;
	}

	/**
	 * Updates the state of each composed strategy
	 */
	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		strategy1.updateState(context, disp);
		strategy2.updateState(context, disp);
	}

	@Override
	public void init(Ball context) {
		strategy1.init(context);
		strategy2.init(context);
	}
}
