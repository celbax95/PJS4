package fr.itemsApp.items;

import java.awt.Graphics2D;

import fr.application.Application;
import fr.itemsApp.Drawable;
import fr.util.point.Point;

public interface PlaceableItem extends Drawable {

	/**
	 * Recuperation d'un item
	 *
	 * @param application
	 *            : application
	 * @param character
	 *            : character recuperant l'item
	 * @return l'item sous forme de CollectableItem
	 */
	CollectableItem collect(Application application, Character character);

	@Override
	void draw(Graphics2D g);

	/**
	 * @return la position de l'item
	 */
	Point getPos();

	/**
	 * Change la position de l'item
	 *
	 * @param pos
	 *            : nouvelle position
	 */
	void setPos(Point pos);
}
