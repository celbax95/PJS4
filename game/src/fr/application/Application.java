package fr.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import fr.explosion.IExplosion;
import fr.itemsApp.Drawable;
import fr.itemsApp.Manageable;
import fr.itemsApp.character.CharacterFactory;
import fr.itemsApp.character.ICharacter;
import fr.map.GameMap;
import fr.util.point.Point;
import fr.util.time.Timer;

/**
 * Application (Le jeu)
 */
public class Application implements Runnable {
	private final static int spawnPlaces[][] = { { 120, 120 } };

	private Map<Integer, ICharacter> players;

	private List<Drawable> drawables;
	private List<Manageable> manageables;

	private GameMap map;

	private List<IExplosion> explosions;

	private Timer timerApploop;

	private Thread myThread;

	/**
	 * @param width  : Largeur de la fenetre
	 * @param height : Hauteur de la fenetre
	 */
	public Application(int width, int height) {
		players = new HashMap<>();
		drawables = new Vector<>();
		manageables = new Vector<>();
		explosions = new Vector<>();
		timerApploop = new Timer();
		myThread = new Thread(this);

		// Chargement de la map depuis le fichier "/maps/1.bmap"
		map = new GameMap("/maps/1.bmap");
	}

	/**
	 * Ajout d'un element Drawable a l'application
	 *
	 * @param drawable : Drawable a ajouter
	 */
	public void addDrawable(Drawable drawable) {
		drawables.add(drawable);
	}

	/**
	 * Ajoute une Explosion
	 *
	 * @param explosion : Explosion a ajouter
	 */
	public void addExplosion(IExplosion explosion) {
		explosions.add(explosion);
	}

	/**
	 * Ajout d'un element Manageable a l'application
	 *
	 * @param manageable : Manageable a ajouter
	 */
	public void addManageable(Manageable manageable) {
		manageables.add(manageable);
	}

	/**
	 * Ajout d'un joueur a l'application
	 *
	 * @param c : Couleur aleatoire du joueur
	 * @return : Id du joueur cree
	 */
	public int addPlayer() {
		int id = Math.abs((new Random()).nextInt());
		while (players.containsKey(id))
			id++;

		// Choix aleatoire parmi les emplacements de spawn existants
		int spawnPlace[] = spawnPlaces[(new Random().nextInt(spawnPlaces.length))];

		ICharacter character = CharacterFactory.getInstance().create("red");
		character.setPos(new Point(spawnPlace[0], spawnPlace[1]));
		character.setId(id);

		players.put(id, character);

		// Ajout du joueur au listes de gestion
		addDrawable(character);
		addManageable(character);

		return id;
	}

	/**
	 * Supprime un joueur de l'application
	 *
	 * @param id
	 */
	public void deletePlayer(int id) {
		ICharacter character = players.get(id);
		if (character != null) {
			players.remove(id);
			removeDrawable(character);
			removeManageable(character);
		}
	}

	/**
	 * @return Liste des elments Drawbles
	 */
	public List<Drawable> getDrawables() {
		return drawables;
	}

	/**
	 * @return liste des explosions en cours
	 */
	public List<IExplosion> getExplosions() {
		return explosions;
	}

	/**
	 * @return Liste des elments Manageable
	 */
	public List<Manageable> getManageables() {
		return manageables;
	}

	/**
	 * @return La map du jeu
	 */
	public GameMap getMap() {
		return map;
	}

	/**
	 * Gere les actions du Joueur
	 *
	 * @param id          : Id du joueur
	 * @param clickedKeys : Touches active du clavier du joueur
	 */
	public boolean managePlayer(int id, List<Integer> clickedKeys) {
		ICharacter character = players.get(id);
		if (character != null) {
			character.actions(this, clickedKeys);
			return true;
		} else
			return false;
	}

	/**
	 * Supprime un Drawable de l'application
	 *
	 * @param drawable : Drawable a supprimer
	 */
	public void removeDrawable(Drawable drawable) {
		drawables.remove(drawable);
	}

	/**
	 * Enleve une explosion de la liste d'explosions
	 * 
	 * @param explosion : explosion a enlever
	 */
	public void removeExplosion(IExplosion explosion) {
		explosions.remove(explosion);
	}

	/**
	 * Supprime un Manageable de l'application
	 *
	 * @param manageable : Manageable a supprimer
	 */
	public void removeManageable(Manageable manageable) {
		manageables.remove(manageable);
	}

	/**
	 * Gestion de tous les elements de l'application Boucle du jeu
	 */
	@Override
	public void run() {

		// Temps d'un passage dans la boucle while, pour la synchro des elements (ex :
		// vitesse de deplacement)
		timerApploop.tick();
		while (!Thread.currentThread().isInterrupted()) {

			// Gestion des elements
			for (int i = 0; i < manageables.size(); i++) {
				manageables.get(i).manage(this, timerApploop.lastTickS());
			}

			// Pause pour optimisation
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			timerApploop.tick();
		}
	}

	/**
	 * Lancement du jeu
	 */
	public void start() {
		myThread.start();
	}
}
