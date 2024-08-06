package wordgame;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FramesBackground extends JPanel{
    ImageIcon image = new ImageIcon("C:\\Users\\Lenovo\\OneDrive\\Pictures\\pixelbackroundCity.gif");
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image.getImage(), 0,0 ,this);
	}
}
