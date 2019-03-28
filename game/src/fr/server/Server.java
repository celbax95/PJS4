package fr.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import fr.application.Application;
import fr.main.Main;

/**
 * Serveur
 */
public class Server implements Runnable {

	private ServerSocket serveur;
	private Thread threadServ;

	private Service first;

	private Application application;

	/**
	 * constructeur Server vide
	 */
	private Server() {
	}

	/**
	 * constructeur Server
	 *
	 * @param port   : port de connexion avec les clients
	 * @param width  : Largeur de la fenetre
	 * @param height : Hauteur de la fenetre
	 */
	public Server(int port, int width, int height) {
		first = null;

		(application = new Application(width, height)).start();

		try {
			serveur = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		threadServ = new Thread(this);
	}

	/**
	 * ferme la socket du serveur
	 *
	 * @param service : un service
	 */
	public void close(Service service) {
		try {
			service.getSocket().close();
			if (service != first) {
				threadServ.interrupt();
				serveur.close();
			}
		} catch (IOException e) {
		}
	}

	/**
	 * ferme la socket du serveur
	 *
	 * @param service : un service
	 */
	@Override
	public void finalize() throws IOException {
		serveur.close();
	}

	/**
	 * Accepte les clients et lance le jeu
	 */
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {

			Socket socket = null;

			try {
				socket = serveur.accept();
				System.out.println("Connecte !");
			} catch (IOException e) {
				System.err.println("Serveur ferme");
				// e.printStackTrace();
			}
			if (socket != null) {
				if (testVersion(socket)) {
					if (first == null)
						(first = new Service(this, socket, application)).start();
					else
						(new Service(this, socket, application)).start();
				}
			}
		}
	}

	/**
	 * lance le Thread du serveur et appel la method run du serveur
	 */
	public void start() {
		threadServ.start();
	}

	/**
	 * Permet de verifier si le serveur est de la meme version que le service En cas
	 * de changements majeurs sur le serveur ou sur le service
	 *
	 * @param socket : une socket
	 * @return true si la version est valide et false sinon
	 */
	public boolean testVersion(Socket socket) {
		try {
			ObjectInputStream sIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			String version = (String) sIn.readObject();
			if (!version.equals(Main.getVersion())) {
				socket.close();
				return false;
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}
}
