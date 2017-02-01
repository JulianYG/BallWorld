package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import paintstrategy.IPaintStrategy;
import paintstrategy.IPaintStrategyFac;
import updatestrategy.IUpdateStrategy;
import updatestrategy.IUpdateStrategyFac;
import updatestrategy.MultiUpdateStrategy;
import updatestrategy.StraightStrategy;
import updatestrategy.SwitcherStrategy;
import util.BallCmdDispatcher;
import util.IDispatcher;
import util.IRandomizer;
import util.Randomizer;

/**
 * The ball model class.
 */
public class BallModel {

	/**
	 * Model to view adapter.
	 */
	private IModel2ViewAdapter model2ViewAdpt = IModel2ViewAdapter.NULL_OBJECT;

	/**
	 * The strategy used to switch all switcher balls at the same time
	 */
	private SwitcherStrategy switcherStrategy = new SwitcherStrategy();

	/**
	 * Dispatcher of the model.
	 */
	private IDispatcher<IBallCmd> myDispatcher = new BallCmdDispatcher<IBallCmd>();

	/**
	 * Time slice of the timer.
	 */
	private int timeSlice = 25; // update every 25 milliseconds

	/**
	 * Initiates the timer.
	 */
	private Timer timer = new Timer(timeSlice, new ActionListener() {

		/**
		 * The timer "ticks" by calling this method every _timeslice
		 * milliseconds
		 */
		public void actionPerformed(ActionEvent e) {
			// Update the view every 25 milliseconds.
			model2ViewAdpt.update();
		}
	});

