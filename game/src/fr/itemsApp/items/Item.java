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
	public Point getPos() {
		return this.pos;
	}

	@Override
	public Point getTile() {
		return this.tile;
	}

	@Override
	public void setTile(Point tile, int tileSize) {
		this.tile = tile;
		this.pos = new Point(tile.getIX() * tileSize, tile.getIY() * tileSize);
	}

	@Override
	public abstract void use(Application application);

	public static void addToLists(Item item, Application application) {
		application.addDrawable(item);
	}
}
