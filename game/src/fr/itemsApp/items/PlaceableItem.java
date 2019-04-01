package fr.itemsApp.items;

import java.awt.Graphics2D;

import fr.application.Application;
import fr.itemsApp.Drawable;
import fr.itemsApp.character.ICharacter;
import fr.util.point.Point;

public interface PlaceableItem extends Drawable {

	/**
	 * Recuperation d'un item
	 *
	 * @param application : application
	 * @param character   : character recuperant l'item
	 *
	 * @return l'item sous forme de CollectableItem
	 */
	CollectableItem collect(Application application, ICharacter character);

	@Override
	void draw(Graphics2D g);

	/**
	 * @return la position de l'item
	 */
	Point getPos();

	/**
	 * @return la position de l'item
	 */
	Point getTile();

	/**
	 * Change la position de l'item
	 *
	 * @param tile     : nouvelle position
	 * @param tileSize : taille d'une tile
	 */
	void setTile(Point tile, int tileSize);

	boolean useNow();

	/**
	 * Ajoute un item a la liste d'items de l'appliçcation
	 *
	 * @param pa          : item a ajouter
	 * @param application : application
	 */
	public static void addToLists(PlaceableItem pa, Application application) {
		application.addItem(pa);
		application.addDrawable(pa);
	}
}
