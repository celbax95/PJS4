package fr.screen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Root extends JPanel {

	private static Root single = null;

	private static int WIDTH, HEIGHT;

	private Screen scr;

	private AppliScreen appScr;

	private Root(Screen scr, AppliScreen appScr, int w, int h, int m) {
		super();
		this.scr = scr;
		WIDTH = w;
		HEIGHT = h;
		this.setLocation(m, m);
		this.setSize(w, h);

		(this.appScr = appScr).start();

		Thread repainter = new Thread(new Repainter(this));
		repainter.start();
	}
	public void closeScr() {
		scr.dispatchEvent(new WindowEvent(scr, WindowEvent.WINDOW_CLOSING));
	}
	@Override
	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);

		this.setBackground(appScr.getBackgroundColor());
		try {
			appScr.draw((Graphics2D) g2);
		} catch (EndApp e) {
			System.err.println(e.getMessage());
			closeScr();
		}
	}

	public static Root getInstance() {
		return single;
	}

	public static Root getInstance(Screen scr, AppliScreen appScr, int w, int h, int m) {
		if (single == null) {
			single = new Root(scr, appScr, w, h, m);
			return single;
		} else
			return single;
	}
}
