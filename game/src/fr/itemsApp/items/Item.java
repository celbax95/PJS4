package fr.itemsApp.items;

import fr.application.Application;
import fr.util.point.Point;

public abstract class Item implements CollectableItem, PlaceableItem {

	protected Point pos;
	protected Character holder;

	@Override
	public CollectableItem collect(Character character) {
		holder = character;
		return this;
	}

	@Override
	public Point getPos() {
		return pos;
	}

	@Override
	public void setPos(Point pos) {
		this.pos = pos;
	}

	@Override
	public abstract void use(Application application);

}
