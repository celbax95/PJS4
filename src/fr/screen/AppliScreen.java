package fr.screen;

import java.awt.Color;
import java.awt.Graphics2D;

public interface AppliScreen {

	void close();

	void draw(Graphics2D g) throws EndApp;

	Color getBackgroundColor();

	String getName();

	void start();
}
