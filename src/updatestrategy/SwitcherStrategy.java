package updatestrategy;

import model.Ball;
import model.IBallCmd;
import util.IDispatcher;

/**
 * The ball for later switch in behavior.
 */
public class SwitcherStrategy implements IUpdateStrategy<IBallCmd> {

	/**
	 * The initial strategy used
	 */
	private IUpdateStrategy<IBallCmd> strategy = new StraightStrategy<IBallCmd>();
	private Ball cont;
	
	/**
	 * Updates the state of the strategy currently in use
	 * @param context The ball using this strategy
	 */
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		strategy.updateState(context, disp);
	}

	/**
	 * Sets the current strategy to a new one
	 * @param newStrategy The strategy to use
	 */
	public void setStrategy(IUpdateStrategy<IBallCmd> newStrategy) {
		strategy = newStrategy;
		strategy.init(cont);
		// every time switch, needs to init that new strategy!
	}

	/**
	 * Here init needs to take the ball for later init
	 */
	@Override
	public void init(Ball context) {
		cont = context;
	}
}
