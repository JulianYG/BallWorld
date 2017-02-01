package view;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Component;

/**
 * This is the BallGUI under view.
 * @param <TUpdateDropListItem> An item in the dropdown list of the gui relating to updating
 * @param <TPaintDropListItem> An item in the dropdown list of the gui relating to paintin
 */
public class BallGUI<TUpdateDropListItem, TPaintDropListItem> extends JFrame {

	/**
	 * Serial UID of the GUI.
	 */
	private static final long serialVersionUID = 5605141603673158390L;

	/**
	 * Adapter back to the model for control tasks.
	 */
	private IModelControlAdapter<TUpdateDropListItem, TPaintDropListItem> modelControlAdpt;

	/**
	 * Adapter back to the model for control tasks.
	 */
	private IModelUpdateAdapter modelUpdateAdpt;

	/**
	 * The top drop list, used to select what strategy to use in a new ball and
	 * to switch the switcher to.
	 */
	private JComboBox<TUpdateDropListItem> dropDown1 = new JComboBox<>();

	/**
	 * Bottom drop list, used for combining with the top list selection.
	 */
	private JComboBox<TUpdateDropListItem> dropDown2 = new JComboBox<>();

	/**
	 * Drowndown list, used for selecting paint strategies
	 */
	private JComboBox<TPaintDropListItem> paintDropDown = new JComboBox<>();

