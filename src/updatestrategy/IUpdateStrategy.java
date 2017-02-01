package updatestrategy;

import model.Ball;
import util.IDispatcher;

/**
 * Interface for update strategy
 */
public interface IUpdateStrategy<TDispMsg> {
	
	/**
	 * Initializes the strategy. Should be called every time the ball sets a 
	 * new strategy.
	 * @param context The ball that is using this strategy
	 */
	public void init(Ball context);
	
	/**
	 * The method to update states of the ball
	 * @param context the ball using this method for update
	 * @param dispatcher dispatcher to send the command
	 */
	public void updateState(Ball context, IDispatcher<TDispMsg> dispatcher);
	
	/**
	 * * A factory for a typed null strategy object.
     * Usage: instantiate this factory class using the desired TDispMsg type and 
     * then call its makeUpdateStrategy() method to create the correctly typed null strategy object.
     */
	public static final class NullFactory<TDispMsg> implements IUpdateStrategyFac<TDispMsg> {
		public IUpdateStrategy<TDispMsg> makeUpdateStrategy() {
			return new IUpdateStrategy<TDispMsg>() {
				// no-op
				public void init(Ball context) {
				}
				public void updateState(Ball context, IDispatcher<TDispMsg> dispatcher) {
				}
			};
		}
	}
}
