package fr.appCli;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fr.drawables.Drawable;
import fr.screen.AppliScreen;
import fr.screen.EndApp;
import fr.screen.keyboard.KeyBoard;

public class AppliClient implements AppliScreen, Runnable {

	private List<Drawable> listD;

	private String name;
	private boolean endApp;

	private Object transfer;
	private Socket socket;
	private Thread myThread;

	public AppliClient(String name, String ip, int port) {
		this.transfer = new Object();

		this.name = name;
		this.endApp = false;

		socket = connexion(ip, port);
		myThread = new Thread(this);

		listD = new ArrayList<>();
	}

	@Override
	public void close() {
		myThread.interrupt();
		endApp = true;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeSocket() {
		try {
			System.out.println("FIN");
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Socket connexion(String ip, int port) {
		try {
			return new Socket(ip, port);
		} catch (IOException e) {
			System.err.println("Impossible de se connecter au serveur");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void draw(Graphics2D g) throws EndApp {
		if (endApp)
			throw new EndApp();
		List<Drawable> ld;
		synchronized (transfer) {
			ld = listD;
		}
		if (listD != null) {
			for (Drawable drawable : ld) {
				drawable.draw(g);
			}
		}
	}

	@Override
	public Color getBackgroundColor() {
		return new Color(60, 60, 60);
	}

	@Override
	public String getName() {
		return name;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			List<Drawable> ld;
			ObjectOutputStream sOut = new ObjectOutputStream((socket.getOutputStream()));
			ObjectInputStream sIn = new ObjectInputStream((socket.getInputStream()));
			while (!Thread.currentThread().isInterrupted()) {
				ld = (List<Drawable>) sIn.readUnshared();
				synchronized (transfer) {
					listD = new ArrayList<>(ld);
				}
				sOut.writeUnshared(KeyBoard.getKeys());
				sOut.flush();
				sOut.reset();
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
				}
			}
		} catch (IOException e) {
			// e.printStackTrace();
			System.err.println("Communication avec le serveur terminee");
			close();
		} catch (ClassNotFoundException e1) {
			// e1.printStackTrace();
			System.err.println("Erreur de Reception depuis le serveur");
			close();
		}
	}

	@Override
	public void start() {
		myThread.start();
	}
}
