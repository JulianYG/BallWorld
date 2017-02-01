package updatestrategy;
import java.awt.Color;

import model.Ball;
import model.IBallCmd;
import paintstrategy.HexagonPaintStrategy;
import util.IDispatcher;

/**
 * A simple class to simulate brownian motion
 * @author JulianYGao
 *
 */
public class BrownStrategy implements IUpdateStrategy<IBallCmd> {

	private int count = 0;
	
	public void init(Ball context) {
		context.setColor(Color.BLACK);
		context.setxVel(0);
		context.setyVel(0);
	}

	/**
	 * Generate background of molecules to simulate random motion
	 */
	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> dispatcher) {
		count++;
		if (count < 500) {
			Ball molecule = new Ball(context.getxPos() + context.getRadius(), context.getyPos() - context.getRadius(), 
					-50, 50, 2, Color.RED, context.getCanvas());
			molecule.setPaintStrategy(new HexagonPaintStrategy());
			molecule.setUpdateStrategy(new ElasticStrategy());
			dispatcher.addObserver(molecule);
		}
	}
}
