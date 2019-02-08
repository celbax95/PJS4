package fr.tiles;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.application.Application;

public class BreakableWall extends MapTileSuper {

	private static final long serialVersionUID = 1L;
	private static Color c = Color.red;

	private static final boolean walkable = false;
	private static final boolean destroyable = true;

	public BreakableWall(int x, int y) {
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
