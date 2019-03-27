package fr.itemsApp.items;

import fr.application.Application;
import fr.util.point.Point;

public abstract class Item implements HoldableItem, PlaceableItem {

	protected Point pos;
	protected Character holder;

	@Override
	public HoldableItem collect(Character character) {
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
