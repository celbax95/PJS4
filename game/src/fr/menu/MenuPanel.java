package fr.menu;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public MenuPanel(int rw) {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(rw, 250));
	}
	
	/*public void paint(Graphics g) {
		try {
			Image img = ImageIO.read(new File(""));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
}
