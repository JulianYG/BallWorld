package updatestrategy;

import model.Ball;
import model.IBallCmd;
import util.IDispatcher;

/**
 * The ball will produce a child of itself with the ball in its way,
 * and make that ball bearable too
 * 
 */
public class SpawnStrategy implements IUpdateStrategy<IBallCmd> {

	private int count = 0; 
	// tick counter that counts out the delay before another ball can be spawned.
	private int delay = 100; 
	// tick delay which increases at each spawn to keep total spawn rate from exponentially exploding.

	@Override
	public void updateState(final Ball context, IDispatcher<IBallCmd> dispatcher) {
		if (delay < count++) {
			dispatcher.dispatch(new IBallCmd() {
				@Override
				public void apply(Ball other, IDispatcher<IBallCmd> disp) {
					if (count != 0 && context != other) {
						if ((context.getRadius() + other.getRadius()) > 
						context.getLocation().distance(other.getLocation())) {
							Ball child = new Ball(context.getxPos(), context.getyPos(), 
								-context.getxVel() + 1, -context.getyVel() + 1, context.getRadius(),
									context.getColor(), context.getCanvas());
							child.setUpdateStrategy(context.getUpdateStrategy());
							child.setPaintStrategy(context.getPaintStrategy());
							other.setUpdateStrategy(new SpawnStrategy());
							disp.addObserver(child);
							count = 0;
							delay *= 5;
						}
					}
				}
			});
		}
	}
	@Override
	public void init(Ball context) {
		count = 0;
		delay = 100;
	}
}
