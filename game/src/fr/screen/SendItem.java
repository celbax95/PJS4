package fr.screen;

import java.util.List;

public class SendItem {
	private int id;
	private List<Integer> keys;

	public SendItem() {
		keys = null;
	}

	public SendItem(int id, List<Integer> keys) {
		this.id = id;
		this.keys = keys;
	}

	public int getId() {
		return id;
	}

	public List<Integer> getKeys() {
		return keys;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setKeys(List<Integer> keys) {
		this.keys = keys;
	}

}
