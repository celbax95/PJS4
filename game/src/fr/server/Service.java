package fr.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
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

	public static void setApplication(Application app) {
		Service.application = app;
	}

	private Socket socket;

	private Thread myThread;

	private Server server;

	private int myPlayer;

	/**
	 * constructeur Service vide
	 */
	private Service() {
	}

	/**
	 * constructeur Service
	 *
	 * @param s
	 *            : le serveur
	 * @param serverSocket
	 *            : la socket du serveur
	 */

	public Service(Server s, Socket serverSocket) {
		this(serverSocket);
		this.server = s;
	}

	/**
	 * constructeur Service
	 *
	 * @param serverSocket
	 *            : la socket du serveur
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

			while (!server.getGameOn()) {
				if (Thread.currentThread().isInterrupted()) {
					return;
				}
			}

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println("Game start");
			synchronized (server) {
				while (server.getApplication() == null) {
					this.wait();
				}
				application = server.getApplication();
				myPlayer = application.addPlayer();
			}

			ObjectOutputStream sOut = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			ObjectInputStream sIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			while (!Thread.currentThread().isInterrupted()) {
				sOut.writeUnshared(application.getMap());
				sOut.flush();
				sOut.reset();
				sOut.writeUnshared(application.getDrawables());
				sOut.flush();
				sOut.reset();
				List<Integer> cliKeys = (List<Integer>) sIn.readUnshared();
				if (!application.managePlayer(myPlayer, cliKeys)) {
					// Quand le joueur est mort
				}
			}
		} catch (IOException e) {
			System.err.println("Joueur déconnecté");
			server.playerLeft();
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
}
