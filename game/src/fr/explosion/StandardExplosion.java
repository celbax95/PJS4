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
		this.pos = new Point(tile.x * tileSize, tile.y * tileSize);
		this.damage = 0;
		this.cd = new Cooldown(0);
	}

	public StandardExplosion(int type, int tileX, int tileY, int tileSize, double damage, int time) {
		this.type = type;
		this.tile = new Point(tileX, tileY);
		this.pos = new Point(tileX * TILE_SIZE, tileY * TILE_SIZE);
		this.damage = damage;
		this.cd = new Cooldown(time);
		this.tileSize = tileSize;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img[type], pos.getIX(), pos.getIY(), null);
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
	}

	@Override
	public void setType(int type) {
		this.type = type;
	}
}
