package fr.screen;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Application
 *
 * @see MainJPanel
 */
public interface AppliScreen {

	/**
	 * Ferme l'application
	 */
	void close();

	/**
	 * Affiche les elements a l'ecran
	 *
	 * @see MainJpanel
	 *
	 * @param g : Permet d'afficher a l'ecran
	 * @throws EndApp : Fin d'application
	 */
	void draw(Graphics2D g) throws EndApp;

	/**
	 * @return Couleur d'arriere plan de l'application
	 */
	Color getBackgroundColor();

	/**
	 * @return Nom de la fenetre
	 */
	String getName();

	/**
	 * Lance l'application
	 */
	void start();
}
