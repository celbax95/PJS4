package fr.itemsApp.items;

import java.awt.Image;

import fr.application.Application;

public interface CollectableItem {

	/**
	 * @return l'image de l'objet
	 */
	Image getIcon();

	/**
	 * Utilisation de l'item
	 *
	 * @param application : application
	 */
	void use(Application application);
}
