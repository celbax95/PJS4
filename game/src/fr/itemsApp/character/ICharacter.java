package fr.itemsApp.character;

import java.io.Serializable;
import java.util.List;

import fr.application.Application;
import fr.itemsApp.Drawable;
import fr.itemsApp.Manageable;
import fr.util.point.Point;

public interface ICharacter extends Drawable, Serializable, Manageable {

	/**
	 * Execute les action suivant les touches du clavier
	 *
	 * @param application
	 *            : Application
	 * @param clickedKeys
	 *            : Touches appuyees du clavier
	 */
	void actions(Application application, List<Integer> clickedKeys);

	/**
	 * @return Coordones du centre du personnage
	 */
	Point getCenter();

	int getHealth();

	/**
	 * Change le cooldown de pose des bombes
	 *
	 * @param bombCoolDown
	 *            : nouveau cooldown
	 */
	void setBombCoolDown(int bombCoolDown);

	void setHealth(int health);

	/**
	 * Change la position du ICharacter
	 *
	 * @param pos
	 */
	void setPos(Point pos);

	/**
	 * Change la vitesse du ICharacter
	 *
	 * @param speed
	 *            : nouvelle vitesse pour le ICharacter
	 */
	void setSpeed(int speed);
}