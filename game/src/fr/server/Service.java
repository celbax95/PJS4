package fr.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import fr.application.Application;

/**
 * Le Service du jeu qui communique avec le client via le serveur
 */
public class Service implements Runnable {

	private static Application application;

	private Socket socket;

	private Thread myThread;

	private Server server;

	private Player myPlayer;

	/**
	 * constructeur Service vide
	 */
	private Service() {
	}

	/**
	 * constructeur Service
	 *
	 * @param s            : le serveur
	 * @param serverSocket : la socket du serveur
	 */

	public Service(Server s, Socket serverSocket) {
		this(serverSocket);
		this.server = s;
	}

	/**
	 * constructeur Service
	 *
	 * @param serverSocket : la socket du serveur
	 */
	public Service(Socket serverSocket) {
		this.server = null;
		this.socket = serverSocket;
		myThread = new Thread(this);
	}

	/**
	 * ferme la socket
	 */
	@Override
	public void finalize() throws IOException {
		this.myThread.interrupt();
		this.socket.close();
	}

	public Socket getSocket() {
		return socket;
	}

	/**
	 * envoie les donnees de l'application au client
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public void run() {
		try {
			ObjectInputStream sIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			synchronized(this.server) {
				this.myPlayer = new Player(this.server.getNoPlayerAvailable(),(String)sIn.readUnshared());
				this.server.addPlayer(this.myPlayer);
			}
			
			
			ObjectOutputStream sOut = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			while (!server.getGameOn()) {
				if (Thread.currentThread().isInterrupted()) {
					return;
				}
				sOut.writeUnshared("ok");
				sOut.flush();
				sOut.reset();
				sOut.writeUnshared(server.getPlayers());
				sOut.flush();
				sOut.reset();
				
			}
			sOut.writeUnshared("Game start");
			sOut.flush();
			sOut.reset();
			synchronized (server) {
				while (server.getApplication() == null) {
					this.wait();
				}
				application = server.getApplication();
				application.addPlayer(this.myPlayer.getNo());
			}
			
			while (!Thread.currentThread().isInterrupted()) {
				sOut.writeUnshared(application.getPlayer(myPlayer));
				sOut.writeUnshared(application.getMap());
				sOut.writeUnshared(application.getDrawables());
				sOut.flush();
				sOut.reset();
				List<Integer> cliKeys = (List<Integer>) sIn.readUnshared();
				if (!application.managePlayer(myPlayer.getNo(), cliKeys)) {
					// Quand le joueur est mort
				}
			}
		} catch (IOException e) {
			System.err.println("Joueur déconnecté");
			e.printStackTrace();
			synchronized(server) {
				server.playerLeft(this.myPlayer);
			}
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			myThread.interrupt();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * lance le thread du service
	 */
	public void start() {
		myThread.start();
	}

	public static void setApplication(Application app) {
		Service.application = app;
	}
}
