package fr.gameLauncher;

import java.io.IOException;

import fr.client.Client;
import fr.screen.MainJPanel;
import fr.screen.Screen;
import fr.server.Server;

/**
 * Classe de lancement du jeu
 */
public class GameLauncher {
	private static Server server;
	private static Client cli;
	private static Menu menu;
	
	/**
	 * Affiche le menu
	 * @see Menu
	 * @exception Lance une exception si le menu n'a pas été initialisé
	 */
	public static void menuDisplay() throws MenuNotSet {
		isMenuSet();
		menu.display();
	}
	
	/**
	 * Crée un serveur
	 * @param title Le titre de la fenêtre gérée par le serveur
	 * @param port Port du serveur
	 * @param nbPlayer Nombres de joueurs supportés par le serveur
	 */
	public static void createServer(String title,int port, int nbPlayers) {
		server = new Server(title, port, nbPlayers);
	}
	/**
	 * Crée un client
	 * @param ip Adresse IP du serveur auquel veut se connecter le client
	 * @param port Numéro du port du serveur auquel veut se connecter le client
	 * @param menu Menu actuellement utilisé
	 * @throws IOException 
	 */
	public static void createClient(String ip, int port, Menu menu) throws IOException {
		cli = new Client(ip, port, menu);
	}
	/**
	 * Mise en marche du serveur
	 */
	public static void serverStart() {
		server.start();
	}
	/**
	 * Fermeture du serveur
	 */
	public static void serverClose() {
		try {
			server.finalize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Fermeture du client
	 */
	public static void clientClose() {
		cli.close();
	}
	
	public static int getServerNbPlayers() {
		return server.getNbPlayers();
	}
	
	/**
	 * Lance une partie
	 */
	public static void serverSetGameOn() {
		server.setGameOn();
	}
	/**
	 * Teste si le menu est instancié
	 * @exception Renvoie MenuNotSet si le menu n'est pas instancié
	 */
	public static void isMenuSet() throws MenuNotSet {
		if(menu==null) {
			throw new MenuNotSet();
		}
	}
	public static void setMenu(Menu menu) {
		GameLauncher.menu = menu;
	}
	/**
	 * Reset du menu
	 */
	public static void resetMenu() {
		clientClose();
		if(server!=null) {
			serverClose();
		}
		MainJPanel.setNewInstance();
		Screen.setNewInstance();
		menu.reset();
		
	}
}
