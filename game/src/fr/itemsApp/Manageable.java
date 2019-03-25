package fr.itemsApp;

import fr.application.Application;

/**
 * Element gerable
 */
public interface Manageable {
	/**
	 * Gestion de l'element
	 *
	 * @param app               : Application
	 * @param TimeSinceLastCall : temps depuis le dernier appel, pour la
	 *                          synchro @see Application.run
	 */
	public void manage(Application app, double TimeSinceLastCall);
}
