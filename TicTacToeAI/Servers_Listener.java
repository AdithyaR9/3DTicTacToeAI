package TicTacToeAI;

import java.io.*;
import java.util.ArrayList;

public class Servers_Listener implements Runnable
{
	// Streams
	private ObjectInputStream is;
	private ObjectOutputStream os;
	
	// board
	private Board board= new Board();;
	
	// selected opponent
	private PlayerInt opponentAsX;
	private PlayerInt opponentAsO;
	
	// X AIs by category
	private ArrayList<PlayerInt> testingAIsAsX = new ArrayList<PlayerInt>();
	private ArrayList<PlayerInt> fourthPeriodAIsAsX = new ArrayList<PlayerInt>();
	private ArrayList<PlayerInt> seventhPeriodAIsAsX = new ArrayList<PlayerInt>();
	
	// X AIs by category
	private ArrayList<PlayerInt> testingAIsAsO = new ArrayList<PlayerInt>();
	private ArrayList<PlayerInt> fourthPeriodAIsAsO = new ArrayList<PlayerInt>();
	private ArrayList<PlayerInt> seventhPeriodAIsAsO = new ArrayList<PlayerInt>();
	
	// Category Names
	private ArrayList<String> categories = new ArrayList<String>();
	private int numberOfGameToPlay = 10;
	private Command_From_Server commandFromSerever = null;
	private String playerName;
	
	
	public Servers_Listener(ObjectOutputStream os, ObjectInputStream is)
	{
		this.is			= is;
		this.os			= os;
		
		// Adds testing AIs
		testingAIsAsX.add(new Test_AI_1('X'));
		testingAIsAsO.add(new Test_AI_1('O'));
		// Adds fourth Period AIs
		
		// Adds Seventh Period AIs
		
		// Load Categories
		categories.add("Test AIs");
		//categories.add("Fourth Period");
		//categories.add("Seventh Period");
	}

	public void run()
	{
		try
		{
			//System.out.println("AI Connection");
			while(true)
			{
				Command_To_Server bigCommand = (Command_To_Server)is.readObject();
				if(bigCommand.getCommand() == Command_To_Server.NEW_MATCH)
				{
					playerName = (String) bigCommand.getCommandData();
					//System.out.println("AI Name is" + playerName);
					
					commandFromSerever = new Command_From_Server(Command_From_Server.CATEGORY_SELECTION,categories);
					os.writeObject(commandFromSerever);
					os.reset();
					
					
					Command_To_Server categoryCommand = (Command_To_Server)is.readObject();
					int categoryIndex =(Integer) categoryCommand.getCommandData();
					ArrayList<String> names = new ArrayList<String>();
					//System.out.println("Category Number" +categoryIndex);
					
					if(categoryIndex == 0)
					{
						for(PlayerInt p: testingAIsAsX)
						{
							names.add(p.getName());
						}
						
						commandFromSerever = new Command_From_Server(Command_From_Server.AI_SELECTION,names);
						os.writeObject(commandFromSerever);
						os.reset();
						
						Command_To_Server pickedAI = (Command_To_Server)is.readObject();
						int ai_Index =(Integer) pickedAI.getCommandData();
						//System.out.println("ai Number " +ai_Index);
						
						if(ai_Index >= testingAIsAsX.size())
						{
							System.out.println("Bad AI Index");
							break;
						}
						else
						{
							opponentAsX = testingAIsAsX.get(ai_Index).freshCopy();
							opponentAsO = testingAIsAsO.get(ai_Index).freshCopy();	
						}
					}
					else if(categoryIndex == 1)
					{
						//System.out.println("Category not implemented");
						break;
						
					}
					else if(categoryIndex == 2)
					{
						//System.out.println("Category not implemented");
						break;
					
					}
					else
					{
						//System.out.println("Bad Category");
						break;
					}
					
					Command_From_Server a = new Command_From_Server(Command_From_Server.START_PLAYER_FIRST_GAMES,opponentAsO.getName());
					os.writeObject(a);
					os.reset();
					
					for(int x = 0; x<numberOfGameToPlay; x++)
						playGame(true);
					
					commandFromSerever = new Command_From_Server(Command_From_Server.START_PLAYER_SECOND_GAMES,opponentAsO.getName());
					os.writeObject(commandFromSerever);
					os.reset();
					
					for(int x = 0; x<numberOfGameToPlay; x++)
						playGame(false);
						
					commandFromSerever = new Command_From_Server(Command_From_Server.MATCHES_COMPLETE,opponentAsO.getName());
					os.writeObject(commandFromSerever);
					os.reset();	
						
					break;
				}
			}
		}
		catch(Exception e)
		{
			//System.out.println("Error in Server's Listener: "+ e.getMessage());
			//e.printStackTrace();
		}
		
	}
	
