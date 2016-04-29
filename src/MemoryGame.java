import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class MemoryGame implements ActionListener
{
	// data fields
	private JFrame mainFrame;					// top level window
	private Container mainContentPane;			// frame that holds card field and turn counter
	private TurnCounterLabel turnCounterLabel;
	private ImageIcon cardIcon[]; // 0-7 are faces, 8 is back
	private String ImageDirectory="default";
	
	/**
	 * Make a JMenuItem, associate an action command and listener, add to menu
	*/
	private static void newMenuItem(String text, JMenu menu, ActionListener listener)
	{
		JMenuItem newItem = new JMenuItem(text);
		newItem.setActionCommand(text);
		newItem.addActionListener(listener);
		menu.add(newItem);
	}
	
	/**
	 * Loads the card icons from GIF files
	 *
	 * @return ImageIcon[9] containing the card icons
	*/
	private ImageIcon[] loadCardIcons()
	{
		// allocate array to store icons
		ImageIcon icon[] = new ImageIcon[9];
		// for each icon
		for(int i = 0; i < 9; i++ )
		{
			// make a new icon from a cardX.gif file
			String fileName = "images/"+ImageDirectory+"/card" + i + ".jpg";
			icon[i] = new ImageIcon(fileName);
			// unable to load icon
			if(icon[i].getImageLoadStatus() == MediaTracker.ERRORED)
			{
				// inform the user of the error and then quit
				JOptionPane.showMessageDialog(this.mainFrame
					, "The image " + fileName + " could not be loaded."
					, "Matching Game Error", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
		return icon;
	}
	
	/**
	 * Default constructor loads card images, makes window
	*/
	public MemoryGame()
	{
		// make toplevel window
		this.mainFrame = new JFrame("Matching Game");
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setSize(400,500);
		this.mainContentPane = this.mainFrame.getContentPane();
		this.mainContentPane.setLayout(new BoxLayout(this.mainContentPane, BoxLayout.PAGE_AXIS)); 
		// make counter label
		this.turnCounterLabel = new TurnCounterLabel();
		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		this.mainFrame.setJMenuBar(menuBar);
		// Game menu
		JMenu gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		newMenuItem("New Game", gameMenu, this);
		newMenuItem("Exit", gameMenu, this);


		this.cardIcon = loadCardIcons(); 
	}
	
	/**
	 * Handles menu events.  Necessary for implementing ActionListener.
	 *
	 * @param e object with information about the event
	*/
	public void actionPerformed(ActionEvent e)
	{
		dprintln("actionPerformed " + e.getActionCommand());
		if(e.getActionCommand().equals("New Game")) newGame();
		else if(e.getActionCommand().equals("Exit")) System.exit(0);
	}
	
	/**
	 * Prints debugging messages to the console
	 *
	 * @param message the string to print to the console
	 */
	static public void dprintln( String message )
	{
		//System.out.println( message );
	}
	
	/**
	 * Randomize the order of elements in an int array
	 *
	 * The method iterates over the array.  For each position in the array,
	 * another position is chosen randomly and the two integer values are
	 * exchanged.
	 *
	 * @param a the int[] to randomize
	*/
	public static void randomizeIntArray(int[] a)
	{
		Random randomizer = new Random();
		// iterate over the array
		for(int i = 0; i < a.length; i++ )
		{
			// choose a random int to swap with
			int d = randomizer.nextInt(a.length);
			// swap the entries
			int t = a[d];
			a[d] = a[i];
			a[i] = t;
		}
	}
	
	/**
	 * Makes a new set of cards and puts them in a new card field (a JPanel)
	 *
	 * @return new panel populated with new cards
	*/
	public JPanel makeCards()
	{
		// make the panel to hold all of the cards
		JPanel panel = new JPanel(new GridLayout(4, 4));
		// this set of cards must have their own manager
		TurnedCardManager turnedManager = new TurnedCardManager(this.turnCounterLabel);
		// all cards have same back
		ImageIcon backIcon = this.cardIcon[8];
		
		// make an array of card numbers: 0, 0, 1, 1, 2, 2, ..., 7, 7
		int cardsToAdd[] = new int[16];
		for(int i = 0; i < 8; i++)
		{
			cardsToAdd[2*i] = i;
			cardsToAdd[2*i + 1] = i;
		}
		// randomize the order of the array
		randomizeIntArray(cardsToAdd);
		
		// make each card object
		for(int i = 0; i < cardsToAdd.length; i++)
		{
			// number of the card, 0-7, randomized
			int num = cardsToAdd[i];
			// make the card object
			Card newCard = new Card(turnedManager, this.cardIcon[num], backIcon, num);
			// add it to the panel
			panel.add(newCard);
		}
		// return the filled panel
		return panel;
	}
	
	/**
	 * Prepares a new game (first game or non-first game)
	*/
	public void newGame()
	{
		// reset the turn counter to zero
		this.turnCounterLabel.reset();
		// clear out the content pane (removes turn counter label and card field)
		this.mainContentPane.removeAll();
		// make a new card field with cards, and add it to the window
		this.mainContentPane.add(makeCards());
		// add the turn counter label back in again
		this.mainContentPane.add(this.turnCounterLabel);
		// show the window (in case this is the first game)
		this.mainFrame.setVisible(true);
	}
}
