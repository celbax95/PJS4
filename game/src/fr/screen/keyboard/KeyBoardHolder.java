package fr.screen.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardHolder implements KeyListener {

	private static KeyBoardManager kb;

	static {
		kb = KeyBoardManager.getInstance();
	}

	public KeyBoardHolder() {
	}

	/**
	 * Gere l'acton a realiser lorsqu'une touche est pressee
	 *
	 * @param e : evennement de touche
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		kb.addKeyPressed(e.getKeyCode());
	}

	/**
	 * Gere l'acton a realiser lorsqu'une touche est relachee
	 *
	 * @param e : evennement de touche
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		kb.removeKeyPressed(e.getKeyCode());
	}

	/**
	 * Gere l'acton a realiser lorsqu'une touche est touche
	 *
	 * @param e : evennement de touche
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

}
