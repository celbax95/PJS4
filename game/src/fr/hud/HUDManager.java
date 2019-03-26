package fr.hud;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import fr.application.Application;
/**
 * Gestionnaire d'HUD en singleton
 */
public class HUDManager implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static HUDManager hudC;
	private Map<Integer, HUD> huds;
	static {
		hudC = new HUDManager();
	}
	/**
	 * Constructeur HUDManager
	 */
	private HUDManager() {
		huds = new HashMap<>();
	}
	/**
	 * @return l'unique instance d'HUDManager
	 */
	public static HUDManager getInstance() {
		return hudC;
	}
	/**
	 * Creer un HUD et l'ajoute dans la liste des huds
	 * des drawables, et des manageables
	 * @param a
	 * @param idPlayer
	 */
	public void createHUD(Application a, int idPlayer) {
		HUD hud = new HUD(a.getPlayerById(idPlayer));
		huds.put(idPlayer, hud);
		a.addDrawable(hud);
		a.addManageable(hud);
	}
	
	/**
	 * Supprime un hud de l'application
	 * @param id : l'id de l'HUD à supprimer
	 */
	public void deleteHUD(Application a, int id) {
		HUD hud = huds.get(id);
		huds.remove(id);
		a.removeDrawable(hud);
		a.removeManageable(hud);
	}
	
}
