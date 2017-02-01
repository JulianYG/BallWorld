package updatestrategy;

import interactstrategy.IInteractStrategy;
import interactstrategy.MultiInteractStrategy;
import model.Ball;
import model.IBallCmd;
import util.IDispatcher;
import util.IRandomizer;
import util.Randomizer;

/**
 * A transport point in space, any ball hit RIGHT INTO THE CENTER will be transported
 * to another random point with original speed (can only transport balls smaller than
 * itself!!)
 * @author JulianYGao
 *
 */
public class WormholeStrategy implements IUpdateStrategy<IBallCmd> {

	private IRandomizer rand = Randomizer.Singleton;

	public void init(Ball context) {
		context.setxVel(0);
		context.setyVel(0);
		context.setMass(0);
		context.setInteractStrategy(new MultiInteractStrategy(context.getInteractStrategy(),
				new IInteractStrategy<IBallCmd>() {
			@Override
			public void interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
				if (context != target) {
					//if the other ball is smaller and touches the context ball, they interact
					if (context.getLocation().distance(target.getLocation()) <= (context.getRadius() + target.getRadius()) && context.getRadius() >= target.getRadius())	{
						//on this interaction, the other ball teleports. Otherwise, it can't 'fit' inside the wormhole, so nothing happens!
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
