import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JOptionPane;

public class Enemy implements Runnable{
	public int direction;
	public String where;
	private GameScreen GameScreen;
	public int x,y;
	private Pacman pacman;
	public Color color;
	public Enemy(int x, int y,GameScreen GameScreen,int direction,Pacman pacman,Color color) {
		this.x = x;
		this.y = y;
		this.GameScreen = GameScreen;
		this.direction = 1;
		this.pacman = pacman;
		this.color = color;
	}

	public void isCollision() {
		Rectangle pacmanRect = new Rectangle(pacman.getxAxis(),pacman.getyAxis(),50,50);
		Rectangle enemyRect = new Rectangle(this.x,this.y,50,50);
		
		if(pacmanRect.intersects(enemyRect)) {
			GameScreen.control = 0;
			 JOptionPane.showMessageDialog(null, "You died, your score is " +GameScreen.score,"",JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		}
		if(pacmanRect.x < 50 && pacmanRect.y < 50) {
			GameScreen.control = 0;
			 JOptionPane.showMessageDialog(null, "You won, your score is " +GameScreen.score,"",JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		}
		for(int i = 0; i < 10; i++) {
			Rectangle temp = new Rectangle(GameScreen.enemies[i].x,GameScreen.enemies[i].y,50,50);
			if(enemyRect.intersects(temp) && !this.equals(GameScreen.enemies[i])) {
				if(this.direction == 1) {
					if(this.color.equals(new Color(0,0,153)))
						this.x -= 25;	
					if(this.color.equals(new Color(102,0,153)))
						this.y -= 25;
					if(this.color.equals(new Color(0,0,255)))
						this.x -= 25;
					if(this.color == Color.RED)
						this.y -= 25;
					if(this.color.equals(new Color(51,204,255)))
						this.x -= 50;
					if(this.color == Color.PINK)
						this.y -= 50;
					this.direction = 0;
				}
				else {
					if(this.color.equals(new Color(0,0,153)))
						this.x += 25;	
					if(this.color.equals(new Color(102,0,153)))
						this.y += 25;
					if(this.color.equals(new Color(0,0,255)))
						this.x += 25;
					if(this.color == Color.RED)
						this.y += 25;
					if(this.color.equals(new Color(51,204,255)))
						this.x += 50;
					if(this.color == Color.PINK)
						this.y += 50;
					this.direction = 1;
				}


			}
				
		}
	}
	
	@Override
	public void run() {
		while (true) {
				try {
					if(this.color.equals(new Color(0,0,153)) || this.color.equals(new Color(102,0,153)))
							Thread.sleep(4000);
					if(this.color.equals(new Color(0,0,255)) || this.color == Color.RED)
							Thread.sleep(2000);
					if(this.color.equals(new Color(51,204,255)) || this.color == Color.PINK)
							Thread.sleep(1000);
						
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(this.x >= 935 || this.y >= 590) {
					direction = 0;
				}
				if(this.x <= 0 || this.y <= 0) {
					direction = 1;
				}
				if(direction != 1) {
					if(this.color.equals(new Color(0,0,153)))
						this.x -= 25;	
					if(this.color.equals(new Color(102,0,153)))
						this.y -= 25;
					if(this.color.equals(new Color(0,0,255)))
						this.x -= 25;
					if(this.color == Color.RED)
						this.y -= 25;
					if(this.color.equals(new Color(51,204,255)))
						this.x -= 50;
					if(this.color == Color.PINK)
						this.y -= 50;
						
				}else {
					if(this.color.equals(new Color(0,0,153)))
						this.x += 25;	
					if(this.color.equals(new Color(102,0,153)))
						this.y += 25;
					if(this.color.equals(new Color(0,0,255)))
						this.x += 25;
					if(this.color == Color.RED)
						this.y += 25;
					if(this.color.equals(new Color(51,204,255)))
						this.x += 50;
					if(this.color == Color.PINK)
						this.y += 50;
				}
				
				isCollision();
				if(GameScreen.control == 1)
					GameScreen.repaint();
		}
		
	}
	public void initEnemy(Color color,Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, 50, 50);
	}
	
}
