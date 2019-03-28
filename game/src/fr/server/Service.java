package fr.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import fr.application.Application;

/**
 * Le Service du jeu qui communique avec le client via le serveur
 */
public class Service implements Runnable {

	private Socket socket;

	private Thread myThread;

	private Server server;

	private Application application;

	private int myPlayer;

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
	 * @param application  : l'application
	 */
	public Service(Server s, Socket serverSocket, Application application) {
		this.server = s;
		this.socket = serverSocket;
		this.application = application;
		myPlayer = application.addPlayer();
		myThread = new Thread(this);
	}

	/**
	 * ferme la socket
	 */
	@Override
	public void finalize() throws IOException {
		server.close(this);
	}

	public Socket getSocket() {
		return socket;
	}

	/**
	 * envoie les données de l'application au client
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public void run() {
		try {
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
		} catch (Exception e) {
			// e.printStackTrace();
			server.close(this);
			myThread.interrupt();
			application.deletePlayer(myPlayer);
			System.err.println("Service termine");
		}
	}

	/**
	 * lance le thread du service
	 */
	public void start() {
		myThread.start();
	}
}
