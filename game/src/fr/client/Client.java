package fr.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import fr.appCli.AppliClient;
import fr.gameLauncher.Menu;
import fr.screen.AppliScreen;
import fr.screen.Screen;

public class Client implements Runnable {

	private static final String NAME = "TEST PJS4";
	private static final int MARGE_H = 35;
	private static final int MARGE_T = 2;

	private static final int MARGE_W = 6;

	public static final int WIDTH = 1728;
	public static final int HEIGHT = 972;
	private Socket socket;

	private Thread myThread;
	private Menu menu;

	public Client(String ip, int port, Menu menu) throws IOException {

		socket = connexion(ip, port);

		this.menu = menu;
		myThread = new Thread(this);
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
		try {
			return new Socket(ip, port);
		} catch (IOException e) {
			throw e;
		}
	}

	public Socket getSocket() {
		return this.socket;
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response;
			while (true) {
				response = in.readLine();
				System.out.println(response);
				if (response.equals("Game start")) {
					break;
				}
				if (Thread.currentThread().isInterrupted()) {
					return;
				}
			}
			menu.hideWindow();

			AppliScreen appScr = new AppliClient(NAME, socket);
			Screen.getInstance(appScr, WIDTH, HEIGHT, MARGE_W, MARGE_H, MARGE_T);

		} catch (IOException e) {
			System.err.println("Client ferme");
		}

	}

	public void start() {
		myThread.start();
	}

}
