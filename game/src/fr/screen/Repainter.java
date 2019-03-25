package fr.screen;

import javax.swing.JPanel;

/**
 * Appelle la methode repaint d'un panel tout les X millisecondes
 */
public class Repainter implements Runnable {

	private static final int FPS30 = 33;
	@SuppressWarnings("unused")
	private static final int FPS60 = 17;

	private JPanel japanel;

	/**
	 * @param jpanel Le panel a actualiser
	 */
	public Repainter(JPanel jpanel) {
		super();
		this.japanel = jpanel;
	}

	/**
	 * Appelle la methode repaint du panel a actualiser
	 */
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
