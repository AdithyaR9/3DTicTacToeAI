package TicTacToeAI; /** This class stores the board's data and allows you to manipulate it */

import java.awt.*;
import java.util.*;

public class Board
{
	private char[][][] board = new char[4][4][4];
	private ArrayList<GamePolygon> polys = new ArrayList<GamePolygon>();
	
	Color xWin = Color.red;
	Color yWin = Color.blue;
	int timer = 0;

	/** Creates a 4x4x4 gameboard where every location is '-' (Empty)*/
	public Board()
	{
		for(int sheet=0; sheet < numSheets(); sheet++)
			for(int row=0; row < numRows(); row++)
				for(int col=0; col < numCols(); col++)
				{
					board[sheet][row][col] = '-';
				}
				
		for(int r = 0; r<numRows(); r++)
		{
			int rowH = 68*(r+1);
			for(int s = 0; s < numSheets(); s++)
			{
				int sheetHMod = 0 -14*s;
				for(int c = 0; c<numCols(); c++)
				{	
					int colW = 20 + 14*c;
					int rowWMod = s*7; 
					int x = colW+rowWMod;
					int y = rowH+sheetHMod;
					
					Polygon p = new Polygon();
					p.addPoint(x+8,y);
					p.addPoint(x+22,y);
					
					p.addPoint(x+14,y+15);
					p.addPoint(x,y+15);
					
					polys.add(new GamePolygon(x,y,new Location(c,r,s),p));
				}
			}
		}
	}
	
	/** Clones the received board
	 * @param b Board - The board to be cloned
	 */
	public Board(Board b)
	{
		for(int sheet=0; sheet < numSheets(); sheet++)
			for(int row=0; row < numRows(); row++)
				for(int col=0; col < numCols(); col++)
				{
					board[sheet][row][col] = b.getLocation(new Location(col,row,sheet));
				}
	}
	
	/** Returns an ArrayList of GamePolygons for drawing the board
	 * @return returns an ArrayList of the boards GamePolygons */
	public ArrayList<GamePolygon> getPolys()
	{	return polys;	}
	
	/** Accesses the Board's 4x4x4 char array
	 * @return returns a 3D char array that stores the boards data*/
	public char[][][] getData()
	{
		return board;
	}
	
