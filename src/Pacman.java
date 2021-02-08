import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;
public class Pacman {
	private int x;
	private int y;

	
public Pacman(int x, int y) {
	super();
	this.setxAxis(x);
	this.setyAxis(y);
}
public int getxAxis() {
	return x;
}
public void setxAxis(int x) {
	this.x = x;
}
public int getyAxis() {
	return y;
}
public void setyAxis(int y) {
	this.y = y;
}


public void initPacman(Color color,Graphics g) {
	g.setColor(color);
	g.fillOval(x, y, 50, 50);
}


}
