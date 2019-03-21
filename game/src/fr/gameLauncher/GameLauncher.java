package fr.gameLauncher;

import java.io.IOException;

import fr.client.Client;
import fr.menu.MenuDisplay;
import fr.server.Server;

public class GameLauncher {
	private static Server server;
	private static Client cli;
	private static Menu menu;
	
	public static void menuDisplay() {
		menu.display();
	}
	
	public static void createServer(String title,int port, int nbPlayers) {
		server = new Server(title, port, nbPlayers);
	}
	public static void createClient(String ip, int port, MenuDisplay menu) {
		cli = new Client(ip, port, menu);
	}
	
	public static void serverStart() {
		server.start();
	}
	
	public static void serverClose() {
		try {
			server.finalize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void clientClose() {
		cli.close();
	}
	
	public static boolean isSocketClientNull() {
		return cli.getSocket()==null;
	}
	
	public static int getServerNbPlayers() {
		return server.getNbPlayers();
	}
	
	public static void serverSetGameOn() {
		server.setGameOn();
	}
	public static void isMenuSet() throws MenuNotSetException {
		if(menu==null) {
			throw new MenuNotSetException();
		}
	}
	public static void setMenu(Menu menu) {
		GameLauncher.menu = menu;
	}
}
