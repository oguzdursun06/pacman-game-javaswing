import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class GameScreen extends JPanel implements KeyListener{

	private JFrame screen = new JFrame();
	private Pacman pacman = new Pacman(900,600);
	public static Enemy enemies[] = new Enemy[10];
	public static Food food[] = new Food[10];
	ArrayList<Integer> foodXValues = new ArrayList<>();
	ArrayList<Integer> foodYValues = new ArrayList<>();
	ArrayList<Integer> xValues = new ArrayList<>();
	ArrayList<Integer> yValues = new ArrayList<>();
	public static int score = 0,control = 1;
	
public GameScreen()
{
	spawnFood(food);
	randomSpawn(enemies);
	this.setFocusable(true);
	this.addKeyListener(this);
	screen.add(this);
	Thread scoreBoard = new Thread(()->
	{
		while(control == 1) {
		try {
			score++;
			Thread.sleep(1000);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	});
	scoreBoard.start();
	Thread Thread[] = new Thread[10];
	for(int i = 0; i < 10; i++) {
		Thread[i] = new Thread(enemies[i]);
		Thread[i].start();
	}
	
	//thread.start();
	screen.setSize(1000, 700);
	screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	screen.setVisible(true);
	screen.setTitle("Pacman");
}
public void paint(Graphics g)
{
	super.paint(g);
	this.setBackground(Color.BLACK);
	for(int i = 0; i < 5; i++) {
		food[i].initFood(g);
	}
	for(int i = 0; i < 10; i++) {
		enemies[i].initEnemy(enemies[i].color, g);
	}
	pacman.initPacman(Color.yellow,g);
	g.setColor(Color.white);
	g.setFont(new Font(null,Font.BOLD,20));
	g.drawString("Score : "+score, 870, 35);
	g.setColor(Color.white);
	g.setFont(new Font(null,Font.ITALIC,40));
	g.drawString("E",7, 35);
	
	
}

@Override
public void keyPressed(KeyEvent e) {
	if(GameScreen.control == 1) {
	if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
		//JOptionPane.showMessageDialog(null, "Saga bastin!!");
		if(pacman.getxAxis() <= 935)
			pacman.setxAxis(pacman.getxAxis()+50);
		this.repaint();
	}
	if(e.getKeyCode() == KeyEvent.VK_LEFT) {
		//JOptionPane.showMessageDialog(null, "Saga bastin!!");
		if(pacman.getxAxis() >= 10)
			pacman.setxAxis(pacman.getxAxis()-50);
		this.repaint();
	}
	if(e.getKeyCode() == KeyEvent.VK_UP) {
		//JOptionPane.showMessageDialog(null, "Saga bastin!!");
		if(pacman.getyAxis() >= 10)
			pacman.setyAxis(pacman.getyAxis()-50);
		this.repaint();
	}
	if(e.getKeyCode() == KeyEvent.VK_DOWN) {
		//JOptionPane.showMessageDialog(null, "Saga bastin!!");
		if(pacman.getyAxis() <= 590)
			pacman.setyAxis(pacman.getyAxis()+50);
		this.repaint();
	}
	for(int i = 0; i < 10; i++) {
		Rectangle pacmanRect = new Rectangle(pacman.getxAxis(),pacman.getyAxis(),50,50);
		Rectangle enemyRect = new Rectangle(enemies[i].x,enemies[i].y,50,50);
		
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
		
	}
	for(int i = 0; i < 5; i++) {
		Rectangle pacmanRect = new Rectangle(pacman.getxAxis(),pacman.getyAxis(),50,50);
		Rectangle foodRect = new Rectangle(food[i].xPos,food[i].yPos,25,25);
		if(pacmanRect.contains(foodRect)) {
			if(food[i].isEat == false) {
				GameScreen.score += 5;
				food[i].isEat = true;
				food[i].xPos += 5000;
				this.repaint();
				
			}
		}
		
		
	}
	}
	
}
public void randomSpawn(Enemy[] enemies) {
	for(int i = 0; i < 10; i++) {
		int random = (int)(Math.random()*7);
		int randomX = (int)(Math.random()*20);
		int randomY = (int)(Math.random()*14);
		for(int j = 0; j < i; j++) {
			if((randomX > xValues.get(j) || randomX < xValues.get(j)) && (randomY > yValues.get(j) || randomY < yValues.get(j)) && (randomX > 1 && randomX < 18) && (randomY > 1 && randomY < 12)) {
			}
			else {
				randomX = (int)(Math.random()*20);
				randomY = (int)(Math.random()*14);
				j = 0;
			}
		}		
		
		if(random == 0) {
			enemies[i] = new Enemy(randomX*50, randomY*50, this, 1,pacman,new Color(51,204,255));
			xValues.add(randomX);
			yValues.add(randomY);
		
		}
		else if(random == 1) {
			enemies[i] = new Enemy(randomX*50, randomY*50, this, 1,pacman,new Color(0,0,255));
			xValues.add(randomX);
			yValues.add(randomY);
		}
		else if(random == 2) {
			enemies[i] = new Enemy(randomX*50, randomY*50, this, 1,pacman,new Color(0,0,153));
			xValues.add(randomX);
			yValues.add(randomY);
		}
		else if(random == 3) {
			enemies[i] = new Enemy(randomX*50, randomY*50, this, 1,pacman,Color.PINK);
			xValues.add(randomX);
			yValues.add(randomY);
		}
		else if(random == 4) {
			enemies[i] = new Enemy(randomX*50, randomY*50, this, 1,pacman,Color.RED);
			xValues.add(randomX);
			yValues.add(randomY);
		}
		else {
			enemies[i] = new Enemy(randomX*50, randomY*50, this, 1,pacman,new Color(102,0,153));
			xValues.add(randomX);
			yValues.add(randomY);
		}
		
	}
	
}
public void spawnFood(Food[] food) {
	for(int i = 0; i < 5; i++) {
		int randomX = (int)(Math.random()*18);
		int randomY = (int)(Math.random()*12);
		for(int j = 0; j < i; j++) {
			if((randomX > foodXValues.get(j) || randomX < foodXValues.get(j)) && (randomY > foodYValues.get(j) || randomY < foodYValues.get(j)) && (randomX > 1 && randomX < 18) && (randomY > 1 && randomY < 12)) {
			}
			else {
				randomX = (int)(Math.random()*20);
				randomY = (int)(Math.random()*12);
				j = 0;
			}
		}
	
		food[i] = new Food(randomX*50, randomY*50,25,25);
		foodXValues.add(randomX);
		foodYValues.add(randomY);
}
}
@Override
public void keyReleased(KeyEvent e) {
		
}
@Override
public void keyTyped(KeyEvent e) {
	
}

}