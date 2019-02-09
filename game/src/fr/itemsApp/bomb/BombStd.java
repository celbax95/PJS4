package fr.itemsApp.bomb;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.application.Application;
import fr.explosion.ExplosionCreator;
import fr.scale.Scale;
import fr.util.point.Point;
import fr.util.time.Cooldown;

public class BombStd implements IBomb {

	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_COOLDOWN = 4000;
	private static final int DEFAULT_BOMB_SIZE = 80;

	private static int SIZE = (int) (DEFAULT_BOMB_SIZE * Scale.getScale());

	private static Color c = Color.black;

	private static final int TIME = 1400;
	private static final int DAMAGE = 333;

	private Point pos, tile;

	private Cooldown cd;
	private int explosionSize;
	private int tileSize;

	public BombStd(Point tile, int tileSize, int cooldown, int explosionSize) {
		super();
		this.tile = tile;
		this.pos = new Point((tile.getIX() * tileSize) + (tileSize - SIZE) / 2,
				(tile.getIY() * tileSize) + (tileSize - SIZE) / 2);
		this.cd = new Cooldown(cooldown);
		this.explosionSize = explosionSize;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(c);
		g.fillRect(pos.getIX(), pos.getIY(), SIZE, SIZE);
	}

	@Override
	public void explode(Application a) {
		ExplosionCreator ec = new ExplosionCreator();

		ec.create(a, tile, explosionSize, DAMAGE, TIME);
		a.removeDrawable(this);
		a.removeManageable(this);
	}

	@Override
	public void manage(Application a, double t) {
		if (cd.isDone()) {
			tileSize = a.getMap().getTileSize();
			explode(a);
		}
	}

	@Override
	public void start() {
		cd.start();
	}

	public static int getSIZE() {
		return SIZE;
	}
}
