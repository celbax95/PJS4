package fr.tiles;

import fr.map.IFactoryTile;
import fr.map.MapTile;

public class FactoryTile implements IFactoryTile {

	private static final long serialVersionUID = 1L;

	@Override
	public MapTile create(char type, int x, int y, int tileSize) {
		switch (type) {
		case '_':
			return new Floor(x, y, tileSize);
		case 'W':
			return new SideWall(x, y, tileSize);
		case 'X':
			return new UnbreakableWall(x, y, tileSize);
		case '+':
			return new BreakableWall(x, y, tileSize);
		case 'G':
			return new Spawner(x, y, tileSize);
		case 'S':
			return new SpawnPoint(x, y, tileSize);
		default:
			return null;
		}

	}

}
