package TicTacToeAI;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.*;

public class ClientMain
{
	public static final String ip = "127.0.0.1";
	public static final int port = 8003;
	
	public static int moveSleepTime 		= 250;
	public static int endSleepTime 			= 1000;
	public static int matchInfoSleepTime 	= 1000;
	public static PlayerScores scores=null;
	
	
	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		
		Board board=new Board();
		Location l;
		DisplayScreenV_AI ds = new DisplayScreenV_AI(board);
		int mode = 0;
		
		PlayerInt myAIasX = new Move('X');
		PlayerInt myAIasO = new Move('O');
		String myAI_Name = myAIasX.getName();
		String opponentName = "";
		PlayerInt currentlyPlaying = null;
		
		while(true)
		{
			do
			{
				System.out.println("-Test Type-");
				System.out.println("1. AI vs Testers");
				System.out.println("2. Exit");
				System.out.print("Enter selection: ");
				mode = keyboard.nextInt();
			}while(mode < 1 || mode > 2);
			
			if(mode == 1)
			{
				scores = new PlayerScores();
				try
				{
				
					Socket connectionToServer = new Socket(ip,port);
				
					ObjectInputStream is = new 
						ObjectInputStream(connectionToServer.getInputStream());
							
					ObjectOutputStream os = new 
						ObjectOutputStream(connectionToServer.getOutputStream());
	
					board.reset();
					while(true)
					{
						os.writeObject(new Command_To_Server(Command_To_Server.NEW_MATCH,myAI_Name));
						os.reset();
						
						Command_From_Server categoriesFromSever = (Command_From_Server)is.readObject();
						ArrayList<String> categories = (ArrayList<String>)categoriesFromSever.getCommandData();
						
						int spot = 0;
						
						do
						{
							System.out.println("\n-Select an AI Category-");
							for(int x = 0; x<categories.size();x++)
							{
								System.out.println(x+"."+ categories.get(x));	
							}
							System.out.print("Enter selection:");
							spot =keyboard.nextInt();
						}while(spot>=categories.size());
						
						os.writeObject(new Command_To_Server(Command_To_Server.SELECT_CATEGORY,spot));
						os.reset();
						
						Command_From_Server aiListFromSever = (Command_From_Server)is.readObject();
						ArrayList<String> aiList = (ArrayList<String>)aiListFromSever.getCommandData();
						
						spot = 0;
						
						do
						{
							System.out.println("\n-Select an AI-");
							for(int x = 0; x<aiList.size();x++)
							{
								System.out.println(x+"."+ aiList.get(x));	
							}
							System.out.print("Enter selection:");
							spot = keyboard.nextInt();
						}while(spot>=aiList.size());
						
						os.writeObject(new Command_To_Server(Command_To_Server.SELECT_AI,spot));
						os.reset();
						opponentName = aiList.get(spot);
						
						
						// play full games list
						while(true)
						{
							Command_From_Server comFromServer = (Command_From_Server)is.readObject();
							//System.out.println("com from server"+ comFromServer.getCommand());
							
							if(comFromServer.getCommand()==Command_From_Server.START_PLAYER_FIRST_GAMES)
							{
								board.reset();
								currentlyPlaying = myAIasX;
								System.out.println("\n\n***"+myAIasX.getName()+ " is playing as Red vs "+ opponentName+ "*** ");
								Thread.sleep(matchInfoSleepTime);
							}
							else if(comFromServer.getCommand()==Command_From_Server.START_PLAYER_SECOND_GAMES)
							{
								board.reset();
								currentlyPlaying = myAIasO;
								System.out.println("\n\n***"+opponentName+ " is playing as Red vs "+myAIasX.getName() + "*** ");
								Thread.sleep(matchInfoSleepTime);
							}
							else if(comFromServer.getCommand()==Command_From_Server.SUCCESSFUL_MOVE)
							{
								l=(Location)comFromServer.getCommandData();
								board.setLocation(l,currentlyPlaying.getLetter());
								//System.out.println("I Really went to "+l);
								Thread.sleep(moveSleepTime);
							}
							else if(comFromServer.getCommand()==Command_From_Server.FAILED_MOVE)
							{
								System.out.println("Your AI failed to provide a valid move!!!");
								Thread.sleep(moveSleepTime);
								
							}
							else if(comFromServer.getCommand()==Command_From_Server.OPPONENT_MOVE)
							{
								//System.out.println("Comp moved to "+(Location)comFromServer.getCommandData());
								board.setLocation((Location)comFromServer.getCommandData(),getOpponentLetter(currentlyPlaying.getLetter()));
								Thread.sleep(moveSleepTime);
								
							}
							else if(comFromServer.getCommand()==Command_From_Server.OPPONENT_FAILED_TO_MOVE)
							{
								//System.out.println("The host AI failed to move. Please tell Mr. Tully");
								Thread.sleep(moveSleepTime);
							}
							else if(comFromServer.getCommand()==Command_From_Server.WIN)
							{
								System.out.println(currentlyPlaying.getName() +" wins!");
								board.isWinner('X');
								board.isWinner('O');
								Thread.sleep(endSleepTime);
								scores.addWin();
								board.reset();
							}
							else if(comFromServer.getCommand()==Command_From_Server.LOSE)
							{
								System.out.println("\t\t\t"+opponentName +" wins!");
								board.isWinner('X');
								board.isWinner('O');
								Thread.sleep(endSleepTime);
								scores.addLoss();
								board.reset();
								
							}
							else if(comFromServer.getCommand()==Command_From_Server.TIE)
							{
								System.out.println("\tTie Game");
								Thread.sleep(endSleepTime);
								scores.addCat();
								board.reset();
							}
							else if(comFromServer.getCommand()==Command_From_Server.MAKE_MOVE)
							{
								//System.out.println("*** calling move");
								l = (Location) currentlyPlaying.getMove(new Board(board));
								//System.out.println("*** moved to " + l);
								os.reset();
								os.writeObject(new Command_To_Server(Command_To_Server.MOVE,l));
								os.reset();
								
							}
							else if(comFromServer.getCommand()==Command_From_Server.MATCHES_COMPLETE)
							{
								System.out.println("Your AI's results are: ");
								System.out.println("Wins: "+scores.getWins());
								System.out.println("Cats: "+scores.getCats());
								System.out.println("Loses: "+scores.getLosses());
								Thread.sleep(endSleepTime);
								break;
							}
							
						}
						break;
							
					}
					
					
				}
				catch(Exception e)
				{
					System.out.println("Error in main: "+e.getMessage());
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("Shutting down...");
				System.exit(0);
			}
		}
		
	}
	
	public static char getOpponentLetter(char self)
	{
		if(self=='X')
			return 'O';
		else
			return 'X';
	}
}