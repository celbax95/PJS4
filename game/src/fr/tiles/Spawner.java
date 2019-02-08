package fr.tiles;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.application.Application;

public class Spawner extends MapTileSuper {

	private static final long serialVersionUID = 1L;
	private static Color c = new Color(0, 100, 255);

	private static final boolean walkable = true;

	private static final boolean destroyable = false;

	public Spawner(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(c);
		g.fillRect(pos.getIX(), pos.getIY(), SIZE, SIZE);
	}

	@Override
	public void interact(Application m) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDestroyable() {
		return destroyable;
	}

	@Override
	public boolean isWalkable() {
		return walkable;
	}
}
