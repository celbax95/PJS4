package fr.hud;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

import fr.application.Application;
import fr.itemsApp.Drawable;
import fr.itemsApp.Manageable;
import fr.itemsApp.character.ICharacter;

/**
 * HUD (interface utilisateur) Utilise pour afficher la barre de vie et le
 * cooldown du joueur et des eventuels changements statuts de celui ci.
 *
 * @author renar
 */
public class HUD implements Drawable, Serializable, Manageable {
	private static final long serialVersionUID = 1L;

	private static final double rescaleRatio = 0.7;

	private static final double WIDTH = 510 * rescaleRatio, HEIGHT = 230 * rescaleRatio;

	private static Image hudGif = (new ImageIcon(HUD.class.getResource("/images/HUD/hud.gif"))).getImage()
			.getScaledInstance((int) WIDTH, (int) HEIGHT, Image.SCALE_DEFAULT);

	private static Image hudPng = (new ImageIcon(HUD.class.getResource("/images/HUD/hud.png"))).getImage()
			.getScaledInstance((int) WIDTH, (int) HEIGHT, Image.SCALE_DEFAULT);

	private ICharacter player;

	private ABar LifeBar, coolDownBar;

	/**
	 * constructeur HUD
	 *
	 * @param player
	 *            : Le joueur
	 */
	public HUD(ICharacter player) {
		this.player = player;
		LifeBar = new LifeBar(222, 22, 1, 26, player, rescaleRatio);
		coolDownBar = new CoolDownBar(222, 76, 1, 26, player, rescaleRatio);

	}

	/**
	 * Permet d'afficher les elements de l'HUD
	 *
	 * @param g
	 *            : Permet l'affichage
	 */
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(hudPng, 0, 0, null);
		g.drawImage(hudGif, 0, 0, null);
		g.setColor(Color.BLACK);
		LifeBar.draw(g);
		coolDownBar.draw(g);
	}

	/**
	 * Gestion de l'element
	 *
	 * @param app
	 *            : Application
	 * @param timeSinceLastCall
	 *            : temps depuis le dernier appel, pour la synchro @see
	 *            Application.run
	 */
	@Override
	public void manage(Application app, double timeSinceLastCall) {

	}
}
