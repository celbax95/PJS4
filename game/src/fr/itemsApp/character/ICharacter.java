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
	 * @param application : Application
	 * @param clickedKeys : Touches appuyees du clavier
	 */
	void actions(Application application, List<Integer> clickedKeys);

	/**
	 * Applique des degats au IChraracter
	 *
	 * @param health : vie a enlever au IChraracter
	 */
	void damage(int health);

	/**
	 * @return Coordones du centre du personnage
	 */
	Point getCenter();

	/**
	 * @return vie du IChraracter
	 */
	int getHealth();

	/**
	 * @return la vie max du ICharacter
	 */
	int getMaxHealth();

	/**
	 * @return Le temps max avant de pouvoir poser une bombe
	 */
	int maxTimeBeforeBomb();

	/**
	 * Fait regarder le ICharacter dans une direction
	 *
	 * @param angle : angle dans lequel le ICharacter doit regarder
	 */
	void setAngle(double angle);

	/**
	 * Change le cooldown de pose des bombes
	 *
	 * @param bombCoolDown : nouveau cooldown
	 */
	void setBombCoolDown(int bombCoolDown);

	/**
	 * Change la vie du ICharacter
	 *
	 * @param health : nouvelle health du ICharacter
	 */
	void setHealth(int health);

	/**
	 * Change l'id du ICharacter
	 *
	 * @param id : nouvel id du ICharacter
	 */
	void setId(int id);

	/**
	 * Change la position du ICharacter
	 *
	 * @param pos
	 */
	void setPos(Point pos);

	/**
	 * Change la vitesse du ICharacter
	 *
	 * @param speed : nouvelle vitesse pour le ICharacter
	 */
	void setSpeed(int speed);

	/**
	 * @return Le temps avant de pouvoir poser une bombe
	 */
	int timeBeforeBomb();
}