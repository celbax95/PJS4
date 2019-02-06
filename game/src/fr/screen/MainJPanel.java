package fr.screen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainJPanel extends JPanel {

	private static MainJPanel single = null;

	private int WIDTH, HEIGHT;

	private Screen screen;

	private AppliScreen appScreen;

	private MainJPanel(Screen screen, AppliScreen appScreen, int width, int height, int margin) {
		super();
		this.screen = screen;
		WIDTH = width;
		HEIGHT = height;
		this.setLocation(margin, margin);
		this.setSize(width, height);

		(this.appScreen = appScreen).start();

		Thread repainter = new Thread(new Repainter(this));
		repainter.start();
	}
	public void closeScr() {
		screen.dispatchEvent(new WindowEvent(screen, WindowEvent.WINDOW_CLOSING));
	}
	@Override
	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);

		this.setBackground(appScreen.getBackgroundColor());
		try {
			appScreen.draw((Graphics2D) g2);
		} catch (EndApp e) {
			System.err.println(e.getMessage());
			closeScr();
		}
	}

	public static MainJPanel getInstance() {
		return single;
	}

	public static MainJPanel getInstance(Screen screen, AppliScreen appScreen, int width, int height, int margin) {
		if (single == null) {
			single = new MainJPanel(screen, appScreen, width, height, margin);
			return single;
		} else
			return single;
	}
}
