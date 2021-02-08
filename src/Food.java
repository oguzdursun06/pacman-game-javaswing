import java.awt.Color;
import java.awt.Graphics;

public class Food{
	public int xPos;
	public int yPos;
	public int width;
	public int height;
	public Boolean isEat = false;
	
	public Food(int xPos, int yPos, int width, int height)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
	}
	
	public void initFood(Graphics g)
	{	
		g.setColor(Color.GREEN);
		g.fillRect(xPos, yPos, width, height);
	}

	
}