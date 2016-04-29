import javax.swing.JLabel;

public class TurnCounterLabel extends JLabel
{
	// data fields
	private int numTurns = 0;
	private final String DESCRIPTION = "Turns: ";
	
	/**
	 * Update the text label with the current counter value
	*/
	private void update()
	{
		setText(DESCRIPTION + Integer.toString(this.numTurns));
	}
	
	/**
	 * Default constructor, starts counter at 0
	*/
	public TurnCounterLabel()
	{
		super();
		reset();
	}
	
	/**
	 * Increments the counter and updates the text label
	*/
	public void increment()
	{
		this.numTurns++;
		update();
	}
	
	/**
	 * Resets the counter to zero and updates the text label
	*/
	public void reset()
	{
		this.numTurns = 0;
		update();
	}
}
