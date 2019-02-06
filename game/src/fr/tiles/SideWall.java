package fr.tiles;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.application.Application;

public class SideWall extends MapTileSuper {

	private static final long serialVersionUID = 1L;
	private static Color c = new Color(0, 0, 0);

	public SideWall(int x, int y) {
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
	public boolean isWalkable() {
		return false;
	}
}