	/** Changes a board position
	 * @param l Location - The location to be accessed
	 * @param c char - New value for the location
	 * @return when the provided location is empty ('-') it places the 
	 * received letter into the location and returns true, otherwise
	 * it returns false
	 */
	public boolean setLocation(Location l, char c)
	{
		if(isEmpty(l))
		{
			board[l.getSheet()][l.getRow()][l.getCol()] = c;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/** Changes a board position without checking to see if it is empty
	 * @param l Location - The location to be accessed
	 * @param c char - New value for the location
	 */
	public void setLocationNoCheck(Location l, char c)
	{
		board[l.getSheet()][l.getRow()][l.getCol()] = c;
	}

	/** Accesses the char at a given location
	 * @param l Location - The location to be accessed 
	 * @return returns the value at the given location*/
	public char getLocation(Location l)
	{
		return board[l.getSheet()][l.getRow()][l.getCol()];
	}

	/** Determines if a player has won
	 * @param player char - The player to be checked ('X' or 'X')
	 * @return returns if the received letter has a win one the board. 
	 * It will also toggle the winning move line from lowercase to uppercase
	 * each call.*/
	public boolean isWinner(char player)
	{
		// check rows
		for(int r=0; r < numRows(); r++)
		{
				for(int s=0; s < numSheets(); s++)
				{
					if(board[s][r][0] == player &&
						board[s][r][1] == player &&
						board[s][r][2] == player &&
						board[s][r][3] == player)
					{
						if(board[s][r][0] == 'X')
						{
							board[s][r][0] =
							board[s][r][1] =
							board[s][r][2] =
							board[s][r][3] = 'x';
						}
						else
						{
							board[s][r][0] =
							board[s][r][1] =
							board[s][r][2] =
							board[s][r][3] = 'o';
						}
						return true;
					}

				}
		}

		// check colums
		for(int c=0; c < numCols(); c++)
		{
				for(int s=0; s < numSheets(); s++)
				{
					if(board[s][0][c] == player &&
						board[s][1][c] == player &&
						board[s][2][c] == player &&
						board[s][3][c] == player)
					{
						if(board[s][0][c] == 'X')
						{
							board[s][0][c] =
							board[s][1][c] =
							board[s][2][c] =
							board[s][3][c] = 'x';
						}
						else
						{
							board[s][0][c] =
							board[s][1][c] =
							board[s][2][c] =
							board[s][3][c] = 'o';
						}
						return true;
					}
				}
		}

		// check depth
		for(int c=0; c < numCols(); c++)
		{
			for(int r=0; r < numRows(); r++)
			{
					if(board[0][r][c] == player &&
						board[1][r][c] == player &&
						board[2][r][c] == player &&
						board[3][r][c] == player)
					{
						if(board[0][r][c] == 'X')
						{
							board[0][r][c] =
							board[1][r][c] =
							board[2][r][c] =
							board[3][r][c] = 'x';
						}
						else
						{
							board[0][r][c] =
							board[1][r][c] =
							board[2][r][c] =
							board[3][r][c] = 'o';
						}
						return true;
					}
			}

		}

		// check crosses in z plane sheets
		for(int s=0; s< numSheets(); s++)
		{
			if(board[s][0][0] == player &&
				board[s][1][1] == player &&
				board[s][2][2] == player &&
				board[s][3][3] == player)
			{
				if(board[s][0][0] == 'X')
				{
					board[s][0][0] =
					board[s][1][1] =
					board[s][2][2] =
					board[s][3][3] = 'x';
				}
				else
				{
					board[s][0][0] =
					board[s][1][1] =
					board[s][2][2] =
					board[s][3][3] = 'o';
				}
				return true;
			}
			if(board[s][0][3] == player &&
				board[s][1][2] == player &&
				board[s][2][1] == player &&
				board[s][3][0] == player)
			{
				if(board[s][0][3] == 'X')
				{
					board[s][0][3] =
					board[s][1][2] =
					board[s][2][1] =
					board[s][3][0] = 'x';
				}
				else
				{
					board[s][0][3] =
					board[s][1][2] =
					board[s][2][1] =
					board[s][3][0] = 'o';
				}
				return true;
			}
		}

		// check crosses in y plane sheets
		for(int r=0; r < numRows(); r++)
		{
			if(board[0][r][0] == player &&
				board[1][r][1] == player &&
				board[2][r][2] == player &&
				board[3][r][3] == player)
			{
				if(board[0][r][0] == 'X')
				{
					board[0][r][0] =
					board[1][r][1] =
					board[2][r][2] =
					board[3][r][3] = 'x';
				}
				else
				{
					board[0][r][0] =
					board[1][r][1] =
					board[2][r][2] =
					board[3][r][3] = 'o';
				}
				return true;
			}
			if(board[3][r][0] == player &&
				board[2][r][1] == player &&
				board[1][r][2] == player &&
				board[0][r][3] == player)
			{
				if(	board[3][r][0] == 'X')
				{
					board[3][r][0] =
					board[2][r][1] =
					board[1][r][2] =
					board[0][r][3] = 'x';
				}
				else
				{
					board[3][r][0] =
					board[2][r][1] =
					board[1][r][2] =
					board[0][r][3] = 'o';
				}
				return true;
			}
		}

		// check crosses in x plane sheets
		for(int c=0; c < numCols(); c++)
		{
			if(board[0][0][c] == player &&
				board[1][1][c] == player &&
				board[2][2][c] == player &&
				board[3][3][c] == player)
			{
				if(board[0][0][c] == 'X')
				{
					board[0][0][c] =
					board[1][1][c] =
					board[2][2][c] =
					board[3][3][c] = 'x';
				}
				else
				{
					board[0][0][c] =
					board[1][1][c] =
					board[2][2][c] =
					board[3][3][c] = 'o';
				}
				return true;
			}
			if(board[0][3][c] == player &&
				board[1][2][c] == player &&
				board[2][1][c] == player &&
				board[3][0][c] == player)
			{
				if(board[0][3][c] == 'X')
				{
					board[0][3][c] =
					board[1][2][c] =
					board[2][1][c] =
					board[3][0][c] = 'x';
				}
				else
				{
					board[0][3][c] =
					board[1][2][c] =
					board[2][1][c] =
					board[3][0][c] = 'o';
				}
				return true;
			}
		}

		// check special cross one
		if(board[0][0][0] == player &&
				board[1][1][1] == player &&
				board[2][2][2] == player &&
				board[3][3][3] == player)
			{
				if(board[0][0][0] == 'X')
				{
					board[0][0][0] =
					board[1][1][1] =
					board[2][2][2] =
					board[3][3][3] = 'x';
				}
				else
				{
					board[0][0][0] =
					board[1][1][1] =
					board[2][2][2] =
					board[3][3][3] = 'o';
				}
				return true;
			}

		// check special cross two
		if(board[0][0][3] == player &&
				board[1][1][2] == player &&
				board[2][2][1] == player &&
				board[3][3][0] == player)
			{
				if(	board[0][0][3] == 'X')
				{
					board[0][0][3] =
					board[1][1][2] =
					board[2][2][1] =
					board[3][3][0] = 'x';
				}
				else
				{
					board[0][0][3] =
					board[1][1][2] =
					board[2][2][1] =
					board[3][3][0] = 'o';

				}
				return true;
			}

		// check special cross three
		if(board[3][0][0] == player &&
				board[2][1][1] == player &&
				board[1][2][2] == player &&
				board[0][3][3] == player)
			{
				if(board[3][0][0] == 'X')
				{
					board[3][0][0] =
					board[2][1][1] =
					board[1][2][2] =
					board[0][3][3] = 'x';
				}
				else
				{
					board[3][0][0] =
					board[2][1][1] =
					board[1][2][2] =
					board[0][3][3] = 'o';
				}
				return true;
			}

		// check special cross four
		if(board[3][0][3] == player &&
				board[2][1][2] == player &&
				board[1][2][1] == player &&
				board[0][3][0] == player)
			{
				if(board[3][0][3] == 'X')
				{
					board[3][0][3] =
					board[2][1][2] =
					board[1][2][1] =
					board[0][3][0] = 'x';
				}
				else
				{
					board[3][0][3] =
					board[2][1][2] =
					board[1][2][1] =
					board[0][3][0] = 'o';
				}
				return true;
			}
		return false;

	}

	/** draws a text representation of the game board */
	public void display()
	{
		for(int s = numSheets()-1; s >=0; s--)
		{
			for(int r = 0; r <numRows(); r++)
			{
				for(int t = 0; t<s;t++)
				{
					System.out.print("    ");
				}
				for(int c = 0; c <numCols(); c++)
				{

						System.out.print(board[s][r][c]);

				}
				System.out.println();
			}

		}
	}

	/** Determines if a location is empty
	 * @param l Location - The location to be checked
	 * @return true when empty, false otherwise
	 */
	public boolean isEmpty(Location l)
	{
		if(board[l.getSheet()][l.getRow()][l.getCol()] == '-')
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/** Determines if the game is a tie
	 * @return true when the game is a tie, false otherwise */
	public boolean isCat()
	{
		int m = 0;
		for(int c=0; c < numCols(); c++)
			for(int r=0; r < numRows(); r++)
				for(int s=0; s < numSheets(); s++)
				{
					if(board[s][r][c] == '-')
						return false;
				}
		if(isWinner('X') || isWinner('O'))
			return false;
		else
			return true;
	}

	/** Number of rows
	 * @return int - numbr of rows  */
	public int numRows()
	{
		return board[0].length;
	}

	/** Number of rows
	 * @return int - numbr of columns  */
	public int numCols()
	{
		return board[0][0].length;
	}

	/** Number of rows
	 * @return int - numbr of sheets  */
	public int numSheets()
	{
		return board.length;
	}

	/** sets all the locations on the board to empty ('-')*/
	public void reset()
	{
		for(int s=0; s < numSheets(); s++)
			for(int r=0; r < numRows(); r++)
				for(int c=0; c < numCols(); c++)
				{
					board[s][r][c] = '-';
				}
	}

	/** draws a graphical representation of the board to the received
	 * graphics object */
	public void draw(Graphics g)
	{
		timer = (timer+1)%8;

		if(timer == 0)
		{
			if(xWin.equals(Color.red))
			{
				xWin = yWin = Color.gray;
			}
			else
			{
				xWin = Color.red;
				yWin = Color.blue;
			}
		}
		g.setColor(Color.black);
		g.fillRect(0,0,145,315);

	
		for(int a=0; a<polys.size(); a++)
		{
			g.setColor(Color.white);
			g.drawPolygon(polys.get(a).getPolygon());
			int x = polys.get(a).getX();
			int y = polys.get(a).getY();
			char letter  = getLocation(polys.get(a).getLocation());
			if(letter == 'X')
			{
				g.setColor(Color.red);
				g.fillOval(x+5,y+1,14,14);
			}
			else if(letter == 'O')
			{
				g.setColor(Color.blue);
				g.fillOval(x+5,y+1,14,14);
			}
			else if(letter == 'x')
			{
				g.setColor(xWin);
				g.fillOval(x+5,y+1,14,14);
			}
			else if(letter == 'o')
			{
				g.setColor(yWin);
				g.fillOval(x+5,y+1,14,14);
			}
		}
	}

	/** draws a polygon with the letter on the screen 'X' is red,
	 * 'O' is blue and ('x'/'o') are white */
	public void drawLocation(Graphics g,int x, int y, char letter)
	{
		g.setColor(Color.gray);
		g.drawLine(x+8,y,x+22,y);
		g.drawLine(x+8,y,x,y+15);
		if(letter == 'X')
		{
			g.setColor(Color.red);
			g.fillOval(x+5,y+1,14,14);
		}
		else if(letter == 'O')
		{
			g.setColor(Color.blue);
			g.fillOval(x+5,y+1,14,14);
		}
		else if(letter == 'x')
		{
			g.setColor(xWin);
			g.fillOval(x+5,y+1,14,14);
		}
		else if(letter == 'o')
		{
			g.setColor(yWin);
			g.fillOval(x+5,y+1,14,14);
		}
		g.setColor(Color.gray);
		g.drawLine(x,y+15,x+14,y+15);
		g.drawLine(x+22,y,x+14,y+15);
	}
	
	/* returns a copy of the Board
	 * @return - a copy of the Boad */
	public Object clone()
	{
		Board copy = new Board();
		for(int sheet=0; sheet < numSheets(); sheet++)
			for(int row=0; row < numRows(); row++)
				for(int col=0; col < numCols(); col++)
				{
					copy.getData()[sheet][row][col] = board[sheet][row][col];
				}
		return copy;
	}
}