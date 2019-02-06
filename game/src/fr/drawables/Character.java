package fr.drawables;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.List;

import javax.swing.ImageIcon;

import fr.application.Application;
import fr.map.GameMap;
import fr.map.MapTile;
import fr.scale.Scale;
import fr.util.point.Point;
import fr.util.time.Cooldown;

public class Character implements Drawable, Serializable, Manageable {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_SIZE = 110;
	private static final int collideMargin = (int) (8 * Scale.getScale());

	private static int SIZE = (int) (DEFAULT_SIZE * Scale.getScale());

	private static Image imgS = (new ImageIcon(Character.class.getResource("/images/characters/red/stand.png")))
			.getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT);
	private static Image[] imgD = {
			(new ImageIcon(Character.class.getResource("/images/characters/red/walk_1.png"))).getImage()
			.getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT),
			(new ImageIcon(Character.class.getResource("/images/characters/red/walk_2.png"))).getImage()
			.getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT), };
	private static int freq = 60;

	private int speed;

	private double angle;

	private int step;

	private Cooldown bombCoolDown;

	private Point pos;
	private Point mouv;

	private Bomb b;

	public Character(Character c) {
		super();
		this.speed = c.speed;
		this.angle = c.angle;
		this.step = c.step;
		this.bombCoolDown = c.bombCoolDown;
		this.pos = c.pos;
		this.mouv = c.mouv;
		this.b = c.b;
	}

	public Character(double x, double y, int bombCoolDown, int speed) {
		b = new Bomb(0, 0, -1, 1, 0);
		pos = new Point(x, y);
		this.speed = (int) (speed * Scale.getScale());
		mouv = new Point(0, 0);
		angle = 0;
		step = 0;
		this.bombCoolDown = new Cooldown(bombCoolDown);
	}

	public void actions(Application a, List<Integer> keys) {
		setMove(keys);
		dropBomb(a,keys);
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform af = new AffineTransform();
		af.translate(pos.x, pos.y);
		af.rotate(angle, SIZE / 2, SIZE / 2);
		if (mouv.x == 0 && mouv.y == 0)
			g.drawImage(imgS, af, null);
		else {
			g.drawImage(imgD[(step < freq / 2) ? 0 : 1], af, null);
		}
	}

	public void dropBomb(Application a, List<Integer> keys) {
		if (keys.contains(KeyEvent.VK_R)) {
			if (bombCoolDown.resetOnDone()) {
				Bomb ab = b.clone();
				Point t = getCenter(); // tmp
				t = a.getMap().getTileFor(t.x, t.y);
				ab.setTile(t.getIX(), t.getIY(), 120);
				a.addDrawable(ab);
				a.addManageable(ab);
				ab.start();
			}
		}
	}

	public Point getCenter() {
		return new Point(pos.x + SIZE / 2, pos.y + SIZE / 2);
	}

	@Override
	public void manage(Application a, double t) {
		move(a.getMap(), t);
	}

	public void move(GameMap map, double t) {

		boolean cmx = true, cmy = true; // Can move x / y

		double x = pos.x + (mouv.x * speed * t);
		double y = pos.y + (mouv.y * speed * t);

		Point tile = map.getTileFor(x + SIZE / 2, y + SIZE / 2);

		MapTile[][] mapTiles = map.getMap();
		MapTile mt;
		for (int mtx = (int) (tile.x - 1); mtx <= tile.x + 1; mtx++) {
			for (int mty = (int) (tile.y - 1); mty <= tile.y + 1; mty++) {
				if (mtx < 0 || mtx > map.getWidth() || mty < 0 || mty > map.getHeight())
					continue;
				if (mtx == tile.x && mty == tile.y)
					continue;
				mt = mapTiles[mtx][mty];

				if (!mt.isWalkable()) {
					// X
					if (isAligned(pos.getIY() + collideMargin, SIZE - collideMargin * 2, mt.getPos().getIY(),
							mt.getSize())) {

						// Droit
						if (mouv.x > 0
								&& isBetween((int) x + SIZE, mt.getPos().getIX(), mt.getPos().getIX() + mt.getSize())) {
							x = mt.getPos().x - SIZE;
							// cmx = false;
						}

						// Gauche
						else if (mouv.x < 0
								&& isBetween((int) x, mt.getPos().getIX(), mt.getPos().getIX() + mt.getSize())) {
							x = mt.getPos().x + mt.getSize();
							// cmx = false;
						}
					}

					// Y
					if (isAligned(pos.getIX() + collideMargin, SIZE - collideMargin * 2, mt.getPos().getIX(),
							mt.getSize())) {

						// Haut
						if (mouv.y > 0
								&& isBetween((int) y + SIZE, mt.getPos().getIY(), mt.getPos().getIY() + mt.getSize())) {
							y = mt.getPos().y - SIZE;
							// cmy = false;
						}

						// Bas
						else if (mouv.y < 0
								&& isBetween((int) y, mt.getPos().getIY(), mt.getPos().getIY() + mt.getSize())) {
							y = mt.getPos().y + mt.getSize();
							// cmy = false;
						}
					}

				}
			}
		}

		if (cmx)
			pos.x = x;
		if (cmy)
			pos.y = y;

		step = (step + 1) % freq;
	}

	public void setMove(List<Integer> keys) {
		mouv.setLocation(0, 0);
		if (keys.contains(KeyEvent.VK_Z))
			mouv.y--;
		if (keys.contains(KeyEvent.VK_S))
			mouv.y++;
		if (keys.contains(KeyEvent.VK_Q))
			mouv.x--;
		if (keys.contains(KeyEvent.VK_D))
			mouv.x++;
		mouv.normalize();
		if (!(mouv.x == 0 && mouv.y == 0)) {
			angle = mouv.getAngle();
		}
	}

	private static boolean isAligned(int p1, int s1, int p2, int s2) {
		return ((p1 < p2 && p2 < p1 + s1) || (p2 < p1 && p1 < p2 + s2) || (p1 < p2 + s2 / 2 && p2 + s2 / 2 < p1 + s1));
	}

	public static boolean isBetween(int p, int p1, int p2) {
		return (p1 < p && p < p2);
	}
}
