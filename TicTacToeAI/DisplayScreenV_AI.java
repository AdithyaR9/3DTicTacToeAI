package TicTacToeAI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayScreenV_AI extends JFrame implements Runnable
{
	public Board board = null;
	BufferedImage img;

	
	public DisplayScreenV_AI(Board board)
	{
		super();
		this.board = board;
		setSize(125,315);
		img = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_ARGB);
		setAlwaysOnTop(true);
		setUndecorated(true);
		setVisible(true);
	}

	public void paint(Graphics g)
	{
		board.draw(img.getGraphics());

		g.drawImage(img,0,0,null);
	}

	public void addNotify()
	{
		super.addNotify();
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run()
	{
		
		while(true)
		{
			try
			{
				repaint();
				//board.isWinner('X');
				Thread.sleep(50);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
}