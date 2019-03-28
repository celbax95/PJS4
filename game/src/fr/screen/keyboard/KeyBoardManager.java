package fr.screen.keyboard;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Singleton Clavier
 */
public class KeyBoardManager implements KeyBoard {

	private static KeyBoardManager instance;

	static {
		instance = new KeyBoardManager();
	}

	/**
	 * @return l'unique instance de KeyBoardManager
	 */
	protected static KeyBoardManager getInstance() {
		return instance;
	}

	private List<Integer> keys = new Vector<Integer>();

	private boolean released = true;

	/**
	 * Ajoute une touche a la liste des touches pressees
	 *
	 * @param key
	 *            : la touche a ajouter
	 */
	public void addKeyPressed(int key) {
		if (!keys.contains(key))
			keys.add(key);
	}

	@Override
	public int getKey(int i) {
		return keys.get(i);
	}

	@Override
	public List<Integer> getKeys() {
		return new LinkedList<>(keys);
	}

	@Override
	public boolean isPressed() {
		released = false;
		return !(keys.isEmpty());
	}

	@Override
	public boolean isPressed(int key) {
		released = false;
		return keys.contains(key);
	}

	/**
	 * Test si le clavier n'a aucune touche pressee
	 *
	 * @return le clavier n'a aucun touche pressee
	 */
	public boolean isReleased() {
		return released;
	}

	/**
	 * Enleve un touche de la liste de touche pressee
	 *
	 * @param key
	 *            : la touche a enlever
	 */
	public void removeKeyPressed(int key) {
		keys.remove((Object) key);
	}

	/**
	 * Indique qu'aucune touche n'est pressee
	 *
	 * @param released
	 */
	public void setReleased(boolean released) {
		this.released = released;
	}

	@Override
	public boolean tap() {
		if (!(keys.isEmpty()) && released)
			return true;
		else if ((keys.isEmpty()) && !released)
			released = true;
		return false;
	}
}