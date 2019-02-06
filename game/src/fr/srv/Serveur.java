package fr.srv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import fr.application.Application;

public class Serveur implements Runnable {

	private ServerSocket serveur;
	private Thread threadServ;

	private Service first;

	private Application a;

	private Serveur() {
	}

	public Serveur(int port, int width, int height) {
		first = null;

		a = new Application(width, height);

		try {
			serveur = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		threadServ = new Thread(this);
	}

	public void close(Service s) {
		if (s != first)
			return;

		threadServ.interrupt();
		try {
			serveur.close();
		} catch (IOException e) {
		}
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
				System.err.println("Serveur ferme");
				//e.printStackTrace();
			}
			if (socket != null) {
				if (first == null)
					(first = new Service(this, socket, a)).start();
				else
					(new Service(socket, a)).start();
			}
		}
	}

	public void start() {
		threadServ.start();
	}
}
