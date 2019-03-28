package fr.main;


import fr.gameLauncher.GameLauncher;

/**
 * Entr�e de l'application
 */
public class Main {
	
	public static void main(String[] args) {
		
		try {
			
			Class.forName("fr.menu.MenuDisplay");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		GameLauncher.menuDisplay();
	}
}
