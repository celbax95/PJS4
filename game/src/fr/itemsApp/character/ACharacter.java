package fr.itemsApp.character;

import java.awt.event.KeyEvent;
import java.util.List;

import fr.application.Application;
import fr.explosion.IExplosion;
import fr.itemsApp.bomb.BombFactory;
import fr.itemsApp.bomb.IBomb;
import fr.map.GameMap;
import fr.map.MapTile;
import fr.util.point.Point;
import fr.util.point.PointCalc;
import fr.util.time.Cooldown;

/**
 * Personnage
 */
public abstract class ACharacter implements ICharacter {

	private static final long serialVersionUID = 1L;
	protected static final int DEFAULT_SIZE = 110;
	protected static final int collideMargin = 8;

	protected static final int walkFrequence = 60;

	protected static final int timeBetweenDamages = 500;

	protected static final int MAX_HEALTH = 100;

	/**
	 * Verification de l'alignement entre deux intervalles
	 *
	 * @param p1
	 *            : Position de la premiere borne de l'intervalle 1
	 * @param s1
	 *            : Distance jusqu'a la deuxieme borne de l'intervale 1
	 * @param p2
	 *            : Position de la premiere borne de l'intervalle 2
	 * @param s2
	 *            : Distance jusqu'a la deuxieme borne de l'intervale 2
	 * @return Intervalles alignees
	 */
	private static boolean isAligned(int p1, int s1, int p2, int s2) {
		return (isBetween(p2, p1, p1 + s1) || isBetween(p1, p2, p2 + s2) || isBetween(p2 + s2 / 2, p1, p1 + s1));
	}

	/**
	 * Verification qu'un point est entre deux autres points sur une droite
	 *
	 * @param p
	 *            : point test
	 * @param p1
	 *            : point 1
	 * @param p2
	 *            : point 2
	 * @return Le point est entre les deux autres
	 */
	public static boolean isBetween(int p, int p1, int p2) {
		return (p1 < p && p < p2);
	}

	protected int speed;

	protected double angleOfView;

	protected int walkStep;

	protected Cooldown lastDamage;

	protected String defaultBomb;

	protected Cooldown bombCoolDown;

	protected Point pos;

	protected Point moves;

	protected int id;

	protected int health;

	/**
	 * @param x
	 *            : Position x
	 * @param y
	 *            : Position y
	 * @param bombCoolDown
	 *            : Temps entre chaque pose de bombe
	 * @param speed
	 *            : Vitesse du personnage
	 */
	public ACharacter(double x, double y, int health, int bombCoolDown, int speed) {
		pos = new Point(x, y);
		this.speed = speed;
		this.health = health;
		lastDamage = new Cooldown(timeBetweenDamages);
		moves = new Point(0, 0);
		angleOfView = 0;
		walkStep = 0;
		this.bombCoolDown = new Cooldown(bombCoolDown);
		defaultBomb = "std";
	}

	@Override
	public void actions(Application application, List<Integer> clickedKeys) {
		setMoves(clickedKeys);
		dropBomb(application, clickedKeys);
	}

	@Override
	public void damage(int health) {
		this.health -= health;
		lastDamage.reset();
	}

	/**
	 * Gere si le joueur est mort
	 *
	 * @param application
	 *            : application
	 */
	private void death(Application application) {
		if (health <= 0) {
			application.deletePlayer(id);
		}
	}

	/**
	 * Pose une bombe sur la tile sur laquelle est le character
	 *
	 * @param application
	 *            : Application
	 * @param clickedKeys
	 *            : Touches appuyees du clavier
	 */
	public void dropBomb(Application application, List<Integer> clickedKeys) {
		if (clickedKeys.contains(KeyEvent.VK_R)) {
			if (bombCoolDown.isDone()) {
				Point tile = application.getMap().getTileFor(pos.getIX(), pos.getIY());
				List<IBomb> bombs = application.getBombs();
				for (IBomb b : bombs) {
					Point btile = b.getTile();
					if (btile.x == tile.x && btile.y == tile.y)
						return;
				}

				IBomb bomb = BombFactory.getInstance().create(defaultBomb, application, this);
				IBomb.addToLists(application, bomb);
				bombCoolDown.reset();
				bomb.start();
			}
		}
	}

	/**
	 * @return Temps entre chaque pose de bombe
	 */
	public Cooldown getBombCoolDown() {
		return bombCoolDown;
	}

