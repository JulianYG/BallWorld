package updatestrategy;

import java.awt.Color;

import interactstrategy.IInteractStrategy;
import interactstrategy.MultiInteractStrategy;
import model.Ball;
import model.IBallCmd;
import util.IDispatcher;

/**
 * A police ball that slows down every ball around it
 * @author JulianYGao
 *
 */
public class PoliceStrategy implements IUpdateStrategy<IBallCmd> {

	private int counter = 0;
	private final int MAX_SPEED = 20;
	
	public void init(Ball context) {
		context.setRadius(20);
		/**
		 * Police needs to have consistent speed and sizes
		 */
		context.setxVel(3);
		context.setyVel(3);
		context.setMass(0);
		context.setInteractStrategy(new MultiInteractStrategy(context.getInteractStrategy(),
				new IInteractStrategy<IBallCmd>() {

			@Override
			public void interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
				if (context.getLocation().distance(target.getLocation()) <= context.getRadius() * 10) {

					if (!target.getCondition()) {
						//gradually slow down absolute x velocity to 1
						if (target.getxVel() > 0) {
							target.setxVel(target.getxVel() / 2);
						} else if (target.getxVel() < 0 ){
							target.setxVel(target.getxVel() / 2);
						}

						//gradually slow down absolute y velocity to 1
						if (target.getyVel() > 0) {
							target.setyVel(target.getyVel() / 2);
						} else {
							target.setyVel(target.getyVel() / 2);
						}
						target.setCondition();
					}
					
				}	else {
					if (target.getCondition()) {
						int xVel = target.getxVel();
						int yVel = target.getyVel();
						if (Math.abs(xVel) < MAX_SPEED)
							target.setxVel((xVel >= 0) ? xVel*2 + 1 : xVel*2 - 1);
						if (Math.abs(yVel) < MAX_SPEED)
							target.setyVel((yVel >= 0) ? yVel*2 + 1 : yVel*2 - 1);
					}
					target.rstCondition();
				}	
			}
		}));
	}

	public void updateState(Ball context, IDispatcher<IBallCmd> dispatcher) {
		counter++;
		if (counter <= 30){
			context.setColor(Color.BLUE);
		} else {
			context.setColor(Color.RED);
			//after 30 seconds red, reset the counter (so it will go blue again)
			if (counter > 60) {
				counter = 0;
			}
		}

		dispatcher.dispatch(new IBallCmd() {
			@Override
			public void apply(Ball other, IDispatcher<IBallCmd> disp) {
				if (context != other) {
					context.interactWith(other, disp);
				}
			}
		});
	}
}
