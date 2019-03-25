package fr.map;

import java.io.Serializable;
/**
 * Fabrique d'elements de terrain
 */
public interface IFactoryTile extends Serializable {
	/**
	 * Creer un element de terrain
	 * @param type : type de l'element
	 * @param x : abscisse du point ou est plac� l'element
	 * @param y : ordonn�e du point ou est plac� l'element
	 * @return l'element de terrain
	 */
	public MapTile create(char type, int x, int y);
}
