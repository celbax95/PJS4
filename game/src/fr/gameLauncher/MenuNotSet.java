package fr.gameLauncher;


/**
 * Exception lancée lorsque qu'aucune classe de menu n'a été chargée et instanciée dans GameLauncher
 */
public class MenuNotSet extends Exception {

	
	private static final long serialVersionUID = 1L;
	
	public MenuNotSet() {
		super("Menu non chargé.");
	}
}
