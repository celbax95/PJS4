package fr.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import fr.appCli.AppliClient;
import fr.application.Application;
import fr.screen.AppliScreen;
import fr.screen.Screen;

/**
 * Serveur
 */
public class Server implements Runnable {
	public static final int WIDTH = 1728;
	public static final int HEIGHT = 972;
	private static final int MARGE_H = 35;
	private static final int MARGE_T = 2;
	private static final int MARGE_W = 6;
	private String title;

	private final int NB_PLAYERS;

	private int nbPlayers;

	private boolean gameOn;
	private ServerSocket serveur;

	private Thread threadServ;
	private Socket socketHost;
	private Application application;

	/**
	 * constructeur Server
	 *
	 * @param title
	 *            : Nom de fenetre de l'application
	 * @param port
	 *            : port de connexion avec les clients
	 * @param nbPlayers
	 *            : nombre de joueurs acceptables par le serveur
	 */
	public Server(String title, int port, int nbPlayers) {

		try {
			serveur = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		threadServ = new Thread(this);
		this.title = title;
		this.NB_PLAYERS = nbPlayers;
		this.nbPlayers = 0;
		this.gameOn = false;
	}

	/**
	 * ferme la socket du serveur
	 */
	public void close() {
		try {
			socketHost.close();
			threadServ.interrupt();
			if (this.application != null) {
				this.application.stop();
			}

			serveur.close();
		} catch (IOException e) {
		}
	}

	/**
	 * ferme la socket du serveur
	 */
	@Override
	public void finalize() throws IOException {
		this.close();
	}

	protected Application getApplication() {
		return application;
	}

	public boolean getGameOn() {
		return gameOn;
	}

	/*
	 * Permet de verifier si le serveur est de la meme version que le service En cas
	 * de changements majeurs sur le serveur ou sur le service
	 *
	 * @param socket : une socket
	 *
	 * @return true si la version est valide et false sinon
	 *
	 * public boolean testVersion(Socket socket) { try { ObjectInputStream sIn = new
	 * ObjectInputStream(new BufferedInputStream(socket.getInputStream())); String
	 * version = (String) sIn.readObject(); if (!version.equals(Main.getVersion()))
	 * { socket.close(); return false; }
	 *
	 * } catch (IOException | ClassNotFoundException e) { e.printStackTrace(); } }
	 */

	public int getNbPlayers() {
		return this.nbPlayers;
	}

	protected void playerLeft() {
		this.nbPlayers--;
	}

	/**
	 * Accepte les clients et lance le jeu
	 */
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			if (nbPlayers < NB_PLAYERS) {
				Socket socket = null;

				try {
					socket = serveur.accept();
					if (this.socketHost == null) {
						this.socketHost = socket;
					}
					this.nbPlayers++;
					System.out.println("Connecte !");
				} catch (IOException e) {
					System.err.println("Serveur ferme");
					break;
				}
				if (socket != null) {
					(new Service(this, socket)).start();
				}
			}
		}
	}

	public void setGameOn() {
		synchronized (this) {
			this.threadServ.interrupt();
			this.gameOn = true;
			(this.application = new Application(WIDTH, HEIGHT)).start();
			AppliScreen appScr = new AppliClient(title, socketHost);
			Service.setApplication(application);
			notifyAll();
			Screen.getInstance(appScr, WIDTH, HEIGHT, MARGE_W, MARGE_H, MARGE_T);
		}
	}

	/**
	 * lance le Thread du serveur et appel la method run du serveur
	 */
	public void start() {
		threadServ.start();
	}
}
