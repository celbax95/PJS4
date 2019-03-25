package fr.screen.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Gestion du clavier
 */
public class KeyBoard implements KeyListener {

	private static List<Integer> keys = new LinkedList<Integer>();
	private static boolean released = true;

	/**
	 * Ajout de la touche pressee dans la liste de touches pressees
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (!keys.contains(e.getKeyCode()))
			keys.add(e.getKeyCode());
	}

	/**
	 * Supression de la touche pressee dans la liste de touches pressees
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		keys.remove((Object) e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * @param i : indice de la touche a recuperer dans la liste de touches pressees
	 * @return : la touche a l'indice i
	 */
	public static int getKey(int i) {
		return keys.get(i);
	}

	/**
	 * @return le liste de toutes les touches pressees
	 */
	public static List<Integer> getKeys() {
		return new LinkedList<>(keys);
	}

	/**
	 * @return une touche est pressee
	 */
	public static boolean isPressed() {
		released = false;
		return !(keys.isEmpty());
	}

	/**
	 * @param key : un touche
	 * @return la touche key est pressee
	 */
	public static boolean isPressed(int key) {
		released = false;
		return keys.contains(key);
	}

	/**
	 * Tapotage du clavier
	 * 
	 * @return la clavier a ete tapote
	 */
	public static boolean tap() {
		if (!(keys.isEmpty()) && released)
			return true;
		else if ((keys.isEmpty()) && !released)
			released = true;
		return false;
	}
}