	/**
	 * Create a special panel with an overridden paintComponent method.
	 */
	private JPanel pnlCanvas = new JPanel() {
		/*
		 * Serial UID of panel.
		 */
		private static final long serialVersionUID = -6952656931251224807L;

		/*
		 * Paint method of the panel.
		 * 
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // clear the panel and redo the background
			modelUpdateAdpt.drawShape(g); // call back to the model to paint the sprites
		}
	};

	/**
	 * The panel with all of control components on it
	 */
	private final JPanel pnlNorth = new JPanel();
	/**
	 * The text box used for entering in the name of new update strategies
	 */
	private final JTextField txtMoveStrategyName = new JTextField("Straight");
	/**
	 * The text box used for entering in the name of new paint strategies
	 */
	private final JTextField txtPaintStrategyName = new JTextField("Ball");
	/**
	 * The button used for clearing the canvas of balls
	 */
	private final JButton btnClearAll = new JButton("Clear All");
	/**
	 * The box containing logic for adding paint strategies
	 */
	private final Box boxAddPaintStrategies = Box.createVerticalBox();
	/**
	 * The button used for adding a paint strategy
	 */
	private final JButton btnAddPaintStrategy = new JButton("Add");
	/**
	 * The box used containing logic for combining update strategies
	 */
	private final Box boxCombiner = Box.createVerticalBox();
	/**
	 * The button used for making a ball
	 */
	private final JButton btnMakeSelectedBall = new JButton("Make Selected Shape");
	/**
	 * The button used for adding an update strategy to the lists
	 */
	private final JButton btnAddToLists = new JButton("Add to lists");
	/**
	 * The box containing logic for adding new update strategies
	 */
	private final Box boxAddMoveStrategies = Box.createVerticalBox();
	/**
	 * The button used to combine two strategies
	 */
	private final JButton btnCombine = new JButton("Combine");
	/**
	 * The box containing logic for swtiching
	 */
	private final Box switcherBox = Box.createVerticalBox();
	/**
	 * The button used to make a new switcher object
	 */
	private final JButton btnMakeSwitcher = new JButton("Make Switcher");
	/**
	 * The button used to switch all existing switchers
	 */
	private final JButton btnSwitch = new JButton("Switch");

	/**
	 * Constructor is supplied with an instance of the model adapter.
	 * @param modelCtrlAdpt The adapter for controlling the model
	 * @param modelUpdateAdpt The adapter for updating the model
	 */
	public BallGUI(IModelControlAdapter<TUpdateDropListItem, TPaintDropListItem> modelCtrlAdpt, IModelUpdateAdapter modelUpdateAdpt) {
		this.modelControlAdpt = modelCtrlAdpt;
		this.modelUpdateAdpt = modelUpdateAdpt;
		initGUI();
	}

	/**
	 * Initiates GUI.
	 */
	private void initGUI() {

		/*
		 * Add Panels and set element values
		 */
		getContentPane().add(pnlNorth, BorderLayout.NORTH);
		getContentPane().add(pnlCanvas, BorderLayout.CENTER);

		pnlCanvas.setBackground(Color.PINK);

		pnlNorth.setBackground(Color.DARK_GRAY);
		boxAddMoveStrategies.setBackground(Color.BLUE);
		pnlNorth.add(boxAddMoveStrategies);
		boxCombiner.setBackground(Color.PINK);
		pnlNorth.add(boxCombiner);
		switcherBox.setBackground(Color.CYAN);
		pnlNorth.add(switcherBox);
		pnlNorth.add(boxAddPaintStrategies);
		pnlNorth.add(btnClearAll);

		switcherBox.add(btnSwitch);
		switcherBox.add(btnMakeSwitcher);

		boxAddPaintStrategies.add(txtPaintStrategyName);
		boxAddPaintStrategies.add(btnAddPaintStrategy);
		paintDropDown.setToolTipText("Select the paint strategy to use here!");
		boxAddPaintStrategies.add(paintDropDown);
		btnAddPaintStrategy.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnSwitch.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSwitch.setToolTipText("Switch the strategy on all switcher shapes to the currently selected strategy in the top droplist.");
		btnMakeSwitcher.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnMakeSwitcher.setToolTipText("Instantiate a shape that can switch strategies.");
		txtMoveStrategyName.setToolTipText("Enter the moving strategy of the shape. Default is Straight.");
		btnClearAll.setToolTipText("Clear all the shapes from the screen.");
		txtMoveStrategyName.setHorizontalAlignment(SwingConstants.LEFT);
		txtMoveStrategyName.setText("Straight");
		txtMoveStrategyName.setColumns(10);

		boxAddMoveStrategies.add(txtMoveStrategyName);
		btnAddToLists.setToolTipText("Add the moving strategy to drop down lists.");
		boxAddMoveStrategies.add(btnAddToLists);

		btnAddToLists.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnMakeSelectedBall.setToolTipText("Make the shape selected in top drop down list on the canvas.");

		boxCombiner.add(btnMakeSelectedBall);
		dropDown1.setToolTipText("Select a strategy for creating or combining here!");
		boxCombiner.add(dropDown1);
		dropDown2.setToolTipText("Select a strategy for combining here!");
		boxCombiner.add(dropDown2);
		boxCombiner.add(btnCombine);

		btnMakeSelectedBall.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCombine.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCombine.setToolTipText("Combine the selected strategies from both drop down lists and generate a new combined strategy.");
		txtPaintStrategyName.setToolTipText("Enter the desired shape of painting strategy. Default is Ball");
		btnAddPaintStrategy.setToolTipText("Add the painting strategy into the drop list below");

		/*
		 * Action Listeners
		 */
		btnAddToLists.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TUpdateDropListItem o = modelControlAdpt.addUpdateStrategy(txtMoveStrategyName.getText());
				if (null == o)
					return; // just in case

				dropDown1.insertItemAt(o, 0);
				dropDown2.insertItemAt(o, 0);
				dropDown1.setSelectedIndex(0);
				dropDown2.setSelectedIndex(0);
			}
		});

		/*
		 * Button to add paint strategy
		 */
		btnAddPaintStrategy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TPaintDropListItem o = modelControlAdpt.addPaintStrategyFac(txtPaintStrategyName.getText());
				if (null == o)
					return; // just in case
				paintDropDown.insertItemAt(o, 0);
				paintDropDown.setSelectedIndex(0);
			}
		});

		/*
		 * Bind button to Make Ball
		 */
		btnMakeSelectedBall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modelControlAdpt.makeShape(dropDown1.getItemAt(dropDown1.getSelectedIndex()),
						paintDropDown.getItemAt(paintDropDown.getSelectedIndex()));
			}
		});

		/*
		 * Bind button to combine strategies
		 */
		btnCombine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TUpdateDropListItem o = modelControlAdpt.combineUpdateStrategies(dropDown1.getItemAt(dropDown1.getSelectedIndex()),
						dropDown2.getItemAt(dropDown2.getSelectedIndex()));
				dropDown1.insertItemAt(o, 0);
			}
		});

		/*
		 * Bind button to Clear all balls if "ClearAll" button is clicked.
		 */
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelControlAdpt.clearAllShapes();
			}
		});

		/*
		 * Bind button to Make new switcher balls
		 */
		btnMakeSwitcher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelControlAdpt.makeSwitcherShape(dropDown1.getItemAt(dropDown1.getSelectedIndex()),
						paintDropDown.getItemAt(paintDropDown.getSelectedIndex()));
			}
		});

		/*
		 * Bind button to Switch strategy
		 */
		btnSwitch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelControlAdpt.switchUpdateStrategy(dropDown1.getItemAt(dropDown1.getSelectedIndex()));
			}
		});
	}

	/**
	 * Starts the GUI
	 */
	public void start() {
		setSize(900, 600); /* set the frame size to be larger than the default. */
		setVisible(true); /* set the frame to be visible. */
	}

	/**
	 * Update the GUI.
	 */
	public void update() {
		pnlCanvas.repaint();
	}

	/**
	 * Get the Canvas Panel
	 * @return The canvas that shapes are to be drawn on
	 */
	public JPanel getPnlCanvas() {
		return pnlCanvas;
	}

}