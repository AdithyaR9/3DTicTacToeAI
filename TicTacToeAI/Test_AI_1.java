package TicTacToeAI;

import java.util.Random;

public class Test_AI_1 implements PlayerInt
{
	private char letter;
	private String name;

	// Pre:		receives a char letter
	// Post: 	sets the name to "Random AI" and the letter to the letter received
	Test_AI_1(char letter)
	{
		name 		= "Random AI";
		this.letter	= letter;
	}

	public char getLetter()
	{
		return letter;
	}

	// Pre:		method is called
	// Post: 	returns the Location where the player wants to move
	public Location getMove(Board board)
	{
		Location l;
		Random rand = new Random();
		do
		{
			l = new Location(rand.nextInt(4),rand.nextInt(4),rand.nextInt(4));
		}while(!board.isEmpty(l));
		return l;
	}

	// Pre:		method is called
	// Post: 	returns the name of the player
	public String getName()
	{
		return name;
	}
	
	public PlayerInt freshCopy()
	{
		return new Test_AI_1(letter);
	} 
}