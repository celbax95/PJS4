package fr.screen.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;
/**
 * Clavier
 */
public class KeyBoard implements KeyListener {

	private static List<Integer> keys = new LinkedList<Integer>();
	private static boolean released = true;
	/**
	 * Gere l'acton a realiser lorsqu'une touche est pressee
	 * @param e : evennement de touche
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (!keys.contains(e.getKeyCode()))
			keys.add(e.getKeyCode());
	}
	/**
	 * Gere l'acton a realiser lorsqu'une touche est relachee
	 * @param e : evennement de touche
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		keys.remove((Object) e.getKeyCode());
	}
	/**
	 * Gere l'acton a realiser lorsqu'une touche est touche
	 * @param e : evennement de touche
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}
	/**
	 * @param i : l'indice de la touche recherche
	 * @return la touche a l'indice i
	 */
	public static int getKey(int i) {
		return keys.get(i);
	}
	/**
	 * @return une nouvelle instance de la liste des touches
	 */
	public static List<Integer> getKeys() {
		return new LinkedList<>(keys);
	}
	/**
	 * @return true si une touche est pressee et false sinon
	 */
	public static boolean isPressed() {
		released = false;
		return !(keys.isEmpty());
	}
	/**
	 * @param key : la cle (identifiant) d'une touche
	 * @return true si une touche donnée est pressee et false sinon
	 */
	public static boolean isPressed(int key) {
		released = false;
		return keys.contains(key);
	}
	/**
	 * @return true si une touche est tappee et false sinon
	 */
	public static boolean tap() {
		if (!(keys.isEmpty()) && released)
			return true;
		else if ((keys.isEmpty()) && !released)
			released = true;
		return false;
	}
}
