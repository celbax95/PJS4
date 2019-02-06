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

public class Service implements Runnable {

	private Socket socket;

	private Thread myThread;

	private Server server;

	private Application application;

	private int myPlayer;

	private Service() {
	}

	public Service(Server s, Socket serverSocket, Application application) {
		this(serverSocket, application);
		this.server = s;
	}

	public Service(Socket serverSocket, Application application) {
		server = null;
		this.socket = serverSocket;
		this.application = application;
		myPlayer = application.addPlayer(randomColor());
		myThread = new Thread(this);
	}

	@Override
	public void finalize() throws IOException {
		socket.close();
	}

	public Color randomColor() {
		Random r = new Random();
		return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void run() {
		try {
			ObjectOutputStream sOut = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			ObjectInputStream sIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

			while (!Thread.currentThread().isInterrupted()) {
				sOut.writeUnshared(application.getDrawables());
				sOut.flush();
				sOut.reset();
				List<Integer> cliKeys = (List<Integer>) sIn.readUnshared();
				application.managePlayer(myPlayer, cliKeys);
			}
		} catch (IOException | ClassNotFoundException e) {
			// e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
			}
			myThread.interrupt();
			System.err.println("Service termine");
		}
	}

	public void start() {
		myThread.start();
	}
}
