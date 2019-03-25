package fr.server;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Random;

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
	 * @param s : le serveur
	 * @param serverSocket : la socket du serveur
	 * @param application : l'application
	 */
	public Service(Server s, Socket serverSocket, Application application) {
		this(serverSocket, application);
		this.server = s;
	}
	/**
	 * constructeur Service
	 * @param serverSocket : la socket du serveur
	 * @param application : l'application
	 */
	public Service(Socket serverSocket, Application application) {
		server = null;
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
		socket.close();
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
				application.managePlayer(myPlayer, cliKeys);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
			}
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
