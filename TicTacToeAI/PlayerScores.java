package TicTacToeAI;

/*PlayerScore - Stores the following match data for players
 * wins / losses / cats */
public class PlayerScores
{
	private int wins 	= 0;
	private int losses	= 0;
	private int cats	= 0;
	private final int POINTS_FOR_CAT 	= 1;
	private final int POINTS_FOR_WIN 	= 2;
	private final int POINTS_FOR_LOSS	= 0;
	
	/* Pre:		None
	 * Post: 	sets wins, lossses, cats to 0 */
	public PlayerScores()
	{}
	
	/* Pre:		None
	 * Post: 	adds one to the wins variable */
	public void addWin()
	{
		wins++;	
	}
	
	/* Pre:		None
	 * Post: 	adds one to the losses variable */
	public void addLoss()
	{
		losses++;
	}
	
	/* Pre:		None
	 * Post: 	adds one to the cats variable */
	public void addCat()
	{
		cats++;	
	}
	
	/* Pre:		None
	 * Post: 	returns the value of wins */
	public int getWins()
	{
		return wins;	
	}
	
	/* Pre:		None
	 * Post: 	returns the value of losses */
	public int getLosses()
	{
		return losses;	
	}
	
	/* Pre:		None
	 * Post: 	returns the value of cats */
	public int getCats()
	{
		return cats;	
	}
	
	/* Pre:		None
	 * Post: 	returns the score caluclated with wins, cats, losses 
	 *	losses are worth 0, cats are worth 1 and wins are worth 2*/
	public int totalScore()
	{
		return POINTS_FOR_WIN*wins + POINTS_FOR_CAT*cats + POINTS_FOR_LOSS*losses;	
	}
}