	/**
	 * A factory for a beeping error strategy that beeps the speaker every 25
	 * updates. Either use the _errorStrategyFac variable directly if you need a
	 * factory that makes an error strategy, or call _errorStrategyFac.make() to
	 * create an instance of a beeping error strategy.
	 */
	private IUpdateStrategyFac<IBallCmd> errorStrategyFac = new IUpdateStrategyFac<IBallCmd>() {
		@Override
		/**
		 * Make the beeping error strategy
		 * 
		 * @return An instance of a beeping error strategy
		 */
		public IUpdateStrategy<IBallCmd> makeUpdateStrategy() {
			return new IUpdateStrategy<IBallCmd>() {
				private int count = 0; // update counter
				@Override
				/**
				 * Beep the speaker every 25 updates
				 */
				public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
					if (25 < count++) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						count = 0;
					}
				}
				@Override
				public void init(Ball context) {	
				}
			};
		}
	};

	/**
	 * BallModel constructor.
	 * 
	 * @param model2ViewAdpt The model2ViewAdpt of the model.
	 */
	public BallModel(IModel2ViewAdapter model2ViewAdpt) {
		this.model2ViewAdpt = model2ViewAdpt;
	}

	/**
	 * Update the model.
	 * 
	 * @param g The graphics g to be updated.
	 */
	public void update(Graphics g) {
		// Update all balls.
		myDispatcher.dispatch((context, disp) -> {
			context.move();
			context.bounce();
			context.getPaintStrategy().paint(context, g);
			context.getUpdateStrategy().updateState(context, disp);
		});
	}

	/**
	 * Start timer.
	 */
	public void start() {
		timer.start();
	}

	/**
	 * Given an update strategy and paint strategy, initializes a ball with the strategies
	 * @param updateStrategy An IUpdateStrategy for the Ball
	 * @param paintStrat An IPaintStrategy for the Ball
	 */
	public void loadBall(IUpdateStrategy<IBallCmd> updateStrategy, IPaintStrategy paintStrat) {
		Ball b = makeBall(model2ViewAdpt.getPnlCanvas());
		b.setUpdateStrategy(updateStrategy);
		b.setPaintStrategy(paintStrat);

		if (null == paintStrat) {
			System.out.println("Null IPaintStrategy in BallModel.loadBall");
		}

		// Add the ball to dispatcher.
		myDispatcher.addObserver(b);
	}

	/**
	 * Clear all balls painted.
	 */
	public void clearAllBalls() {
		// Delete all balls from dispatcher.
		myDispatcher.deleteObservers();
	}

	/**
	 * Given a name of a paint strategy, loads an instance of it and returns it
	 * @param 	paintStrategyName the name of the class to be loaded
	 * @return 	An initialized instance of the class specified
	 */
	private IPaintStrategy loadPaintStrategy(String paintStrategyName) {
		try {
			Object[] args = {};
			java.lang.reflect.Constructor<?> cs[] = Class.forName(paintStrategyName).getConstructors();
			java.lang.reflect.Constructor<?> c = null;
			for (int i = 0; i < cs.length; i++) {
				// find the first constructor with the right number of input parameters 
				if (args.length == (cs[i]).getParameterTypes().length) {
					c = cs[i];
					break;
				}
			}
			//call the constructor for the found paint strategy
			return (IPaintStrategy) c.newInstance(args);
		} catch (Exception ex) {
			System.out.println("Uh oh! The model had some sort of error when it tried to load Class " + paintStrategyName + ". Here is the exception's message:");
			//Done instead of just printing ex due to some weird infinite loop behavior we encountered before changing to this
			System.err.println(ex.getMessage()); 
			System.out.println("We not going to return null in place of a paint strategy. Now returning to normal execution....");
			return null;
		}
	}

	/**
	 * The following method returns an instance of an IUpdateStrategy, given a fully
	 * qualified updateStrategy name.
	 * The method assumes that there is only one constructor for the supplied class
	 * that has the same *number* of input parameters as specified in the args
	 * array and that it conforms to a specific signature, i.e. specific order
	 * and types of input parameters in the args array.
	 * 
	 * @param updateStrategyName
	 *            A string that is the fully qualified class name of the desired
	 *            object
	 * @return An instance of the supplied class.
	 */
	@SuppressWarnings("unchecked")
	private IUpdateStrategy<IBallCmd> loadUpdateStrategy(String updateStrategyName) {
		try {
			Object[] args = {};
			java.lang.reflect.Constructor<?> cs[] = Class.forName(updateStrategyName).getConstructors();
			java.lang.reflect.Constructor<?> c = null;
			for (int i = 0; i < cs.length; i++) { // find the first constructor
													// with the right number of
													// nput parameters
				if (args.length == (cs[i]).getParameterTypes().length) {
					c = cs[i];
					break;
				}
			}
			//call the constructor for the found update strategy
			return (IUpdateStrategy<IBallCmd>) c.newInstance(args); 
			
		} catch (Exception ex) {
			System.out.println("Uh oh! The model had some sort of error when it tried to load Class " + updateStrategyName + ". Here is the exception's message:");
			//This is done instead of just printing ex due to some weird infinite loop behavior we encountered before changing to this
			System.err.println(ex.getMessage()); 
			
			System.out.println("Instead of crashing, we are instead creating an errorStrategyFac's error strategy to use in place of the requested ");
			System.out.println("update strategy, and then continuing as normal (this message was printed from " + this.getClass() + "'s loadUpdateStrategy)");
			return this.errorStrategyFac.makeUpdateStrategy(); 
		}
	}

	/**
	 * Turns the common representation of an update strategy into the formal class name
	 * @param 	className The common name of the update strategy
	 * @return 	A formal version of the classname that can be used by the loader
	 */
	private String fixUpdateStrategyName(String className) {
		return "updatestrategy." + className + "Strategy";
	}

	/**
	 * Turns the common representation of a paint strategy into the formal class name
	 * @param 	className The common name of the paint strategy
	 * @return 	A formal version of the classname that can be used by the loader
	 */
	private String fixPaintStrategyName(String className) {
		return "paintstrategy." + className + "PaintStrategy";
	}

	/**
	 * Get arguments to create a ball from the panel.
	 * 
	 * @param 	pnlCanvas Panel to get information from.
	 * @return 	A Ball that paints on the canvas
	 */
	private Ball makeBall(JPanel pnlCanvas) {

		// Get panel size.
		int panelWidth = pnlCanvas.getWidth();
		int panelHeight = pnlCanvas.getHeight();

		// Create a ball with random position, velocity and color.
		IRandomizer rand = Randomizer.Singleton;

		int radius = rand.randomInt(10, 40);

		int xPos = rand.randomInt(radius, panelWidth - radius);
		int yPos = rand.randomInt(radius, panelHeight - radius);
		int xVel = 0;
		while (xVel == 0) {
			xVel = rand.randomInt(-10, 10);
		}
		int yVel = 0;
		while (yVel == 0) {
			yVel = rand.randomInt(-10, 10);
		}
		Color color = rand.randomColor();

		return new Ball(xPos, yPos, xVel, yVel, radius, color, pnlCanvas);
	}

	/**
	 * Given a paint strategy name, instantiates a Factory to produce such paint strategies
	 * @param 	stratName The name of the strategy we want the factory to produce
	 * @return	An IStrategyFactory that returns the specified kind of paint strategy
	 */
	public IPaintStrategyFac makePaintStrategyFac(final String stratName) {
		if (null == stratName)
			System.out.println("Error: please enter name of the shape!");
		return new IPaintStrategyFac() {
			public IPaintStrategy makePaintStrategy() {
				return loadPaintStrategy(fixPaintStrategyName(stratName));
			}

			public String toString() {
				return stratName;
			}
		};
	}

	/**
	 * Returns an IStrategyFac that can instantiate the strategy specified by
	 * classname. Returns a factory for a beeping error strategy if classname is
	 * null. The toString() of the returned factory is the classname.
	 * 
	 * @param classname
	 *            Shortened name of desired strategy
	 * @return A factory to make that strategy
	 */
	public IUpdateStrategyFac<IBallCmd> makeUpdateStrategyFac(final String classname) {
		if (null == classname)
			return errorStrategyFac;
		return new IUpdateStrategyFac<IBallCmd>() {
			/**
			 * Instantiate a strategy corresponding to the given class name.
			 * 
			 * @return An IUpdateStrategy instance
			 */
			public IUpdateStrategy<IBallCmd> makeUpdateStrategy() {
				// In the case of a switcher, we're going to use the same one
				// for everyone
				if (classname.equals("Switcher")) {
					return new StraightStrategy<IBallCmd>();
				}
				return loadUpdateStrategy(fixUpdateStrategyName(classname));
			}

			/**
			 * Return the given class name string
			 */
			public String toString() {
				return classname;
			}
		};
	}

	/**
	 * Returns an IStrategyFac that can instantiate a MultiStrategy with the two
	 * strategies made by the two given IStrategyFac objects. Returns null if
	 * either supplied factory is null. The toString() of the returned factory
	 * is the toString()'s of the two given factories, concatenated with "-". If
	 * either factory is null, then a factory for a beeping error strategy is
	 * returned.
	 * 
	 * @param stratFac1
	 *            An IStrategyFac for a strategy
	 * @param stratFac2
	 *            An IStrategyFac for a strategy
	 * @return An IStrategyFac for the composition of the two strategies
	 */
	public IUpdateStrategyFac<IBallCmd> combineStrategyFacs(final IUpdateStrategyFac<IBallCmd> stratFac1, 
			final IUpdateStrategyFac<IBallCmd> stratFac2) {
		if (null == stratFac1 || null == stratFac2)
			return errorStrategyFac;
		return new IUpdateStrategyFac<IBallCmd>() {
			/**
			 * Instantiate a new MultiStrategy with the strategies from the
			 * given strategy factories
			 * 
			 * @return A MultiStrategy instance
			 */
			public IUpdateStrategy<IBallCmd> makeUpdateStrategy() {
				return new MultiUpdateStrategy<IBallCmd>(stratFac1.makeUpdateStrategy(), stratFac2.makeUpdateStrategy());
			}

			/**
			 * Return a string that is the toString()'s of the given strategy
			 * factories concatenated with a "-"
			 */
			public String toString() {
				return stratFac1.toString() + "-" + stratFac2.toString();
			}
		};
	}

	/**
	 * Creates a new ball that contains the Switcher Strategy
	 * @param stratFac A factor that produces the strategy the switcher will start with
	 */
	public void loadSwitcherBall(IPaintStrategyFac stratFac) {
		loadBall(switcherStrategy, stratFac.makePaintStrategy());
	}

	/**
	 * Switches the strategy of the switcher strategy to the given strategy
	 * 
	 * @param strategy The strategy to switch to
	 */
	public void switchStrategy(IUpdateStrategy<IBallCmd> strategy) {
		switcherStrategy.setStrategy(strategy);
	}

}
