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

	private Socket socket;
	private Thread myThread;
	private Server server;

	private static Application application;

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
		server = null;
		this.socket = serverSocket;
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
	 * envoie les donnees de l'application au client
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public void run() {
		try {
			

			while (!server.getGameOn()) {
				if(Thread.currentThread().isInterrupted()) {
					return;
				}
			}
			
			
			PrintWriter out = new PrintWriter (socket.getOutputStream ( ), true);
			out.println("Game start");
			while(application == null) { }
			myPlayer = application.addPlayer();
			ObjectOutputStream sOut = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			ObjectInputStream sIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			while(!Thread.currentThread().isInterrupted()) {
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
			//e.printStackTrace();
			System.err.println("Joueur déconnecté");
			server.playerLeft();
			try {
				socket.close();
			} catch (IOException e1) {}
			myThread.interrupt();
			//application.deletePlayer(myPlayer);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
