package controller;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.BallModel;
import model.IBallCmd;
import model.IModel2ViewAdapter;
import paintstrategy.IPaintStrategyFac;
import updatestrategy.IUpdateStrategyFac;
import view.*;

/**
 * Controller class that controls the interactions between model and view.
 */
public class Controller {
	/**
	 * The ball model.
	 */
	private BallModel model;
	/**
	 * The ball view.
	 */
	private BallGUI<IUpdateStrategyFac<IBallCmd>, IPaintStrategyFac> view;

	/**
	 * Controller constructor.
	 */
	public Controller() {
		// Here the model is shown being constructed first then the view but it
		// could easily be the other way around if needs dictated it.
		// set the model field

		model = new BallModel(new IModel2ViewAdapter() {

			@Override
			// Update the view.
			public void update() {
				view.update();
			}

			public JPanel getPnlCanvas() {
				return view.getPnlCanvas();
			}
		});

		// set the view field
		// other code elided.

		view = new BallGUI<IUpdateStrategyFac<IBallCmd>, IPaintStrategyFac>(new IModelControlAdapter<IUpdateStrategyFac<IBallCmd>, IPaintStrategyFac>() {

			@Override
			/**
			 * Returns an IStrategyFac that can instantiate the strategy
			 * specified by classname. Returns null if classname is null. The
			 * toString() of the returned factory is the classname.
			 * 
			 * @param classname
			 *            Shortened name of desired strategy
			 * @return A factory to make that strategy
			 */
			public IUpdateStrategyFac<IBallCmd> addUpdateStrategy(String classname) {
				return model.makeUpdateStrategyFac(classname);
			}

			@Override
			/**
			 * Add a ball to the system with a strategy as given by the given
			 * IStrategyFac
			 * 
			 * @param selectedItem
			 *            The fully qualified name of the desired strategy.
			 */
			public void makeShape(IUpdateStrategyFac<IBallCmd> selectedUpdateStrat, IPaintStrategyFac selectedPaintStrat) {
				if (null != selectedUpdateStrat && null != selectedPaintStrat) {
					try {
						model.loadBall(selectedUpdateStrat.makeUpdateStrategy(), selectedPaintStrat.makePaintStrategy()); 
					} catch (Exception e) {
						System.out.println("Uh oh! The model failed to load a ball! Below this line is the runtime exception (also, look above to see if something happened lower down the chain): ");
						e.printStackTrace();
						System.err.flush();
						System.out.flush();
						//Extra prints to decrease likelihood that the error hasn't finished printing yet
						//Possibly look into a better way to do this?
						System.out.println("				");
						System.out.println("				");
						System.out.flush();
						System.out.println("				");
						System.out.println("Possibly try again with a different strategy name? (We didn't crash, don't worry!) Continuing normal execution now...");
					}
				} else {
					//This block will fire if either strategy is null, which could happen if the user typed in a name incorrectly
					System.out.println("The controller was about to ask the model to load a ball with a null update or paint strategy! Good thing we didn't continue!");
					System.out.println("Check you correctly selected the neccessary strategies. For now, we have deliberately decided to just do nothing intead of crashing");
				}
			
			}

			@Override
			/**
			 * Returns an IStrategyFac that can instantiate a MultiStrategy with
			 * the two strategies made by the two given IStrategyFac objects.
			 * Returns null if either supplied factory is null. The toString()
			 * of the returned factory is the toString()'s of the two given
			 * factories, concatenated with "-". *
			 * 
			 * @param selectedItem1
			 *            An IStrategyFac for a strategy
			 * @param selectedItem2
			 *            An IStrategyFac for a strategy
			 * @return An IStrategyFac for the composition of the two strategies
			 */
			public IUpdateStrategyFac<IBallCmd> combineUpdateStrategies(IUpdateStrategyFac<IBallCmd> selectedItem1, 
					IUpdateStrategyFac<IBallCmd> selectedItem2) {
				return model.combineStrategyFacs(selectedItem1, selectedItem2);
			}

			/**
			 * Tells the model to clear all shapes from the screen
			 */
			@Override
			public void clearAllShapes() {
				model.clearAllBalls();
			}

			/**
			 * Tells the model to switch the switcher strategy to that produced
			 * by the given IUpdateFactory
			 */
			@Override
			public void switchUpdateStrategy(IUpdateStrategyFac<IBallCmd> selectedItem) {
				model.switchStrategy(selectedItem.makeUpdateStrategy());
			}

			/**
			 * Gets an IPaintStrategyFac from the model that produces Strategies
			 * of the given name
			 */
			@Override
			public IPaintStrategyFac addPaintStrategyFac(String shapename) {
				return model.makePaintStrategyFac(shapename);
			}

			/**
			 * Tells the model to load a switcher ball that has the given paint strategy
			 */
			@Override
			public void makeSwitcherShape(IUpdateStrategyFac<IBallCmd> selectedUpdateStrat, IPaintStrategyFac selectedPaintStrat) {
				if (null != selectedUpdateStrat && null != selectedPaintStrat) {
					model.loadSwitcherBall(selectedPaintStrat);
				}
			}
		}, new IModelUpdateAdapter() {
			/**
			 * Pass the update request to the model.
			 * 
			 * @param g
			 *            The Graphics object the balls use to draw themselves.
			 */
			public void drawShape(Graphics g) {
				model.update(g);
			}
		});
	}

	/**
	 * Start the model and view.
	 */
	public void start() {
		// Initiating model and view.
		model.start();
		view.start();
	}

	/**
	 * Initializes the controller
	 * 
	 * @param args This is not used
	 */
	public static void main(String[] args) {
		// Initiating controller.
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Controller controller = new Controller(); 
					// instantiate the system
					controller.start(); // start the system
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
