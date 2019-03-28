package fr.appCli;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.sun.glass.events.KeyEvent;

import fr.Camera.Camera;
import fr.client.Client;
import fr.gameLauncher.GameLauncher;
import fr.itemsApp.Drawable;
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

	private Camera camera;
	private Object transferAim;
	private Point aim;

	private boolean receiving;

	/**
	 * @param name   : Nom de la fenetre de jeu
	 * @param socket : Socket liee au serveur
	 */
	public AppliClient(String name, Socket socket) {
		this.transfer = new Object();
		this.transferAim = new Object();

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
	 */
	private void changeScale() {

		KeyBoard keyBoard = KeyBoard.getInstance();

		Scale scale = Scale.getInstance();

		if (keyBoard.isPressed(KeyEvent.VK_ADD))
			scale.increase();
		else if (keyBoard.isPressed(KeyEvent.VK_SUBTRACT))
			scale.decrease();
		scale.update();
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

		// Application d'un eventuel changement d'echelle
		changeScale();

		setCamera(g);

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

			// Input et Output de la socket
			ObjectOutputStream sOut = new ObjectOutputStream((socket.getOutputStream()));
			ObjectInputStream sIn = new ObjectInputStream((socket.getInputStream()));

			// Tant que l'application n'est pas terminee
			while (!Thread.currentThread().isInterrupted()) {

				// Recuperation de la camera
				synchronized (transferAim) {
					aim = (Point) sIn.readUnshared();
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
							new Point(map.getWidth() * map.getTileSize(), map.getHeight() * map.getTileSize()), aim);
					receiving = true;
				}
			}
		} catch (IOException e) {
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
		synchronized (transferAim) {
			camera.setA(aim);
		}
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
