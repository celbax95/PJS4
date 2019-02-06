package fr.tiles;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.application.Application;

public class UnbreakableWall extends MapTileSuper {

	private static final long serialVersionUID = 1L;
	private static Color c = new Color(50, 50, 50);

	public UnbreakableWall(int x, int y, int size) {
		super(x, y, size);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(c);
		g.fillRect(pos.getIX(), pos.getIY(), size, size);
	}

	@Override
	public void interact(Application m) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isWalkable() {
		return false;
	}
}
