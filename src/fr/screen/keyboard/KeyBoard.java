package fr.screen.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

public class KeyBoard implements KeyListener {

	private static List<Integer> keys = new LinkedList<Integer>();
	private static boolean released = true;

	@Override
	public void keyPressed(KeyEvent e) {
		if (!keys.contains(e.getKeyCode()))
			keys.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys.remove((Object) e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public static int getKey(int i) {
		return keys.get(i);
	}

	public static List<Integer> getKeys() {
		return keys;
	}

	public static boolean isPressed() {
		released = false;
		return !(keys.isEmpty());
	}

	public static boolean isPressed(int key) {
		released = false;
		return keys.contains(key);
	}

	public static boolean tap() {
		if (!(keys.isEmpty()) && released)
			return true;
		else if ((keys.isEmpty()) && !released)
			released = true;
		return false;
	}
}
