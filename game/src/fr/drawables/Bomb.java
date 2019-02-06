package fr.drawables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

import fr.application.Application;
import fr.scale.Scale;
import fr.util.point.Point;
import fr.util.time.Cooldown;

public class Bomb implements Drawable, Serializable, Manageable {

	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_COOLDOWN = 4000;
	private static final int DEFAULT_BOMB_SIZE = 80;

	private static int SIZE = (int) (DEFAULT_BOMB_SIZE * Scale.getScale());

	private static Color c = Color.black;

	private Point pos, tile;

	private Cooldown cd;

	private int explosionSize;

	public Bomb(Bomb b) {
		this.pos = b.pos.clone();
		this.tile = b.tile.clone();
		this.cd = b.cd.clone();
		this.explosionSize = b.explosionSize;
	}

	public Bomb(int x, int y, int tileSize) {
		tile = new Point(x, y);
		pos = new Point(x * tileSize + (tileSize - SIZE) / 2, y * tileSize + (tileSize - SIZE) / 2);
		cd = new Cooldown(5000);
	}

	public Bomb(int tileX, int tileY, int cooldown, int explosionSize, int tileSize) {
		tile = new Point(tileX, tileY);
		pos = getPosFromTile(tile, tileSize);
		cd = new Cooldown(cooldown < 0 ? DEFAULT_COOLDOWN : cooldown);
		this.explosionSize = explosionSize;
	}

	@Override
	public Bomb clone() {
		return new Bomb(this);
	}


	@Override
	public void draw(Graphics2D g) {
		g.setColor(c);
		g.fillRect(pos.getIX(), pos.getIY(), SIZE, SIZE);

	}

	public void explode(Application a) {
		a.removeDrawable(this);
		a.removeManageable(this);
	}

	public Point getPosFromTile(Point t, int tileSize) {
		return new Point(t.x * tileSize + (tileSize - SIZE) / 2, t.y * tileSize + (tileSize - SIZE) / 2);
	}

	@Override
	public void manage(Application a, double t) {
		if (cd.isDone()) {
			explode(a);
		}
	}

	public void setCooldown(int time) {
		cd.setFreq(time);
	}

	public void setTile(int x, int y, int tileSize) {
		tile = new Point(x, y);
		pos = getPosFromTile(tile, tileSize);
	}

	public void start() {
		cd.start();
	}

}
