package fr.gameLauncher;

import java.util.ArrayList;

import fr.server.Player;

public interface Menu {
	public void display();

	public void hideWindow();

	public void reset();

	public void updatePlayers(ArrayList<Player> players);

	public void back();

	public void giveResults(ArrayList<Integer> results);
}
