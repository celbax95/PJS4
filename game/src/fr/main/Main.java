package fr.main;

import fr.gameLauncher.GameLauncher;
import fr.gameLauncher.MenuNotSet;

/**
 * Entrée de l'application
 */
public class Main {

	public static void main(String[] args) {

		try {

			Class.forName("fr.menu.MenuDisplay");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {

			GameLauncher.menuDisplay();

		} catch (MenuNotSet e) {

			e.printStackTrace();
		}
	}
}
