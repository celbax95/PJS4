package fr.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import fr.appCli.AppliClient;
import fr.gameLauncher.GameLauncher;
import fr.gameLauncher.Menu;
import fr.screen.AppliScreen;
import fr.screen.Screen;
import fr.server.Player;

public class Client implements Runnable {

	private static final String NAME = "TEST PJS4";
	private static final int MARGE_H = 35;
	private static final int MARGE_T = 2;

	private static final int MARGE_W = 6;

	private static final int WIDTH = 1728;
	private static final int HEIGHT = 972;
	private Socket socket;

	private Thread myThread;
	private Menu menu;

	private String alias;

	public Client(String ip, int port, String alias, Menu menu) throws IOException {
		if(ip.equals("Ip Adress")) {
			socket = connexion("localhost", port);
		}
		else {
			socket = connexion(ip, port);
		}
		if (socket.isClosed()) {
			throw new IOException();
		}
		this.menu = menu;
		myThread = new Thread(this);
		if(!alias.equals("Alias") && !alias.equals("")) {
			this.alias = alias;
		}
		else {
			this.alias = "Player";
		}

		this.start();
	}

	public void close() {
		myThread.interrupt();
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

	private Socket connexion(String ip, int port) throws IOException {
		return new Socket(ip, port);
	}

	public Socket getSocket() {
		return this.socket;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			ObjectOutputStream sOut = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			sOut.writeUnshared(this.alias);
			sOut.flush();
			sOut.reset();

			ObjectInputStream sIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			String response;
			while (true) {
				if (Thread.currentThread().isInterrupted()) {
					return;
				}
				response = String.valueOf(sIn.readUnshared());
				//System.out.println(response);
				if (response.equals("Game start")) {
					break;
				}
				synchronized(this) {
					if (!Thread.currentThread().isInterrupted()) {
						GameLauncher.updateMenu((ArrayList<Player>) sIn.readUnshared());
					}
				}
			}
			menu.hideWindow();

			AppliScreen appScr = new AppliClient(NAME, socket, sIn, sOut);
			Screen.getInstance(appScr, WIDTH, HEIGHT, MARGE_W, MARGE_H, MARGE_T);

		} catch (IOException e) {
			GameLauncher.updateMenu(new ArrayList<Player>());
			GameLauncher.menuBack();
			close();
			System.err.println("Client ferme");
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void start() {
		myThread.start();
	}
	
	public static int getWidth() {
		return WIDTH;
	}
	public static int getHeight() {
		return HEIGHT;
	}

}
