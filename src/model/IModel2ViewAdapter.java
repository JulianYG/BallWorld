package model;

import javax.swing.JPanel;

/**
 * Interface that goes from the model to the view that enables the model to talk to the view
 */
public interface IModel2ViewAdapter {

	/**
	 * The method that tells the view to update
	 */
	public void update();

	/**
	 * Gets the JPanel that the shapes are drawn on
	 * @return 	A JPanel that the shapes are drawn on
	 */
	public JPanel getPnlCanvas();

	/**
	 * No-op "null" adapter
	 * See the web page on the Null Object Design Pattern at http://cnx.org/content/m17227/latest/
	 */
	public static final IModel2ViewAdapter NULL_OBJECT = new IModel2ViewAdapter() {
		public void update() {
		}

		@Override
		public JPanel getPnlCanvas() {
			return null;
		}
	};
}
