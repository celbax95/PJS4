package fr.screen;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.screen.keyboard.KeyBoard;

/**
 * Singloton Screen, une fenetre
 */
@SuppressWarnings("serial")
public class Screen extends JFrame {

	private static Screen single = null;

	@SuppressWarnings("unused")
	private MainJPanel mainJpanel;

	/**
	 * @param appScreen : Application a lancer dans la fenetre
	 * @param width     : largeur de la fenetre
	 * @param height    : hauteur de la fenetre
	 * @param marginX   : marge horizontale dans la fenetre
	 * @param marginY   : marge verticale dans la fenetre
	 * @param margin    : marge totale dans la fenetre
	 */
	private Screen(AppliScreen appScreen, int width, int height, int marginX, int marginY, int margin) {
		// Nom d ela fenetre
		this.setTitle(appScreen.getName());

		// Quand on ferme la fenetre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				appScreen.close();
			}
		});

		// Proprietes de la fenetres
		this.setResizable(false);
		this.setSize(marginX + width + margin * 2, marginY + height + margin * 2);
		this.setLocationRelativeTo(null);

		// Clavier
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(new KeyBoard());

		// Interieur de la fenetre
		JPanel jp = new JPanel();
		jp.setLayout(null);
		jp.setBackground(Color.black);
		jp.add((mainJpanel = MainJPanel.getInstance(this, appScreen, width, height, margin)));
		this.setContentPane(jp);
		this.setVisible(true);
	}

	/**
	 * @return l'unique instance de Screen
	 */
	public static Screen getInstance() {
		return single;
	}

	/**
	 * Cree ou renvoie l'instance unique de Screen
	 * 
	 * @param appScreen : Application a lancer dans la fenetre
	 * @param width     : largeur de la fenetre
	 * @param height    : hauteur de la fenetre
	 * @param marginX   : marge horizontale dans la fenetre
	 * @param marginY   : marge verticale dans la fenetre
	 * @param margin    : marge totale dans la fenetre
	 * @return la nouvelle instance ou l'instance existante
	 */
	public static Screen getInstance(AppliScreen appScreen, int width, int height, int marginX, int marginY,
			int margin) {
		if (single == null) {
			single = new Screen(appScreen, width, height, marginX, marginY, margin);
			return single;
		} else
			return single;
	}
}
