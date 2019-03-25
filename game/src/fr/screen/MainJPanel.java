package fr.screen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;

/**
 * Singloton MainPanel, Contenu de la fenetre
 */
@SuppressWarnings("serial")
public class MainJPanel extends JPanel {

	private static MainJPanel single = null;

	private int WIDTH, HEIGHT;

	private Screen screen;

	private AppliScreen appScreen;

	/**
	 * @param screen    : la fenetre
	 * @param appScreen : Application a lancer dans la fenetre
	 * @param width     : largeur de la fenetre
	 * @param height    : hauteur de la fenetre
	 * @param margin    : marge totale dans la fenetre
	 */
	private MainJPanel(Screen screen, AppliScreen appScreen, int width, int height, int margin) {
		super();
		this.screen = screen;

		// taille du panel
		WIDTH = width;
		HEIGHT = height;

		this.setLocation(margin, margin);
		this.setSize(WIDTH, HEIGHT);

		(this.appScreen = appScreen).start();

		Thread repainter = new Thread(new Repainter(this));
		repainter.start();
	}

	/**
	 * Fermeturede la fenetre
	 */
	public void closeScr() {
		screen.dispatchEvent(new WindowEvent(screen, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * Methode appellee par le repainter, pour actualiser l'affichage dans la
	 * fenetre
	 */
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

	/**
	 * @return l'instance unique MainPanel
	 */
	public static MainJPanel getInstance() {
		return single;
	}

	/**
	 * Cree ou renvoie le MainPanel unique
	 *
	 * @param screen    : la fenetre
	 * @param appScreen : Application a lancer dans la fenetre
	 * @param width     : largeur de la fenetre
	 * @param height    : hauteur de la fenetre
	 * @param margin    : marge totale dans la fenetre
	 * @return une nouvelle instance ou l'instance existante
	 */
	public static MainJPanel getInstance(Screen screen, AppliScreen appScreen, int width, int height, int margin) {
		if (single == null) {
			single = new MainJPanel(screen, appScreen, width, height, margin);
			return single;
		} else
			return single;
	}
}
