package fr.srv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur implements Runnable {

	private ServerSocket serveur;
	private Thread threadServ;

	public Serveur(int port) {
		try {
			serveur = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		threadServ = new Thread(this);
	}

	@Override
	public void finalize() throws IOException {
		serveur.close();
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {

			Socket socket = null;

			try {
				socket = serveur.accept();
				System.out.println("Connecte !");
			} catch (IOException e) {
				System.err.println("Probleme de connexion !");
				e.printStackTrace();
			}
			if (socket != null)
				(new Service(socket)).start();
		}
	}

	public void start() {
		threadServ.start();
	}
}
