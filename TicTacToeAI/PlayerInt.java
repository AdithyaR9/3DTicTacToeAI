package TicTacToeAI;

/* PlayerInt - Is an interface all human and computer AIs
 * must implement to function properly in the provided
 * environment. */
public interface PlayerInt
{
	/* Pre: None
	 * Post: Returns the player's letter */
	public char getLetter();

	/* Pre: Receives the current game board
	 * Post: Returns  the player would like to move to */
	public Location getMove(Board board);

	/* Pre: None
	 * Post: Returns the player's name */
	public String getName();
	
	/* Pre: None
	 * Post: Returns a clone of the current object*/
	public PlayerInt freshCopy();
}
