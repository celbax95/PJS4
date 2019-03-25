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
	 * @param application    : Application
	 * @param clickedKeys : Touches appuyees du clavier
	 */
	void actions(Application application, List<Integer> clickedKeys);

	/**
	 * @return Coordones du centre du personnage
	 */
	Point getCenter();

	void setBombCoolDown(int bombCoolDown);

	void setPos(Point pos);

	void setSpeed(int speed);
}