package fr.tiles;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import fr.application.Application;

public class Floor extends MapTileSuper {

	private static final int scale = 120;

	private static final long serialVersionUID = 1L;
	private static Image img = (new ImageIcon(Floor.class.getResource("/images/map/floor/floor.png"))).getImage()
			.getScaledInstance(scale, scale, Image.SCALE_DEFAULT);

	public Floor(int x, int y, int size) {
		super(x, y, size);
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
	public boolean isWalkable() {
		return true;
	}
}
