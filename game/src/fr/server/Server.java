package fr.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import fr.application.Application;
import fr.gameLauncher.GameLauncher;

/**
 * Serveur
 */
public class Server implements Runnable {
	public static final int WIDTH = 1728;
	public static final int HEIGHT = 972;
	private static final int MARGE_H = 35;
	private static final int MARGE_T = 2;
	private static final int MARGE_W = 6;
	private final int PORT;
	private String title;

	private final int NB_PLAYERS;
	private int nbPlayers;
	private ArrayList<Player> players;

	private boolean gameOn;
	private ServerSocket server;
	private ArrayList<Service> services;
	private Service first;
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
			this.server = new ServerSocket(port);
			this.threadServ = new Thread(this);
			this.services = new ArrayList<Service>();
			this.title = title;
			this.nbPlayers = 0;
			this.gameOn = false;
			this.players = new ArrayList<Player>();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.PORT = port;
		this.NB_PLAYERS = nbPlayers;
	}

	public void addPlayer(Player p) {
		int i = 1;
		for (Player player : players) {
			if (i == 1 && player.getAlias().equals(p.getAlias())) {
				i++;
			}
			//System.out.println(player.getAlias() + " ? " +(p.getAlias()+" "+i));
			if(i > 1 && player.getAlias().equals(p.getAlias()+" "+i)) {
				i++;
			}
		}
		if (i != 1) {
			p.setAlias(p.getAlias() + " " + i);
		}
		players.add(p);
	}

	/**
	 * ferme la socket du serveur
	 */
	public void close() {
		try {
			for (Service s : this.services) {
				s.finalize();
			}
			socketHost.close();
			threadServ.interrupt();
			if (this.application != null) {
				this.application.stop();
			}
			server.close();
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

	public boolean getGameOn() {
		return gameOn;
	}

	public int getNbPlayers() {
		return this.nbPlayers;
	}

	public int getNoPlayerAvailable() {
		if (players.isEmpty()) {
			return 1;
		} else {
			return players.get(players.size() - 1).getNo() + 1;
		}
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	protected void playerLeft(Player player) {
		this.nbPlayers--;
		int noPlayerRemoved = player.getNo();
		if (noPlayerRemoved == this.players.size()) {
			this.players.remove(player);
		} else {
			this.players.remove(player);
			for (int i = noPlayerRemoved - 1; i < this.players.size(); i++) {
				this.players.get(i).setNo(this.players.get(i).getNo() - 1);
			}
		}
		if (this.nbPlayers == 0) {
			close();
		}
	}

	/**
	 * Accepte les clients et lance le jeu
	 */
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {

			Socket socket = null;

			try {
				socket = server.accept();
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
				Service s;
				s = new Service(this, socket);
				synchronized (this) {
					if (first == null) {
						first = s;
					}
				}
				synchronized (services) {
					services.add(s);
				}
				s.start();
			}

			// Cas de connexions quand le serveur est plein

			boolean resetServerSocket = false;
			while (nbPlayers >= NB_PLAYERS && !Thread.currentThread().isInterrupted()) {
				if (!resetServerSocket) {
					resetServerSocket = true;
					try {
						this.server.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (resetServerSocket) {
				try {
					this.server = new ServerSocket(PORT);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setGameOn() {
		synchronized (this) {
			this.threadServ.interrupt();
			this.gameOn = true;
			(this.application = new Application(WIDTH, HEIGHT)).start();
			synchronized (this.application) {
				Service.setApplication(application);
			}
			notifyAll();
		}
	}

	/**
	 * lance le Thread du serveur et appel la method run du serveur
	 */
	public void start() {
		threadServ.start();
	}
}
