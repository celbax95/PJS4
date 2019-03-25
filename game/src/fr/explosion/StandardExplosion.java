package fr.explosion;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import fr.application.Application;

public class StandardExplosion extends Explosion {

	private static final long serialVersionUID = 1L;

	private static Image[] img = {
			((new ImageIcon(Character.class.getResource("/images/explosions/standard/0.png"))).getImage()
					.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT)),
			((new ImageIcon(Character.class.getResource("/images/explosions/standard/1.png"))).getImage()
					.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT)),
			((new ImageIcon(Character.class.getResource("/images/explosions/standard/2.png"))).getImage()
					.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT)),
	};

	public StandardExplosion() {
		super();
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img[type], pos.getIX(), pos.getIY(), null);
	}

	@Override
	public void manage(Application a, double t) {
		tileSize = a.getMap().getTileSize();
		if (explosionTime.isDone()) {
			a.removeDrawable(this);
			a.removeManageable(this);
		}
	}
}
