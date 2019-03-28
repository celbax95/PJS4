package fr.gameLauncher;


/**
 * Exception lanc�e lorsque qu'aucune classe de menu n'a �t� charg�e et instanci�e dans GameLauncher
 */
public class MenuNotSet extends Exception {

	
	private static final long serialVersionUID = 1L;
	
	public MenuNotSet() {
		super("Menu non charg�.");
	}
}
