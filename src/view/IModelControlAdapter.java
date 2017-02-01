package view;

/**
 * Adapter that the view uses to communicate to the model for non-repetitive control tasks such as manipulating strategies.
 * @author swong
 * @param <TUpdateDropListItem> An item in the dropdown list of the gui relating to updating
 * @param <TPaintDropListItem> An item in the dropdown list of the gui relating to paintin
 *
 */
public interface IModelControlAdapter<TUpdateDropListItem, TPaintDropListItem> {

	/**
	 * Take the given short strategy name and return a corresponding something to put onto both drop lists.
	 * @param classname  The shortened class name of the desired strategy
	 * @return Something to put onto both the drop lists.
	 */
	public TUpdateDropListItem addUpdateStrategy(String classname);

	/**
	 * Gets an IPaintStrategyFac from the model that produces Strategies
	 * of the given name
	 * @param shapename The paint strategy to be produced 
	 * @return The IPaintStrategyFac that produces the given paint strategy
	 */
	public TPaintDropListItem addPaintStrategyFac(String shapename);

	/**
	 * Make a ball with the selected short strategy name.
	 * @param selectedUpdateStrategy An update strategy factory for the ball
	 * @param selectedShapeFac a paint strategy factory for the ball
	 */
	public void makeShape(TUpdateDropListItem selectedUpdateStrategy, TPaintDropListItem selectedShapeFac);

	/**
	 * Return a new object to put on both lists, given two items from the lists.
	 * @param selectedItem1  An object from one drop list
	 * @param selectedItem2 An object from the other drop list
	 * @return An object to put back on both lists.
	 */
	public TUpdateDropListItem combineUpdateStrategies(TUpdateDropListItem selectedItem1, TUpdateDropListItem selectedItem2);

	/**
	 * Tells the model to clear all of the balls on the panel.
	 */
	public void clearAllShapes();

	/**
	 * Makes a ball with the Switcher strategy
	 * @param selectedUpdateStrategy An update strategy factory for the ball
	 * @param selectedShapeFac a paint strategy factory for the ball
	 */
	public void makeSwitcherShape(TUpdateDropListItem selectedUpdateStrategy, TPaintDropListItem selectedShapeFac);

	/**
	 * Switches the composed strategy of the SwitcherStrategy to selectedItem
	 * @param selectedItem An item from one drop list
	 */
	public void switchUpdateStrategy(TUpdateDropListItem selectedItem);

}
