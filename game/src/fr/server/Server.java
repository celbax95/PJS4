package fr.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import fr.application.Application;

public class Server implements Runnable {

	private ServerSocket serveur;
	private Thread threadServ;

	private Service first;

	private Application application;

	@SuppressWarnings("unused")
	private Server() {
	}

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

	public void close(Service service) {
		if (service != first)
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
				// e.printStackTrace();
			}
			if (socket != null) {
				if (first == null)
					(first = new Service(this, socket, application)).start();
				else
					(new Service(socket, application)).start();
			}
		}
	}

	public void start() {
		threadServ.start();
	}
}
