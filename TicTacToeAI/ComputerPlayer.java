package TicTacToeAI;

public class ComputerPlayer implements PlayerInt
{
	private char letter;
	private String name;

	public ComputerPlayer(char letter)
	{
		this.letter = letter;
		name = "Random Computer";
	}

	public char getLetter()
	{
		return letter;
	}

	public String getName()
	{
		return name;
	}

	public Location getMove(Board board)
	{
		try
		{
			System.out.print("Thinking .");
			Thread.sleep(500);
			System.out.print(".");
			Thread.sleep(500);
			System.out.print(".\n");
			Thread.sleep(500);
			
		}
		catch(Exception e)
		{
			System.out.println("Error in Computer Player - getMove: "+e.getMessage());
			e.printStackTrace();
		}
		while(true)
		{
			int sheet = (int) (Math.random()*4);
			int row = (int) (Math.random()*4);
			int col = (int) (Math.random()*4);
			Location l = new Location(col,row,sheet);
			if(board.isEmpty(l))
			{
				return l;
			}
		}
	}
	
	public String toString()
	{
		return name+" - "+letter;
	}
	
	public PlayerInt freshCopy()
	{
		return new ComputerPlayer(letter);
	}
}