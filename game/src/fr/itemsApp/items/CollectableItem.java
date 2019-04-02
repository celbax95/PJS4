package fr.itemsApp.items;

import java.awt.Image;

import fr.application.Application;

public interface CollectableItem {

	/**
	 * @return l'image de l'objet
	 */
	Image getImage();

	/**
	 * Utilisation de l'item
	 *
	 * @param application : application
	 */
	void use(Application application);
}
