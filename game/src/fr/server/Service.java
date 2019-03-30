package fr.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import fr.application.Application;
import fr.itemsApp.Drawable;

/**
 * Le Service du jeu qui communique avec le client via le serveur
 */
public class Service implements Runnable {

	private static Application application;

	private Socket socket;

	private Thread myThread;

	private Server server;

	private Player myPlayer;
	
	ObjectInputStream sIn;
	
	ObjectOutputStream sOut;
	
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
		try {
			this.sIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			this.sOut = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			synchronized (this.server) {
				this.myPlayer = new Player(this.server.getNoPlayerAvailable(), (String) sIn.readUnshared());
				this.server.addPlayer(this.myPlayer);
			}

			
			while (!server.getGameOn()) {
				if (Thread.currentThread().isInterrupted()) {
					return;
				}
				sOut.writeUnshared("ok");
				sOut.flush();
				sOut.reset();
				synchronized(this.server) {
					sOut.writeUnshared(server.getPlayers());
				}
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
				application.addPlayer(this.myPlayer.getNo());
			}
			//int i = 0;
			while (!Thread.currentThread().isInterrupted()) {
				synchronized(application) {
					sOut.writeUnshared(application.getPlayer(myPlayer));
					sOut.flush();
				}
				sOut.reset();
				sOut.writeUnshared(application.getMap());
				synchronized(application) {
					sOut.writeUnshared(new Object[]{application.getMap(), application.getDrawables()});
					sOut.flush();
				}
				sOut.flush();
				sOut.reset();
				
				System.out.println("no problem 1 "+myPlayer.getAlias());
				List<Integer> cliKeys = (List<Integer>) sIn.readUnshared();
				System.out.println("no problem 2 "+myPlayer.getAlias());
				synchronized(application) {
					if (!application.managePlayer(myPlayer.getNo(), cliKeys)) {
						// Quand le joueur est mort
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Joueur déconnecté");
			e.printStackTrace();
			synchronized (server) {
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
	
	public ObjectInputStream getSIn(){
		return this.sIn;
	}
	
	public ObjectOutputStream getSOut() {
		return this.sOut;
	}
}
