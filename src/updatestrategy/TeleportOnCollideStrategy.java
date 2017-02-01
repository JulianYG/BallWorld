package updatestrategy;

import interactstrategy.IInteractStrategy;
import interactstrategy.MultiInteractStrategy;
import model.Ball;
import model.IBallCmd;
import util.IDispatcher;
import util.IRandomizer;
import util.Randomizer;

/**
 * A ball that, on collisions with another ball, teleports both itself and the other ball
 * @author Clayton Drazner
 *
 */
public class TeleportOnCollideStrategy implements IUpdateStrategy<IBallCmd> {

	private IRandomizer rand = Randomizer.Singleton;

	public void init(Ball context) {

		context.setInteractStrategy(new MultiInteractStrategy(context.getInteractStrategy(),
				new IInteractStrategy<IBallCmd>() {
			@Override
			public void interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
				if (context != target) {
					if (context.getLocation().distance(target.getLocation()) <= (context.getRadius() + target.getRadius())) {
						//balls interact on collision
						//first the ball that THIS strategy is the strategy for will teleport...
						context.setxPos(rand.randomInt(context.getRadius(), 
										context.getCanvas().getWidth() - context.getRadius()));
						context.setyPos(rand.randomInt(context.getRadius(), 
										context.getCanvas().getHeight() - context.getRadius()));
					
						//...AND then the other ball will ALSO teleport!
						target.setxPos(rand.randomInt(target.getRadius(), 
									target.getCanvas().getWidth() - target.getRadius()));
						target.setyPos(rand.randomInt(target.getRadius(), 
									target.getCanvas().getHeight() - target.getRadius()));
					}
				}
			}
		}));
	}

	public void updateState(Ball context, IDispatcher<IBallCmd> dispatcher) {
		dispatcher.dispatch(new IBallCmd() {
			@Override
			public void apply(Ball other, IDispatcher<IBallCmd> disp) {
				context.interactWith(other, disp);
			}
		});
	}
}
