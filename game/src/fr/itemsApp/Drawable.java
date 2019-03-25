package fr.itemsApp;

import java.awt.Graphics2D;

/**
 * Element affichable
 */
public interface Drawable {
	/**
	 * Affichage de l'element
	 *
	 * @param g : Permet d'afficher
	 */
	public void draw(Graphics2D g);
}
