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

	public AppliClient(String name, Socket socket) {
		this.transfer = new Object();
		this.transferPlayer = new Object();

		this.name = name;
		this.endApp = false;
		this.receiving = false;

		this.socket = socket;

		this.myThread = new Thread(this);
		this.listD = new ArrayList<>();
	}

	/**
	 * @param name   : Nom de la fenetre de jeu
	 * @param socket : Socket liee au serveur
	 */
	public AppliClient(String name, Socket socket, ObjectInputStream sIn, ObjectOutputStream sOut) {
		this(name, socket);

		this.sIn = sIn;
		this.sOut = sOut;
	}

	/**
	 * Change le scale suivant les touches du clavier pressees
	 */
	private double changeScale() {

		KeyBoard keyBoard = KeyBoard.getInstance();

		Scale scale = Scale.getInstance();

		if (keyBoard.isPressed(KeyEvent.VK_ADD))
			scale.increase();
		else if (keyBoard.isPressed(KeyEvent.VK_SUBTRACT))
			scale.decrease();
		this.changingScale = scale.update();
		if (this.changingScale != 0)
			this.camera.move(-this.changingScale * this.map.getHeight() * this.map.getTileSize() * 0.5,
					-this.changingScale * this.map.getHeight() * this.map.getTileSize() * 0.5);

		return scale.getScale();
	}

	/**
	 * Met fin a l'application client
	 */
	@Override
	public void close() {
		this.myThread.interrupt();
		this.endApp = true;
		try {
			this.socket.close();
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
			if (this.socket != null)
				this.socket.close();
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
		if (this.endApp)
			throw new EndApp();

		if (!this.receiving)
			return;

		AffineTransform oldTransform = g.getTransform();

		// Application d'un eventuel changement d'echelle
		double scale = this.changeScale();

		this.setCamera(g);

		g.scale(scale, scale);

		// creation d'un cache
		List<Drawable> ld;

		// initialisation du cache
		synchronized (this.transfer) {
			ld = this.listD;
		}

		// Affichage de la map
		MapTile[][] mt = this.map.getMap();
		if (mt != null)
			for (MapTile[] mapTiles : mt)
				for (MapTile mapTile : mapTiles)
					mapTile.draw(g);

		// Affichage des elements
		for (Drawable drawable : ld)
			drawable.draw(g);

		g.setTransform(oldTransform);

		if (this.player != null) {
			this.hud.setCharacter(this.player);
			this.hud.draw(g);
		}
	}

	@Override
	public Color getBackgroundColor() {
		return new Color(60, 60, 60);
	}

	@Override
	public String getName() {
		return this.name;
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
			
			int nbPlayers = 4;
			ArrayList<Integer> results;
			
			// Tant que l'application n'est pas terminee
			while (!Thread.currentThread().isInterrupted()) {

				// Recuperation de la camera
				ICharacter tmpPlayer = (ICharacter) this.sIn.readUnshared();
				synchronized (this.transferPlayer) {
					this.player = tmpPlayer;
				}

				Object[] envoiClient = (Object[]) this.sIn.readUnshared();
				// Recuperation de la map
				this.map = (GameMap) envoiClient[0];
				// Recuperation des elements
				listDrawables = (List<Drawable>) envoiClient[1];

				// Elements dans le cache
				synchronized (this.transfer) {
					this.listD = new ArrayList<>(listDrawables);
				}
				this.sOut.writeUnshared(keyBoard.getKeys());
				this.sOut.flush();
				this.sOut.reset();
				
				nbPlayers = (int) this.sIn.readUnshared();
				if(nbPlayers <= 1) {
					results = (ArrayList<Integer>) this.sIn.readUnshared();
					
				}

				if (!this.receiving) {
					this.camera = new Camera(new Point(Client.getWidth(), Client.getHeight()),
							new Point(this.map.getWidth() * this.map.getTileSize(),
									this.map.getHeight() * this.map.getTileSize()),
							this.player.getCenter());
					this.hud = HUD.getInstance();
					this.receiving = true;
				}
			}
			this.sOut.close();
			this.sIn.close();
		} catch (IOException e) {
			System.err.println("Communication avec le serveur terminee");
			// e.printStackTrace();
			this.close();
			GameLauncher.resetMenu();
		} catch (ClassNotFoundException e1) {
			System.err.println("Erreur de Reception depuis le serveur");
			this.close();
			GameLauncher.resetMenu();
		}
	}

	private void setCamera(Graphics2D g) {
		synchronized (this.transferPlayer) {
			this.camera.setA(this.player != null ? this.player.getCenter() : null);
		}
		this.camera.update();
		Point camPos = this.camera.getPos();
		g.translate(camPos.x, camPos.y);
	}

	@Override
	public void start() {
		this.myThread.start();
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
