package updatestrategy;

import java.awt.Color;

import interactstrategy.*;
import model.Ball;
import model.IBallCmd;
import paintstrategy.*;
import util.IDispatcher;

/**
 * The black hole that absorbs anything close to itself. When
 * radius exceeds limit, explode into pieces and leave with smaller black hole
 * @author JulianYGao
 */
public class BlackholeStrategy implements IUpdateStrategy<IBallCmd> {

	private int originRad;
	public void init(Ball context) {
		originRad = context.getRadius();
		context.setColor(Color.BLACK);
		context.setMass(0);
		context.setInteractStrategy(new MultiInteractStrategy(context.getInteractStrategy(),
				new IInteractStrategy<IBallCmd>() {
			@Override
			public void interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
				int newRadius = newRadius(context.getRadius(), target.getRadius());
				context.setRadius(newRadius);				
				disp.deleteObserver(target);			
			}
		}));
	}

	/**
	 * Test the collide condition, at the same time interact (eating) the other ball
	 */
	public void updateState(Ball context, IDispatcher<IBallCmd> dispatcher) {

		// if the ball exceeds its original radius too much, supernova
		if (context.getRadius() > originRad * 3) {
			Ball newObj1 = new Ball(context.getxPos() - context.getRadius()*2, context.getyPos() + context.getRadius()*2, 
					-40, 40, originRad / 4 + 2,
					Color.RED, context.getCanvas());
			newObj1.setPaintStrategy(new HexagonPaintStrategy());
			newObj1.setUpdateStrategy(new ElasticStrategy());

			//Second object
			Ball newObj2 = new Ball(context.getxPos() - context.getRadius()*2, context.getyPos() - context.getRadius()*2, 
					-40, -40, originRad / 4 + 2,
					Color.BLUE, context.getCanvas());
			newObj2.setPaintStrategy(new HexagonPaintStrategy());
			newObj2.setUpdateStrategy(new ElasticStrategy());

			//Third object
			Ball newObj3 = new Ball(context.getxPos() + context.getRadius()*2, context.getyPos() - context.getRadius()*2, 
					40, -40, originRad / 4 + 2,
					Color.GREEN, context.getCanvas());
			newObj3.setPaintStrategy(new HexagonPaintStrategy());
			newObj3.setUpdateStrategy(new ElasticStrategy());

			//fourth object
			Ball newObj4 = new Ball(context.getxPos() + context.getRadius()*2, context.getyPos() + context.getRadius()*2, 
					40, 40, originRad / 4 + 2,
					Color.MAGENTA, context.getCanvas());
			newObj4.setPaintStrategy(new HexagonPaintStrategy());
			newObj4.setUpdateStrategy(new ElasticStrategy());

			Ball newObj5 = new Ball(context.getxPos(), context.getyPos(), 
					0, 0, originRad / 2 + 2,
					Color.MAGENTA, context.getCanvas());
			newObj5.setPaintStrategy(context.getPaintStrategy());
			newObj5.setUpdateStrategy(new BlackholeStrategy());

			dispatcher.deleteObserver(context);	
			dispatcher.addObserver(newObj4);
			dispatcher.addObserver(newObj3);
			dispatcher.addObserver(newObj2);
			dispatcher.addObserver(newObj1);
			dispatcher.addObserver(newObj5);
		}

		dispatcher.dispatch(new IBallCmd() {
			@Override
			public void apply(Ball other, IDispatcher<IBallCmd> disp) {
				if (context != other) {
					if ((context.getRadius() * 2 + other.getRadius()) >= context.getLocation().distance(other.getLocation())) {
						context.interactWith(other, disp);
					}
				}
			}
		});
	}

	private int newRadius(int origin, int added) {
		return (int) Math.sqrt(origin*origin + added*added); 
	}
}
