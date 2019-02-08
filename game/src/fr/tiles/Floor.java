package fr.tiles;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import fr.application.Application;

public class Floor extends MapTileSuper {

	private static final long serialVersionUID = 1L;
	private static Image img = (new ImageIcon(Floor.class.getResource("/images/map/floor/floor.png"))).getImage()
			.getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT);

	private static final boolean walkable = true;

	private static final boolean destroyable = false;

	public Floor(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, pos.getIX(), pos.getIY(), null);
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
