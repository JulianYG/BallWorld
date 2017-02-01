package interactstrategy;

import model.Ball;
import model.IBallCmd;
import util.IDispatcher;

/**
 * A composite strategy that merges multiple interact strategies together
 * @author JulianYGao
 *
 */
public class MultiInteractStrategy implements IInteractStrategy<IBallCmd> {

	/**
	 * The first strategy to be composed.
	 */
	IInteractStrategy<IBallCmd> strategy1;
	/**
	 * The second strategy to be composed.
	 */
	IInteractStrategy<IBallCmd> strategy2;
	
	public MultiInteractStrategy(IInteractStrategy<IBallCmd> strat1, IInteractStrategy<IBallCmd> strat2) {
		this.strategy1 = strat1;
		this.strategy2 = strat2;
	}
	@Override
	public void interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
		strategy1.interact(context, target, disp);
		strategy2.interact(context, target, disp);
	}
}
