package TicTacToeAI; /** Location - Stores the x,y,z values of a 3D location */
import java.io.Serializable;
public class Location implements Serializable
{
	int sheet;
	int row;
	int col;
	
	/** Sets x, y, z to the recieved values */
	public Location(int col,int row,int sheet)
	{
		this.col	= col;
		this.row	= row;
		this.sheet 	= sheet;
	}
	
	/** Copys the data of the received location
	 * @param l Location - the location to be copied*/
	public Location(Location l)
	{
		this.col	= l.getCol();
		this.row	= l.getRow();
		this.sheet 	= l.getSheet();
	}
	
	/** Returns the location's column
	 * @return int - the column*/
	public int getCol()
	{
		return col;
	}
	
	/** Returns the location's row
	 * @return int - the row*/
	public int getRow()
	{
		return row;	
	}
	
	/** Returns the location's sheet
	 * @return int - the sheet*/
	public int getSheet()
	{
		return sheet;	
	}
	
	/** Returns a text representation of the location 
	 * in the form of (x,y,x)
	 * @ return Stirng - a text representation of the location*/
	public String toString()
	{
		String s = "(x=" + col+",y="+row+ ",z="+sheet+ ")";
		return s;
	}
	
	/* Pre:		None
	 * Post: 	returns a copy of the Location*/
	public Object clone()
	{
		return new Location(col,row,sheet);
	}

}