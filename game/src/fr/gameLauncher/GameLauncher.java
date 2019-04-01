package fr.gameLauncher;

import java.io.IOException;
import java.util.ArrayList;

import fr.client.Client;
import fr.screen.MainJPanel;
import fr.screen.Screen;
import fr.server.Player;
import fr.server.Server;
import fr.son.AudioPlayer;

/**
 * Classe de lancement du jeu
 */
public class GameLauncher {
	private static Server server;
	private static Client cli;
	private static Menu menu;
	static String Cmenu = ".\\BanqueSon\\menu.wav";

	static String Cjeu = ".\\BanqueSon\\Yeah2.wav";
	static Object Smenu = null;
	static Object Sjeu = null;

	/**
	 * Fermeture du client
	 */
	public static void clientClose() {
		cli.close();
	}

	/**
	 * Crée un client
	 *
	 * @param ip
	 *            Adresse IP du serveur auquel veut se connecter le client
	 * @param port
	 *            Numéro du port du serveur auquel veut se connecter le client
	 * @param menu
	 *            Menu actuellement utilisé
	 * @throws IOException
	 */
	public static void createClient(String ip, int port, String alias, Menu menu) throws IOException {
		cli = new Client(ip, port, alias, menu);
	}

	/**
	 * Crée un serveur
	 *
	 * @param title
	 *            Le titre de la fenêtre gérée par le serveur
	 * @param port
	 *            Port du serveur
	 * @param nbPlayer
	 *            Nombres de joueurs supportés par le serveur
	 */
	public static void createServer(String title, int port, int nbPlayers) {
		server = new Server(title, port, nbPlayers);
	}

	public static int getServerNbPlayers() {
		return server.getNbPlayers();
	}

	public static AudioPlayer initialiserSon(String chemin) {

		if (chemin.equals(Cmenu)) {
			try {
				AudioPlayer SoundMenu = new AudioPlayer(chemin);
				Smenu = SoundMenu;

			} catch (Exception ex) {
				System.out.println("Error with playing sound.");
				ex.printStackTrace();
			} finally {
				return (AudioPlayer) Smenu;
			}

		} else if (chemin.equals(Cjeu)) {
			try {
				AudioPlayer SoundMenu = new AudioPlayer(chemin);
				Sjeu = SoundMenu;

			} catch (Exception ex) {
				System.out.println("Error with playing sound.");
				ex.printStackTrace();
			} finally {
				return (AudioPlayer) Sjeu;
			}
		} else
			return null;
	}

	/**
	 * Teste si le menu est instancié
	 *
	 * @exception Renvoie
	 *                MenuNotSet si le menu n'est pas instancié
	 */
	public static void isMenuSet() throws MenuNotSet {
		if (menu == null) {
			throw new MenuNotSet();
		}
	}

	public static void lancerSon(AudioPlayer monSon) {
		monSon.playConst();
	}

	public static void menuBack() {
		menu.back();
	}

	/**
	 * Affiche le menu
	 *
	 * @see Menu
	 * @exception Lance
	 *                une exception si le menu n'a pas été initialisé
	 */
	public static void menuDisplay() throws MenuNotSet {

		initialiserSon(Cmenu);
		lancerSon((AudioPlayer) Smenu);
		isMenuSet();
		menu.display();
	}

	/**
	 * Reset du menu
	 */
	public static void resetMenu() {

		clientClose();
		if (server != null) {
			serverClose();
		}
		MainJPanel.setNewInstance();
		Screen.setNewInstance();
		menu.reset();
		// On met arette la musique du jeu pour repasser sur la musique du menu
		((AudioPlayer) Sjeu).pause();
		((AudioPlayer) Smenu).playConst();
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
	 * Lance une partie
	 */
	public static void serverSetGameOn() {
		((AudioPlayer) Smenu).pause();
		initialiserSon(Cjeu);
		lancerSon((AudioPlayer) Sjeu);
		server.setGameOn();

	}

	/**
	 * Mise en marche du serveur
	 */
	public static void serverStart() {
		server.start();
	}

	public static void setMenu(Menu menu) {
		GameLauncher.menu = menu;
	}

	public static void stoperSon(AudioPlayer monSon) {
		monSon.pause();
	}

	// >>>>>>> mise a jour integration son

	public static void updateMenu(ArrayList<Player> players) {
		menu.updatePlayers(players);
	}

}
