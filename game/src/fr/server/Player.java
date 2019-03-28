package fr.server;

public class Player {
	private int no;
	private String alias;
	
	public Player(int no, String alias) {
		this.no = no;
		this.alias = alias;
	}
	
	public int getNo() {
		return no;
	}
	public String getAlias() {
		return alias;
	}
}