	public void playGame(boolean playerFirst)
	{
		//System.out.println("In play game");
		try
		{
			board.reset();
			//Location l;
		 	boolean firstPlayersTurn = true;
			
			if(playerFirst)
			{
				while(true)
				{
					if(!board.isWinner('X') && !board.isWinner('X') && !board.isCat())
					{
						if(firstPlayersTurn)
						{
							commandFromSerever = new Command_From_Server(Command_From_Server.MAKE_MOVE);
							os.writeObject(commandFromSerever);
							os.reset();
							
							Command_To_Server categoryCommand = (Command_To_Server)is.readObject();
							Location l =(Location) categoryCommand.getCommandData();
							//System.out.println("Visitor moved to  "+l);
					
							if(l!=null &&board.isEmpty(l))
							{
								board.setLocation(l,'X');
								commandFromSerever = new Command_From_Server(Command_From_Server.SUCCESSFUL_MOVE,l);
								os.writeObject(commandFromSerever);
								os.reset();
							}
							else
							{
								//System.out.println(playerName + " failed to move.");
								commandFromSerever = new Command_From_Server(Command_From_Server.FAILED_MOVE);
								os.writeObject(commandFromSerever);
								os.reset();
							}
							
							firstPlayersTurn = false;
						}
						else
						{
							Location l=opponentAsO.getMove(new Board(board));
							if(board.isEmpty(l))
							{
								board.setLocation(l,'O');
								commandFromSerever = new Command_From_Server(Command_From_Server.OPPONENT_MOVE,l);
								os.writeObject(commandFromSerever);
								os.reset();
								//System.out.println("Home AI moved to "+ l);
							}
							else
							{
								//System.out.println(opponentAsO.getName()+" failed to move.");
								commandFromSerever = new Command_From_Server(Command_From_Server.OPPONENT_FAILED_TO_MOVE);
								os.writeObject(commandFromSerever);
								os.reset();
							}
								
							firstPlayersTurn = true;
						}	
						
					}
					if(board.isWinner('X'))
					{
						//System.out.println("X Wins");
						commandFromSerever = new Command_From_Server(Command_From_Server.WIN);
						os.writeObject(commandFromSerever);
						os.reset();
						return;
					}
					else if(board.isWinner('O'))
					{
						//System.out.println("O Wins");
						commandFromSerever = new Command_From_Server(Command_From_Server.LOSE);
						os.writeObject(commandFromSerever);
						os.reset();
						return;
					}
					else if(board.isCat())
					{
						//System.out.println("Cat");
						commandFromSerever = new Command_From_Server(Command_From_Server.TIE);
						os.writeObject(commandFromSerever);
						os.reset();
						return;
					}
				}
			}
			else
			{
				while(true)
				{
					if(!board.isWinner('X') && !board.isWinner('X') && !board.isCat())
					{
						if(firstPlayersTurn)
						{
							Location l=opponentAsX.getMove(new Board(board));
							if(board.isEmpty(l))
							{
								board.setLocation(l,'X');
								commandFromSerever = new Command_From_Server(Command_From_Server.OPPONENT_MOVE,l);
								os.writeObject(commandFromSerever);
								os.reset();
							}
							else
							{
								commandFromSerever = new Command_From_Server(Command_From_Server.OPPONENT_FAILED_TO_MOVE);
								os.writeObject(commandFromSerever);
								os.reset();
							}
							
							firstPlayersTurn = false;
						}
						else
						{
							commandFromSerever = new Command_From_Server(Command_From_Server.MAKE_MOVE);
							os.writeObject(commandFromSerever);
							os.reset();
							
							Command_To_Server categoryCommand = (Command_To_Server)is.readObject();
							Location l =(Location) categoryCommand.getCommandData();
					
							if(l!=null &&board.isEmpty(l))
							{
								board.setLocation(l,'O');
								commandFromSerever = new Command_From_Server(Command_From_Server.SUCCESSFUL_MOVE,l);
								os.writeObject(commandFromSerever);
								os.reset();
							}
							else
							{
								commandFromSerever = new Command_From_Server(Command_From_Server.FAILED_MOVE);
								os.writeObject(commandFromSerever);
								os.reset();
							}
							/**/
								
							firstPlayersTurn = true;
						}	
						
					}
					
					if(board.isWinner('X'))
					{
						//System.out.println("X Wins");
						commandFromSerever = new Command_From_Server(Command_From_Server.LOSE);
						os.writeObject(commandFromSerever);
						os.reset();
						return;
					}
					else if(board.isWinner('O'))
					{
						//System.out.println("O Wins");
						commandFromSerever = new Command_From_Server(Command_From_Server.WIN);
						os.writeObject(commandFromSerever);
						os.reset();
						return;
					}
					else if(board.isCat())
					{
						//System.out.println("Cat");
						commandFromSerever = new Command_From_Server(Command_From_Server.TIE);
						os.writeObject(commandFromSerever);
						os.reset();
						return;
					}
				}
			}
		}
		catch(Exception e)
		{ 
			//System.out.println("Crashed While Playing a Game");
			//e.printStackTrace();
		}
		
	}
}


