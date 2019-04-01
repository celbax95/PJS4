package fr.explosion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.application.Application;
import fr.itemsApp.bomb.IBomb;
import fr.itemsApp.items.ItemFactory;
import fr.itemsApp.items.PlaceableItem;
import fr.map.GameMap;
import fr.map.MapTile;
import fr.tiles.Floor;
import fr.util.point.Point;

/**
 * Créer une explosion
 */
public class ExplosionCreator implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<IExplosion> explosions;

	/**
	 * Creer une liste d'explosions (dans 4 directions différentes) pour 1 bombe et
	 * les ajoutes à la liste des drawables et des manageables
	 *
	 * @param application
	 * @param bombe
	 */
	public void create(Application application, IBomb bombe) {

		this.explosions = new ArrayList<>();

		GameMap map = application.getMap();

		// horizontal

		Point tile = bombe.getTile();

		int sizeExplosion = bombe.getExplosionSize();

		boolean continueLeft = true, continueRight = true, continueUp = true, continueDown = true;

		IExplosion explosion = bombe.getExplosion();
		explosion.setTile(tile.clone());
		explosion.setType(0);

		this.explosions.add(explosion);

		for (int i = 1; i <= sizeExplosion; i++) {
			if (continueRight && (continueRight = this.setExplosion(map, bombe, 1, tile.getIX() + i, tile.getIY())))
				;
			if (continueLeft && (continueLeft = this.setExplosion(map, bombe, 1, tile.getIX() - i, tile.getIY())))
				;
			if (continueUp && (continueUp = this.setExplosion(map, bombe, 2, tile.getIX(), tile.getIY() - i)))
				;
			if (continueDown && (continueDown = this.setExplosion(map, bombe, 2, tile.getIX(), tile.getIY() + i)))
				;
		}
		for (IExplosion e : this.explosions) {
			application.addDrawable(e);
			application.addManageable(e);
			application.addExplosion(e);
		}
	}

	/**
	 * Vérifie si une explosion peut etre placée à une position (x, y) donnée
	 * determine également la taille de la serie d'explosions et si l'explosion peut
	 * détruire un mur ou non
	 *
	 * @param mapTile : ensemble des éléments présents sur le terrain
	 * @param bombe   : la bombe qui est déposée sur le terrain
	 * @param type    : type de l'explosion (1 direction droite ou gauche) (2
	 *                direction haut ou bas)
	 * @param x       : abscisse du point ou doit etre placee l'explosion
	 * @param y       : ordonnée du point ou doit etre placee l'explosion
	 */
	private boolean setExplosion(GameMap map, IBomb bombe, int type, int x, int y) {

		MapTile[][] mapTiles = map.getMap();

		if (x < 0 || x > mapTiles.length || y < 0 || y > mapTiles[0].length)
			return false;

		if (mapTiles[x][y].isDestroyable() || mapTiles[x][y].isWalkable()) {
			IExplosion ex = bombe.getExplosion();
			ex.setTile(new Point(x, y));
			ex.setType(type);
			this.explosions.add(ex);
		}

		if (mapTiles[x][y].isDestroyable()) {
			Point tile = mapTiles[x][y].getTile();
			mapTiles[x][y] = new Floor(tile.getIX(), tile.getIY());
			ItemFactory ifa = ItemFactory.getInstance();
			PlaceableItem pa = ifa.createRandom();
			pa.setTile(new Point(tile), map.getTileSize());
		}

		return !mapTiles[x][y].isDestroyable() || mapTiles[x][y].isWalkable();
	}
}
