package model;

import java.awt.Color;
import java.awt.Point;
import javax.swing.JPanel;

import interactstrategy.IInteractStrategy;
import paintstrategy.IPaintStrategy;
import updatestrategy.IUpdateStrategy;
import util.IDispatcher;
import util.IObserver;

/**
 * The Ball shape object that moves inside given region, 
 * itself defined by update strategy, paint strategy and canvas region.
 */
public class Ball implements IObserver<IBallCmd> {

	/**
	 * The location of the shape's center
	 */
	private Point location;
	/**
	 * The velocity of the shape
	 */
	private Point velocity;
	/**
	 * A flag made for strategy convenience
	 */
	private boolean checkCondition = false;
	/**
	 * color of the shape
	 */
	private Color color;
	/**
	 * Radius of a ball.
	 * 
	 * Often will be used to get a measure of the ball's 'mass'
	 * if a strategy hasn't given the ball a value to use
	 */
	private int radius;
	/**
	 * 'Mass' of a ball. If this is not initialized by a strategy,
	 * then other strategies will default to using radius instead of 
	 * this field.
	 */
	private double mass;
	/**
	 * Panel to be painted on.
	 */
	JPanel pnlCanvas;
	/**
	 * The strategy used by the ball for moving around the canvas
	 */
	private IUpdateStrategy<IBallCmd> updateStrategy = new IUpdateStrategy.NullFactory<IBallCmd>().makeUpdateStrategy();
	/**
	 * The strategy used by the ball for displaying itself on the canvas
	 */
	private IPaintStrategy paintStrategy = IPaintStrategy.NULL_STRATEGY;
	/**
	 * The strategy used by the ball to interact with others
	 */
	private IInteractStrategy<IBallCmd> interactStrategy = IInteractStrategy.NULL_STRATEGY;

	/**
	 * Constructor of Ball
	 * @param xPos			the x coordinate position of the ball
	 * @param yPos			the y coordinate position of the ball
	 * @param xVel			the x velocity of the ball
	 * @param yVel			the y velocity of the ball
	 * @param radius    	the radius of the ball
	 * @param color			the color of the ball
	 * @param pnlCanvas		the panel to which the ball will be painted
	 */
	public Ball(int xPos, int yPos, int xVel, int yVel, int radius, Color color, JPanel pnlCanvas) {
		this.location = new Point(xPos, yPos);
		this.velocity = new Point(xVel, yVel);
		this.radius = radius;
		this.color = color;
		this.pnlCanvas = pnlCanvas;
		this.mass = radius * radius;
	}

	/**
	 * Method to update the position, velocity and color of the ball.
	 * @param disp
	 * 		the dispatcher to send out commands
	 * @param cmd
	 * 	    the command sent between balls
	 */
	public void update(IDispatcher<IBallCmd> disp, IBallCmd cmd) {
		cmd.apply(this, disp);
	}

	/**
	 * Helper function to update the ball's velocity when a boundary is hit.
	 */
	public void bounce() {

		// If the wall is reached, bounce the ball back by changing its direction.
		if (getxPos() > pnlCanvas.getWidth() - getRadius()) {
			setxVel(-1 * getxVel());
			setxPos(pnlCanvas.getWidth() - getRadius());
		} else if (getxPos() - getRadius() <= 0) {
			setxVel(-1 * getxVel());
			setxPos(getRadius());
		} else if (getyPos() >= pnlCanvas.getHeight() - getRadius()) {
			setyVel(-1 * getyVel());
			setyPos(pnlCanvas.getHeight() - getRadius());
		} else if (getyPos() - getRadius() <= 0) {
			setyVel(-1 * getyVel());
			setyPos(getRadius());
		}
	}

	/**
	 * Helper function to update the ball's position.
	 */
	public void move() {
		// New Position = Old Position + Velocity.
		setxPos(getxPos() + getxVel());
		setyPos(getyPos() + getyVel());
	}

