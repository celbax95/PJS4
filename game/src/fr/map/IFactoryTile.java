package fr.map;

import java.io.Serializable;

public interface IFactoryTile extends Serializable {
	public MapTile create(char type, int x, int y, int tileSize);
}
