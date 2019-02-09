package fr.explosion;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import fr.application.Application;
import fr.scale.Scale;
import fr.util.point.Point;
import fr.util.time.Cooldown;

public class StandardExplosion implements IExplosion {

	private static final long serialVersionUID = 1L;

	private static final int TILE_SIZE = (int) (120 * Scale.getScale());

	private static Image[] img = {
			((new ImageIcon(Character.class.getResource("/images/explosions/standard/0.png"))).getImage()
					.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT)),
			((new ImageIcon(Character.class.getResource("/images/explosions/standard/1.png"))).getImage()
					.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT)),
			((new ImageIcon(Character.class.getResource("/images/explosions/standard/2.png"))).getImage()
					.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT)),

	};

	private int type;

	private Point tile, pos;

	private double damage;

	private int tileSize;

	private Cooldown cd;

	public StandardExplosion() {
		tileSize = TILE_SIZE;
		type = 0;
		tile = new Point(0, 0);
		this.pos = getPosfromTile(tile);
		this.damage = 0;
		this.cd = new Cooldown(0);
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img[type], pos.getIX(), pos.getIY(), null);
	}

	private Point getPosfromTile(Point tile) {
		return new Point(tile.x * tileSize, tile.y * tileSize);
	}

	@Override
	public void manage(Application a, double t) {
		tileSize = a.getMap().getTileSize();
		if (cd.isDone()) {
			a.removeDrawable(this);
			a.removeManageable(this);
		}
	}

	@Override
	public void setCooldown(int cd) {
		this.cd = new Cooldown(cd);
	}

	@Override
	public void setDamage(double damage) {
		this.damage = damage;
	}

	@Override
	public void setTile(Point tile) {
		this.tile = tile;
		this.pos = getPosfromTile(tile);
	}

	@Override
	public void setType(int type) {
		this.type = type;
	}
}
