package fr.menu;

import java.awt.Dimension;
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
