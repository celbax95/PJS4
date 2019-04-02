package fr.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import fr.explosion.IExplosion;
import fr.itemsApp.Drawable;
import fr.itemsApp.Manageable;
import fr.itemsApp.bomb.IBomb;
import fr.itemsApp.character.CharacterFactory;
import fr.itemsApp.character.ICharacter;
import fr.itemsApp.items.PlaceableItem;
import fr.map.GameMap;
import fr.util.point.Point;
import fr.util.point.PointCalc;
import fr.util.time.Timer;

/**
 * Application (Le jeu)
 */
public class Application implements Runnable {

	private List<Point> spawnPlaces;
	private int currentSpawnPoint;

	private Map<Integer, ICharacter> players;

	private List<Drawable> drawables;
	private List<Manageable> manageables;

	private GameMap map;

	private List<IExplosion> explosions;
	private List<IBomb> bombs;
	private List<PlaceableItem> items;

	private Timer timerApploop;

	private Thread myThread;

	/**
	 * @param width  : Largeur de la fenetre
	 * @param height : Hauteur de la fenetre
	 */
	public Application(int width, int height) {
		this.players = new HashMap<>();
		this.drawables = new Vector<>();
		this.manageables = new Vector<>();
		this.explosions = new Vector<>();
		this.bombs = new Vector<>();
		this.items = new Vector<>();
		this.timerApploop = new Timer();
		this.myThread = new Thread(this);

		// Chargement de la map depuis le fichier "/maps/1.bmap"
		this.map = new GameMap("/maps/2.bmap");
		this.spawnPlaces = this.map.getSpawnPoints();
		this.currentSpawnPoint = 0;
	}

	/**
	 * Ajoute une bombe a la liste des bombes
	 *
	 * @param bomb : bombe a ajouter
	 */
	public void addBomb(IBomb bomb) {
		this.bombs.add(bomb);
	}

	/**
	 * Ajout d'un element Drawable a l'application
	 *
	 * @param drawable : Drawable a ajouter
	 */
	public void addDrawable(Drawable drawable) {
		this.drawables.add(drawable);
	}

	/**
	 * Ajoute une Explosion
	 *
	 * @param explosion : Explosion a ajouter
	 */
	public void addExplosion(IExplosion explosion) {
		this.explosions.add(explosion);
	}

	/**
	 * Ajoute un Item
	 *
	 * @param item : Item a ajouter
	 */
	public void addItem(PlaceableItem item) {
		this.items.add(item);
	}

	/**
	 * Ajout d'un element Manageable a l'application
	 *
	 * @param manageable : Manageable a ajouter
	 */
	public void addManageable(Manageable manageable) {
		this.manageables.add(manageable);
	}

	/**
	 * Ajout d'un joueur a l'application
	 *
	 * @param c : Couleur aleatoire du joueur
	 * @return : Id du joueur cree
	 */
	public void addPlayer(int no) {
		int id = no;

		// Choix aleatoire parmi les emplacements de spawn existants
		Point spawnPlace = this.spawnPlaces.get(this.currentSpawnPoint);
		this.currentSpawnPoint = (this.currentSpawnPoint + 1) % this.spawnPlaces.size();

		ICharacter character = CharacterFactory.getInstance().create("red");
		character.setPos(new Point(spawnPlace.x, spawnPlace.y));
		character.setId(id);

		character.setAngle(PointCalc.getAngle(PointCalc.sub(this.map.getCenter(), character.getCenter())));

		this.players.put(id, character);

		// Ajout du joueur au listes de gestion
		this.addDrawable(character);
		this.addManageable(character);
	}

	/**
	 * Supprime un joueur de l'application
	 *
	 * @param id
	 */
	public void deletePlayer(int id) {
		ICharacter character = this.players.get(id);
		if (character != null) {
			this.players.remove(id);
			this.removeDrawable(character);
			this.removeManageable(character);
		}
	}
	
	/**
	 * Renvoie le nombre de joueurs en vie
	 */
	public int getPlayersNb() {
		return this.players.size();
	}
	/**
	 * @return La liste de toutes les bombes
	 */
	public List<IBomb> getBombs() {
		return this.bombs;
	}

	/**
	 * @return Liste des elments Drawbles
	 */
	public List<Drawable> getDrawables() {
		return this.drawables;
	}

	/**
	 * @return liste des explosions en cours
	 */
	public List<IExplosion> getExplosions() {
		return this.explosions;
	}

	/**
	 * @return liste des items
	 */
	public List<PlaceableItem> getItems() {
		return this.items;
	}

	/**
	 * @return Liste des elments Manageable
	 */
	public List<Manageable> getManageables() {
		return this.manageables;
	}

	/**
	 * @return La map du jeu
	 */
	public GameMap getMap() {
		return this.map;
	}

	public ICharacter getPlayer(int i) {
		try {
			return this.players.get(i);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Gere les actions du Joueur
	 *
	 * @param id          : Id du joueur
	 * @param clickedKeys : Touches active du clavier du joueur
	 */
	public boolean managePlayer(int id, List<Integer> clickedKeys) {
		ICharacter character = this.players.get(id);
		if (character != null) {
			character.actions(this, clickedKeys);
			return true;
		} else
			return false;
	}

	/**
	 * Supprime une bombe de la liste des bombes
	 *
	 * @param bomb : la bombe a supprimer
	 */
	public void removeBomb(IBomb bomb) {
		this.bombs.remove(bomb);
	}

	/**
	 * Supprime un Drawable de l'application
	 *
	 * @param drawable : Drawable a supprimer
	 */
	public void removeDrawable(Drawable drawable) {
		this.drawables.remove(drawable);
	}

	/**
	 * Enleve une explosion de la liste d'explosions
	 *
	 * @param explosion : explosion a enlever
	 */
	public void removeExplosion(IExplosion explosion) {
		this.explosions.remove(explosion);
	}

	/**
	 * Enleve un item de la liste d'item
	 *
	 * @param item : item a enlever
	 */
	public void removeItem(PlaceableItem item) {
		this.items.remove(item);
	}

	/**
	 * Supprime un Manageable de l'application
	 *
	 * @param manageable : Manageable a supprimer
	 */
	public void removeManageable(Manageable manageable) {
		this.manageables.remove(manageable);
	}

	/**
	 * Gestion de tous les elements de l'application Boucle du jeu
	 */
	@Override
	public void run() {

		// Temps d'un passage dans la boucle while, pour la synchro des elements (ex :
		// vitesse de deplacement)
		this.timerApploop.tick();
		while (!Thread.currentThread().isInterrupted()) {

			// Gestion des elements
			for (int i = 0; i < this.manageables.size(); i++)
				this.manageables.get(i).manage(this, this.timerApploop.lastTickS());

			// Pause pour optimisation
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			this.timerApploop.tick();
		}
	}

	/**
	 * Lancement du jeu
	 */
	public void start() {
		this.myThread.start();
	}

	public void stop() {
		this.myThread.interrupt();
	}

	public ArrayList<Integer> getPlayersKeys() {
		return new ArrayList<Integer>(this.players.keySet());
	}
}
