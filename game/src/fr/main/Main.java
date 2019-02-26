package fr.main;

import fr.menu.MenuDisplay;

/**
 * Entrée de l'application
 */
public class Main {
	
	private static final String NAME = "TEST PJS4";

	public static void main(String[] args) {
		
		MenuDisplay m = MenuDisplay.getInstance(NAME, 500);
		m.display();
	}
}