	/**
	 * @return The ball's move strategy
	 */
	public IUpdateStrategy<IBallCmd> getUpdateStrategy() {
		return updateStrategy;
	}

	/**
	 * @param strategy The update strategy for this ball to use
	 */
	public void setUpdateStrategy(IUpdateStrategy<IBallCmd> strategy) {
		this.updateStrategy = strategy;
		updateStrategy.init(this);
	}

	/**
	 * @return The ball's paint strategy
	 */
	public IPaintStrategy getPaintStrategy() {
		return paintStrategy;
	}

	/**
	 * @param paintStrat The paint strategy for this ball to use
	 */
	public void setPaintStrategy(IPaintStrategy paintStrat) {
		this.paintStrategy = paintStrat;
		paintStrategy.init(this);
	}

	/**
	 * Get the location of the ball
	 * @return the Point representing the ball's location
	 */
	public Point getLocation() {
		return this.location;
	}
	
	/**
	 * Get the velocity of the ball
	 * @return the Point representing the ball's velocity
	 */
	public Point getVelocity() {
		return this.velocity;
	}
	
	/**
	 * @return The ball's x velocity
	 */
	public int getxVel() {
		return this.velocity.x;
	}

	/**
	 * @param xVel The x velocity for the ball to have
	 */
	public void setxVel(int xVel) {
		this.velocity.x = xVel;
	}

	/**
	 * @return The ball's y velocity
	 */
	public int getyVel() {
		return this.velocity.y;
	}

	/**
	 * @param yVel The y velocity for the ball to use
	 */
	public void setyVel(int yVel) {
		this.velocity.y = yVel;
	}
	
	/**
	 * @return The ball's radius
	 */
	public int getRadius() {
		return radius;
	}
	/**
	 * Get the check condition of the ball
	 * @return boolean of whether checked or not
	 */
	public boolean getCondition() {
		return this.checkCondition;
	}
	/**
	 * Set the check condition to be checked
	 */
	public void setCondition() {
		this.checkCondition = true;
	}
	/**
	 * Reset the balls condition to be unchecked
	 */
	public void rstCondition() {
		this.checkCondition = false;
	}
	/**
	 * @param radius The radius for the ball to have
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * @return The ball's color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color The color for the ball to have
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the ball's x position
	 */
	public int getxPos() {
		return this.location.x;
	}

	/**
	 * @param xPos The x position for the ball to have
	 */
	public void setxPos(int xPos) {
		this.location.x = xPos;
	}

	/**
	 * @return The ball's y position
	 */
	public int getyPos() {
		return this.location.y;
	}

	/**
	 * @param yPos The y position for the ball to have
	 */
	public void setyPos(int yPos) {
		this.location.y = yPos;
	}

	/**
	 * @return The canvas that the ball is being drawn on
	 */
	public JPanel getCanvas() {
		return pnlCanvas;
	}
	
	/**
	 * The method that defines post-collision behavior of the balls
	 * @param target	the target ball to receive command
	 * @param disp	the dispatcher that sends the command
	 */
	public void interactWith(Ball target, IDispatcher<IBallCmd> disp) {
		disp.dispatch(new IBallCmd() {
			@Override
			public void apply(Ball context, IDispatcher<IBallCmd> disp) {
				if (context != target)
					context.getInteractStrategy().interact(context, target, disp);
			}
		});
	}
	
	public void setInteractStrategy(IInteractStrategy<IBallCmd> intStrat) {
		this.interactStrategy = intStrat;
	}
	
	public IInteractStrategy<IBallCmd> getInteractStrategy() {
		return this.interactStrategy;
	}

	/**
	 * Gets the mass
	 * @return the mass of the ball
	 */
	public double getMass() {
		//If the mass has been set
		return this.mass;
	}

	public void setMass(double mass) {
		//As soon as this is called, getMass() will return the private field mass's value
		//Before calling this, the ball just uses the radius
		this.mass = mass;
	}
}
