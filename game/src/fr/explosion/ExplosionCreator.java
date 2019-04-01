package fr.explosion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.application.Application;
import fr.itemsApp.bomb.IBomb;
import fr.itemsApp.items.ItemFactory;
import fr.itemsApp.items.PlaceableItem;
import fr.map.MapTile;
import fr.tiles.Floor;
import fr.util.point.Point;

/**
 * Cr�er une explosion
 */
public class ExplosionCreator implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<IExplosion> explosions;

	/**
	 * Creer une liste d'explosions (dans 4 directions diff�rentes) pour 1 bombe et
	 * les ajoutes � la liste des drawables et des manageables
	 *
	 * @param application
	 * @param bombe
	 */
	public void create(Application application, IBomb bombe) {

		this.explosions = new ArrayList<>();

		MapTile[][] m = application.getMap().getMap();

		// horizontal

		Point tile = bombe.getTile();

		int sizeExplosion = bombe.getExplosionSize();

		boolean continueLeft = true, continueRight = true, continueUp = true, continueDown = true;

		IExplosion explosion = bombe.getExplosion();
		explosion.setTile(tile.clone());
		explosion.setType(0);

		this.explosions.add(explosion);

		for (int i = 1; i <= sizeExplosion; i++) {
			if (continueRight && (continueRight = this.setExplosion(m, bombe, 1, tile.getIX() + i, tile.getIY())))
				;
			if (continueLeft && (continueLeft = this.setExplosion(m, bombe, 1, tile.getIX() - i, tile.getIY())))
				;
			if (continueUp && (continueUp = this.setExplosion(m, bombe, 2, tile.getIX(), tile.getIY() - i)))
				;
			if (continueDown && (continueDown = this.setExplosion(m, bombe, 2, tile.getIX(), tile.getIY() + i)))
				;
		}
		for (IExplosion e : this.explosions) {
			application.addDrawable(e);
			application.addManageable(e);
			application.addExplosion(e);
		}
	}

	/**
	 * V�rifie si une explosion peut etre plac�e � une position (x, y) donn�e
	 * determine �galement la taille de la serie d'explosions et si l'explosion peut
	 * d�truire un mur ou non
	 *
	 * @param mapTile : ensemble des �l�ments pr�sents sur le terrain
	 * @param bombe   : la bombe qui est d�pos�e sur le terrain
	 * @param type    : type de l'explosion (1 direction droite ou gauche) (2
	 *                direction haut ou bas)
	 * @param x       : abscisse du point ou doit etre placee l'explosion
	 * @param y       : ordonn�e du point ou doit etre placee l'explosion
	 */
	private boolean setExplosion(MapTile[][] mapTile, IBomb bombe, int type, int x, int y) {
		if (x < 0 || x > mapTile.length || y < 0 || y > mapTile[0].length)
			return false;

		if (mapTile[x][y].isDestroyable() || mapTile[x][y].isWalkable()) {
			IExplosion ex = bombe.getExplosion();
			ex.setTile(new Point(x, y));
			ex.setType(type);
			this.explosions.add(ex);
		}

		if (mapTile[x][y].isDestroyable()) {
			Point tile = mapTile[x][y].getTile();
			mapTile[x][y] = new Floor(tile.getIX(), tile.getIY());
			ItemFactory ifa = ItemFactory.getInstance();
			PlaceableItem pa = ifa.createRandom();
		}

		return !mapTile[x][y].isDestroyable() || mapTile[x][y].isWalkable();
	}
}
