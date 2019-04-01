package fr.itemsApp.items;

import java.awt.Graphics2D;

import fr.application.Application;
import fr.itemsApp.character.ICharacter;
import fr.util.point.Point;

public abstract class Item implements CollectableItem, PlaceableItem {

	protected final int SIZE = 110;

	protected Point pos, tile;
	protected ICharacter holder;

	@Override
	public CollectableItem collect(Application application, ICharacter character) {
		this.holder = character;
		return this;
	}

	@Override
	public abstract void draw(Graphics2D g);

	@Override
	public Point getTile() {
		return this.pos;
	}

	@Override
	public void setTile(Point pos) {
		this.pos = pos;
	}

	@Override
	public abstract void use(Application application);

}
