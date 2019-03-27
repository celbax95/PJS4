package fr.itemsApp.items;

import fr.itemsApp.Drawable;
import fr.util.point.Point;

public interface PlaceableItem extends Drawable {

	CollectableItem collect(Character character);

	Point getPos();

	void setPos(Point pos);
}
