package fr.screen;

import javax.swing.JPanel;

public class Repainter implements Runnable {

	private static final int FPS30 = 33;
	@SuppressWarnings("unused")
	private static final int FPS60 = 17;

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
				Thread.sleep(FPS30);
			} catch (InterruptedException e) {
			}
		}
	}
}
