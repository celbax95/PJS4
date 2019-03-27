package fr.itemsApp.items;

import java.awt.Graphics2D;

import fr.application.Application;
import fr.util.point.Point;

public abstract class Item implements CollectableItem, PlaceableItem {

	protected Point pos;
	protected Character holder;

	@Override
	public CollectableItem collect(Application application, Character character) {
		holder = character;
		return this;
	}

	@Override
	public abstract void draw(Graphics2D g);

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