	@Override
	public Point getCenter() {
		return new Point(pos.x + DEFAULT_SIZE / 2, pos.y + DEFAULT_SIZE / 2);
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public int getMaxHealth() {
		return MAX_HEALTH;
	}

	@Override
	public void manage(Application application, double timeSinceLastCall) {
		move(application, timeSinceLastCall);
		setDamage(application);
		death(application);
	}

	@Override
	public int maxTimeBeforeBomb() {
		return (int) bombCoolDown.getFreq();
	}

	/**
	 * Bouge le joueur, et gere les collisions
	 *
	 * @param map
	 *            : Carte de la partie
	 * @param t
	 *            : Temps entre chaque appel
	 */
	public void move(Application application, double t) {

		GameMap map = application.getMap();

		double x = pos.x;
		double y = pos.y;

		double newSpeed = speed;

		List<IBomb> bombs = application.getBombs();

		for (IBomb b : bombs) {
			if (isAligned(pos.getIX() + collideMargin, DEFAULT_SIZE - collideMargin * 2, b.getPos().getIX(),
					b.getSize().getIX())
					&& isAligned(pos.getIY() + collideMargin, DEFAULT_SIZE - collideMargin * 2, b.getPos().getIY(),
							b.getSize().getIY())) {
				// Si le Character est sur une bombe

				if (moves.x == 0 && moves.y == 0) {
					// Si le Character est immobile

					Point center = getCenter();
					Point bcenter = b.getCenter();

					Point pushDir = PointCalc.sub(center, bcenter);
					pushDir.normalize();

					x += (pushDir.x * speed / 1.5 * t);
					y += (pushDir.y * speed / 1.5 * t);
				} else {
					newSpeed *= 0.5;
				}
			}
		}

		x += (moves.x * newSpeed * t);
		y += (moves.y * newSpeed * t);

		Point tMove = new Point(x - pos.x, y - pos.y);
		tMove.normalize();

		Point tile = map.getTileFor(x + DEFAULT_SIZE / 2, y + DEFAULT_SIZE / 2);

		MapTile[][] mapTiles = map.getMap();
		MapTile mapTile;

		// Collisions
		for (int mtx = (int) (tile.x - 1); mtx <= tile.x + 1; mtx++) {
			for (int mty = (int) (tile.y - 1); mty <= tile.y + 1; mty++) {

				// Selection d'un tile dans la map
				if (mtx < 0 || mtx > map.getWidth() || mty < 0 || mty > map.getHeight())
					continue;
				if (mtx == tile.x && mty == tile.y)
					continue;

				mapTile = mapTiles[mtx][mty];

				// Verification de collision avec les tiles nom walkable
				if (!mapTile.isWalkable()) {
					// X
					if (isAligned(pos.getIY() + collideMargin, DEFAULT_SIZE - collideMargin * 2,
							mapTile.getPos().getIY(), mapTile.getSize())) {

						// Droite
						if (tMove.x > 0 && isBetween((int) x + DEFAULT_SIZE, mapTile.getPos().getIX(),
								mapTile.getPos().getIX() + mapTile.getSize())) {
							x = mapTile.getPos().x - DEFAULT_SIZE;
							// On ne peut pas aller a droite
						}

						// Gauche
						else if (tMove.x < 0 && isBetween((int) x, mapTile.getPos().getIX(),
								mapTile.getPos().getIX() + mapTile.getSize())) {
							x = mapTile.getPos().x + mapTile.getSize();
							// On ne peut pas aller a gauche
						}
					}

					// Y
					if (isAligned(pos.getIX() + collideMargin, DEFAULT_SIZE - collideMargin * 2,
							mapTile.getPos().getIX(), mapTile.getSize())) {

						// Haut
						if (tMove.y > 0 && isBetween((int) y + DEFAULT_SIZE, mapTile.getPos().getIY(),
								mapTile.getPos().getIY() + mapTile.getSize())) {
							y = mapTile.getPos().y - DEFAULT_SIZE;
							// On ne peut pas aller en haut
						}

						// Bas
						else if (tMove.y < 0 && isBetween((int) y, mapTile.getPos().getIY(),
								mapTile.getPos().getIY() + mapTile.getSize())) {
							y = mapTile.getPos().y + mapTile.getSize();
							// On ne peut pas aller en bas
						}
					}
				}
			}
		}

		// Affectation du deplacement
		pos.x = x;
		pos.y = y;

		// Mise a jour de l'animation de deplacement
		walkStep = (walkStep + 1) % walkFrequence;
	}

	@Override
	public void setAngle(double angle) {
		this.angleOfView = angle;
	}

	@Override
	public void setBombCoolDown(int bombCoolDown) {
		this.bombCoolDown = new Cooldown(bombCoolDown);
	}

	/**
	 * Applique les degats au joueur s'il doit en recevoir
	 *
	 * @param application
	 */
	private void setDamage(Application application) {

		if (!lastDamage.isDone())
			return;

		// Explosion Damage
		List<IExplosion> explosions = application.getExplosions();

		Point tile = getCenter();
		tile = application.getMap().getTileFor(tile.x, tile.y);

		for (IExplosion e : explosions) {
			Point eTile = e.getTile();
			if (tile.x == eTile.x && tile.y == eTile.y)
				damage(e.getDamage());
		}
	}

	@Override
	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Demande au character de bouger dans une direction
	 *
	 * @param clickedKeys
	 *            : Touches appuyees du clavier
	 */
	public void setMoves(List<Integer> clickedKeys) {
		moves.setLocation(0, 0);
		if (clickedKeys.contains(KeyEvent.VK_Z))
			moves.y--;
		if (clickedKeys.contains(KeyEvent.VK_S))
			moves.y++;
		if (clickedKeys.contains(KeyEvent.VK_Q))
			moves.x--;
		if (clickedKeys.contains(KeyEvent.VK_D))
			moves.x++;

		// Normalisation pour que le personnage n'aille pas plus vite en diagonale
		moves.normalize();

		if (!(moves.x == 0 && moves.y == 0)) {
			angleOfView = moves.getAngle();
		}
	}

	@Override
	public void setPos(Point pos) {
		this.pos = pos;
	}

	@Override
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public int timeBeforeBomb() {
		return (int) bombCoolDown.timeBefore();
	}
}
