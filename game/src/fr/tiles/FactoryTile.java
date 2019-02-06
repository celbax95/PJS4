package fr.tiles;

import fr.map.IFactoryTile;
import fr.map.MapTile;

public class FactoryTile implements IFactoryTile {

	private static final long serialVersionUID = 1L;

	@Override
	public MapTile create(char type, int x, int y) {
		switch (type) {
		case '_':
			return new Floor(x, y);
		case 'W':
			return new SideWall(x, y);
		case 'X':
			return new UnbreakableWall(x, y);
		case '+':
			return new BreakableWall(x, y);
		case 'G':
			return new Spawner(x, y);
		case 'S':
			return new SpawnPoint(x, y);
		default:
			return null;
		}

	}

}
