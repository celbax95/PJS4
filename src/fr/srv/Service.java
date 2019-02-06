package fr.srv;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Random;

import fr.application.Application;

public class Service implements Runnable {

	private Socket socket;

	private Thread thread;

	private Serveur s;

	private Application a;

	private int myPlayer;

	private Service() {
	}

	public Service(Serveur s, Socket serverSocket, Application a) {
		this(serverSocket, a);
		this.s = s;
	}

	public Service(Socket serverSocket, Application a) {
		s = null;
		this.socket = serverSocket;
		try {
			socket.setReceiveBufferSize(30000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.a = a;
		myPlayer = a.addPlayer(randomColor());
		thread = new Thread(this);
	}

	@Override
	public void finalize() throws IOException {
		socket.close();
	}

	private double msTime() {
		return (double) (System.nanoTime()) / 1000000;
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
				sOut.writeUnshared(a.getDrawables());
				sOut.flush();
				sOut.reset();
				List<Integer> keys = (List<Integer>) sIn.readUnshared();
				a.managePlayer(myPlayer, keys);
			}
		} catch (IOException | ClassNotFoundException e) {
			// e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
			}
			thread.interrupt();
			System.err.println("Service termine");
		}
	}

	public void start() {
		thread.start();
	}
}
