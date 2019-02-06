package fr.screen;

import javax.swing.JPanel;

public class Repainter implements Runnable {

	private static final int PAUSE = 17;

	private JPanel japanel;

	public Repainter(JPanel jpanel) {
		super();
		this.japanel = jpanel;
	}

	@Override
	public void run() {
		while (true) {
			japanel.repaint();
			try {
				Thread.sleep(PAUSE);
			} catch (InterruptedException e) {
			}
		}
	}
}
