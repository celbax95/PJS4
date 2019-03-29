package fr.appCli;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.sun.glass.events.KeyEvent;

import fr.camera.Camera;
import fr.client.Client;
import fr.gameLauncher.GameLauncher;
import fr.hud.HUD;
import fr.itemsApp.Drawable;
import fr.itemsApp.character.ICharacter;
import fr.map.GameMap;
import fr.map.MapTile;
import fr.scale.Scale;
import fr.screen.AppliScreen;
import fr.screen.EndApp;
import fr.screen.keyboard.KeyBoard;
import fr.util.point.Point;

/**
 * Application client, recoit et affiche les donnees recues du serveur
 */
public class AppliClient implements AppliScreen, Runnable {

	private List<Drawable> listD;

	private String name;
	private boolean endApp;

	private Object transfer;
	private Socket socket;
	private Thread myThread;

	private GameMap map;
	
	ObjectInputStream sIn;
	ObjectOutputStream sOut;

	private Camera camera;
	private Object transferPlayer;
	private ICharacter player;

	private double changingScale;

	private HUD hud;

	private boolean receiving;

	/**
	 * @param name   : Nom de la fenetre de jeu
	 * @param socket : Socket liee au serveur
	 */
	public AppliClient(String name, Socket socket, ObjectInputStream sIn, ObjectOutputStream sOut) {
		this.transfer = new Object();

		this.name = name;
		this.endApp = false;

		this.socket = socket;
		this.sIn = sIn;
		this.sOut = sOut;

		myThread = new Thread(this);

		listD = new ArrayList<>();
	}
	
	public AppliClient(String name, Socket socket) {
		this.transfer = new Object();
		this.transferPlayer = new Object();

		this.name = name;
		this.endApp = false;
		this.receiving = false;

		this.socket = socket;
		// testVersion(socket);

		myThread = new Thread(this);

		listD = new ArrayList<>();
	}

	/**
	 * Change le scale suivant les touches du clavier pressees
	 *
	 * @param g
	 */
	private double changeScale() {

		KeyBoard keyBoard = KeyBoard.getInstance();

		Scale scale = Scale.getInstance();

		// reception de commande pour changer le scale
		if (keyBoard.isPressed(KeyEvent.VK_ADD))
			scale.increase();
		else if (keyBoard.isPressed(KeyEvent.VK_SUBTRACT))
			scale.decrease();
		changingScale = scale.update();

		if (changingScale != 0) {
			// alignement de la camera
			camera.move(-changingScale * map.getHeight() * map.getTileSize() * 0.5,
					-changingScale * map.getHeight() * map.getTileSize() * 0.5);
		}

		return scale.getScale();
	}

	/**
	 * Met fin a l'application client
	 */
	@Override
	public void close() {
		myThread.interrupt();
		endApp = true;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ferme la socket Client
	 */
	public void closeSocket() {
		try {
			System.out.println("FIN");
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Affiche les donnees recues par le serveur (Map et Elements)
	 *
	 * @see run
	 */
	@Override
	public void draw(Graphics2D g) throws EndApp {
		if (endApp)
			throw new EndApp();

		if (!receiving)
			return;

		AffineTransform oldGTransform = g.getTransform();

		// Eventuel changement d'echelle
		double scale = changeScale();

		// Calcul de la position de la camera
		setCamera(g);

		g.scale(scale, scale);

		// creation d'un cache
		List<Drawable> ld;

		// initialisation du cache
		synchronized (transfer) {
			ld = listD;
		}

		// Affichage de la map
		MapTile[][] mt = map.getMap();
		if (mt != null) {
			for (MapTile[] mapTiles : mt) {
				for (MapTile mapTile : mapTiles) {
					mapTile.draw(g);
				}
			}
		}

		// Affichage des elements
		for (Drawable drawable : ld) {
			drawable.draw(g);
		}

		g.setTransform(oldGTransform);

		if (player != null) {
			hud.setCharacter(player);
			hud.draw(g);
		}
	}

	@Override
	public Color getBackgroundColor() {
		return new Color(60, 60, 60);
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Recoit les donnees serveur et envoie les inputs du client (clavier)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			// Creation du cache
			List<Drawable> listDrawables;

			KeyBoard keyBoard = KeyBoard.getInstance();
			ObjectOutputStream sOut;
			ObjectInputStream sIn;
			// Input et Output de la socket
			if(this.sIn == null && this.sOut == null) {
				sOut = new ObjectOutputStream((socket.getOutputStream()));
				sIn = new ObjectInputStream((socket.getInputStream()));
				this.sOut = sOut;
				this.sIn = sIn;
			}
			else {
				sOut = this.sOut;
				sIn = this.sIn;
			}
			

			// Tant que l'application n'est pas terminee
			while (!Thread.currentThread().isInterrupted()) {

				// Recuperation de la camera
				synchronized (transferPlayer) {
					player = (ICharacter) sIn.readUnshared();
				}

				// Recuperation de la map
				map = (GameMap) sIn.readUnshared();

				// Recuperation des elements
				listDrawables = (List<Drawable>) sIn.readUnshared();

				// Elements dans le cache
				synchronized (transfer) {
					listD = new ArrayList<>(listDrawables);
				}

				sOut.writeUnshared(keyBoard.getKeys());
				sOut.flush();
				sOut.reset();

				if (!receiving) {
					camera = new Camera(new Point(Client.WIDTH, Client.HEIGHT),
							new Point(map.getWidth() * map.getTileSize(), map.getHeight() * map.getTileSize()),
							player.getCenter());

					hud = HUD.getInstance();

					receiving = true;
				}
			}
			this.sOut.close();
			this.sIn.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Communication avec le serveur terminee");
			close();
			GameLauncher.resetMenu();
		} catch (ClassNotFoundException e1) {
			System.err.println("Erreur de Reception depuis le serveur");
			close();
			GameLauncher.resetMenu();
		}
	}

	private void setCamera(Graphics2D g) {
		synchronized (transferPlayer) {
			camera.setA(player != null ? player.getCenter() : null);
		}
		camera.update();
		Point camPos = camera.getPos();
		g.translate(camPos.x, camPos.y);
	}

	@Override
	public void start() {
		myThread.start();
	}

	/*
	 * Verifie que le client est a jour par rapport au serveur
	 *
	 * @param socket : socket de communication client / serveur
	 *
	 * public void testVersion(Socket socket) { try { ObjectOutputStream sOut = new
	 * ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	 * sOut.writeObject(Main.getVersion()); sOut.flush(); sOut.reset(); } catch
	 * (IOException e) { e.printStackTrace(); } }
	 */
}
