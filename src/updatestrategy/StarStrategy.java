package updatestrategy;

import java.awt.Color;

import interactstrategy.IInteractStrategy;
import interactstrategy.MultiInteractStrategy;
import model.Ball;
import model.IBallCmd;
import paintstrategy.*;
import util.IDispatcher;
import util.Randomizer;

/**
 * A 'star' ball that sucks (think gravitational pull) all balls within that ball's radius 
 * and increase its area. When reaching certain limits it will explode
 * and spit out numerous small balls.
 *
 * To increase the mass of this star enough to be noticeable, 
 * try adding other stars to the system for it to eat up
 *
 */
public class StarStrategy implements IUpdateStrategy<IBallCmd> {

	private double originMass;
	private int originRad;
	public void init(Ball context) {
		//default mass is large but nothing on a singularity
		context.setMass(1000);
		//For purposes of a nice looking simulation, what we're going to do 
		//is force every ball on the screen within a certain range to be pulled 
		//in (and forget their old velocities)
		//so that stars can eat each other and objects in the same range of size (like planets)
		context.setMass(Randomizer.Singleton.randomDouble(1050, 1100));

		//starts as a small red star
		context.setColor(Color.RED);
		this.originRad = context.getRadius();
		this.originMass = context.getMass();

		//'sucks in' objects that are less massive, then adds their mass to itself
		context.setInteractStrategy(new MultiInteractStrategy(context.getInteractStrategy(),
				new IInteractStrategy<IBallCmd>() {
			@Override
			public void interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
				//Suck in things that get too close, but make sure this star is actually more massive!
				if ((context.getRadius() * 2 + target.getRadius()) >= context.getLocation().distance(target.getLocation())){
					if (context.getMass() > target.getMass()) {
						//will never overflow here, because the star would have exploded if the mass was too large
						double newMass = context.getMass() + target.getMass();
						context.setMass(newMass);	
						disp.deleteObserver(target);
					}
				} else if (context.getLocation().distance(target.getLocation()) <= context.getRadius() * 35 && target.getMass() != 0) {
					//(simplified) gravity calculations! first off, approx g * m1/m2
					double forceGravity = 10 * context.getMass()/target.getMass();
					double suctionConstant = 5;
					//The 2/ is so it looks nicer on farther distances
					double resultingSuction = suctionConstant * forceGravity * (2/(context.getLocation().distance(target.getLocation())));
					//adjust for mass
					double angleBetween = Math.atan2(target.getLocation().getY() - context.getLocation().getY(), target.getLocation().getX() - context.getLocation().getX());
					double deltaXVelocity = (-1* Math.cos(angleBetween)) * resultingSuction;
					double deltaYVelocity = (-1* Math.sin(angleBetween)) * resultingSuction;
					int newXVelocity = (int) (target.getVelocity().getX() + deltaXVelocity);
					int newYVelocity = (int) (target.getVelocity().getY() + deltaYVelocity);

					double oldAbsTotalVelocity = Math.sqrt(Math.pow(target.getxVel(), 2) + Math.pow(target.getyVel(), 2));
					//ensure that the total absolute velocity doesn't decrease too quickly or too much (would make things look weird)
					if ((Math.abs(newXVelocity) >= 5) || Math.abs(newYVelocity) >= 5 || oldAbsTotalVelocity < 5) {
						//double newAbsTotalVelocity = Math.sqrt(Math.pow(newXVelocity, 2) + Math.pow(newYVelocity, 2));
						//can't decrease to less than 8 + 1/4th of old value per frame
						//if (newAbsTotalVelocity > (oldAbsTotalVelocity/4) + 8) {
						target.setxVel(newXVelocity);
						target.setyVel(newYVelocity);
					}
				}
			}
		}));
	}

	/**
	 * Test the collide condition, at the same time interact (eating) the other ball
	 */
	public void updateState(Ball context, IDispatcher<IBallCmd> dispatcher) {

		//as the star grows in mass, like a real star probably would, its color changes
		if ((context.getMass() > originMass*1.05) && context.getMass() < originMass*1.5) {
			context.setColor(Color.ORANGE);
		} else if ((context.getMass() > originMass*1.5) && context.getMass() < originMass*3) {
			context.setColor(Color.YELLOW);
		} else if ((context.getMass() > originMass*3) && context.getMass() < originMass*5) {
			context.setColor(Color.WHITE);
		} else if ((context.getMass() > originMass*5) && context.getMass() < originMass*7) {
			context.setColor(Color.BLUE);	
		} 

		// if the ball exceeds its original mass too much, 'supernova' (explode)
		// if the ball exceeds its original radius too much, supernova

		if (context.getMass() > originMass * 7) {
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
					context.interactWith(other, disp);
				}
			}
		});
	}

}
