package fr.itemsApp.character;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.ImageIcon;

import fr.application.Application;
import fr.itemsApp.bomb.BombFactory;
import fr.itemsApp.bomb.IBomb;
import fr.map.GameMap;
import fr.map.MapTile;
import fr.scale.Scale;
import fr.util.point.Point;
import fr.util.time.Cooldown;

/**
 * Personnage
 */
public class CharacterRed implements ICharacter {

	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_SIZE = 110;
	private static final int collideMargin = (int) (8 * Scale.getScale());

	private static int SIZE = (int) (DEFAULT_SIZE * Scale.getScale());

	private static Image imgS = (new ImageIcon(CharacterRed.class.getResource("/images/characters/red/stand.png")))
			.getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT);
	private static Image[] imgD = {
			(new ImageIcon(CharacterRed.class.getResource("/images/characters/red/walk_1.png"))).getImage()
					.getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT),
			(new ImageIcon(CharacterRed.class.getResource("/images/characters/red/walk_2.png"))).getImage()
					.getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT), };

	private static final int walkFrequence = 60;

	private static final double MAX_HEALTH = 100;

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

	private int speed;

	private double angleOfView;

	private int walkStep;

	private String defaultBomb;

	private Cooldown bombCoolDown;

	private Point pos;

	private Point moves;

	private BombFactory bombFactory;

	private double health;

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
	public CharacterRed(double x, double y, int bombCoolDown, int speed) {
		pos = new Point(x, y);
		this.speed = (int) (speed * Scale.getScale());
		moves = new Point(0, 0);
		angleOfView = 0;
		walkStep = 0;
		this.bombCoolDown = new Cooldown(bombCoolDown);
		this.bombFactory = new BombFactory();
		defaultBomb = "std";
		health = MAX_HEALTH;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fr.itemsApp.character.ICharacter#actions(fr.application.Application,
	 * java.util.List)
	 */
	@Override
	public void actions(Application application, List<Integer> clickedKeys) {
		setMoves(clickedKeys);
		dropBomb(application, clickedKeys);
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform af = new AffineTransform();
		af.translate(pos.x, pos.y);
		af.rotate(angleOfView, SIZE / 2, SIZE / 2);

		// Affichage du skin statique ou en mouvement
		if (moves.x == 0 && moves.y == 0)
			g.drawImage(imgS, af, null);
		else {
			g.drawImage(imgD[(walkStep < walkFrequence / 2) ? 0 : 1], af, null);
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
			if (bombCoolDown.resetOnDone()) {
				IBomb bomb = bombFactory.create(defaultBomb, application, this);
				IBomb.addToLists(application, bomb);
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

	/*
	 * (non-Javadoc)
	 *
	 * @see fr.itemsApp.character.ICharacter#getCenter()
	 */
	@Override
	public Point getCenter() {
		return new Point(pos.x + SIZE / 2, pos.y + SIZE / 2);
	}

	/**
	 * @return health : les points de vie du joueur
	 */
	@Override
	public double getHealth() {
		return health;
	}

	@Override
	public double getMaxHealth() {
		return MAX_HEALTH;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fr.itemsApp.character.ICharacter#manage(fr.application.Application,
	 * double)
	 */
	@Override
	public void manage(Application application, double timeSinceLastCall) {
		move(application.getMap(), timeSinceLastCall);
	}

	/**
	 * Bouge le joueur, et gere les collisions
	 *
	 * @param map
	 *            : Carte de la partie
	 * @param t
	 *            : Temps entre chaque appel
	 */
	public void move(GameMap map, double t) {

		double x = pos.x + (moves.x * speed * t);
		double y = pos.y + (moves.y * speed * t);

		Point tile = map.getTileFor(x + SIZE / 2, y + SIZE / 2);

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
					if (isAligned(pos.getIY() + collideMargin, SIZE - collideMargin * 2, mapTile.getPos().getIY(),
							mapTile.getSize())) {

						// Droite
						if (moves.x > 0 && isBetween((int) x + SIZE, mapTile.getPos().getIX(),
								mapTile.getPos().getIX() + mapTile.getSize())) {
							x = mapTile.getPos().x - SIZE;
							// On ne peut pas aller a droite
						}

						// Gauche
						else if (moves.x < 0 && isBetween((int) x, mapTile.getPos().getIX(),
								mapTile.getPos().getIX() + mapTile.getSize())) {
							x = mapTile.getPos().x + mapTile.getSize();
							// On ne peut pas aller a gauche
						}
					}

					// Y
					if (isAligned(pos.getIX() + collideMargin, SIZE - collideMargin * 2, mapTile.getPos().getIX(),
							mapTile.getSize())) {

						// Haut
						if (moves.y > 0 && isBetween((int) y + SIZE, mapTile.getPos().getIY(),
								mapTile.getPos().getIY() + mapTile.getSize())) {
							y = mapTile.getPos().y - SIZE;
							// On ne peut pas aller en haut
						}

						// Bas
						else if (moves.y < 0 && isBetween((int) y, mapTile.getPos().getIY(),
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
	public void setBombCoolDown(int bombCoolDown) {
		this.bombCoolDown = new Cooldown(bombCoolDown);
	}

	@Override
	public void setHealth(double health) {
		this.health = health;
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
}
