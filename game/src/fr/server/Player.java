package fr.server;

import java.io.Serializable;

public class Player implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int no;
	private String alias;
	
	public Player(int no, String alias) {
		this.no = no;
		this.alias = alias;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String toString() {
		return no + " " + alias;
	}
}
