package fr.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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

	private static final double RECTANGLE_HEIGHT = 26 * rescaleRatio, RECTANGLE_MAX_WIDTH = 200 * rescaleRatio,
			RECTANGLE_X = 222 * rescaleRatio;

	private static final double LIFE_MAX_AMOUNT = 100;

	private static Image hudGif = (new ImageIcon(HUD.class.getResource("/images/HUD/hud.gif"))).getImage()
			.getScaledInstance((int) WIDTH, (int) HEIGHT, Image.SCALE_DEFAULT);

	private static Image hudPng = (new ImageIcon(HUD.class.getResource("/images/HUD/hud.png"))).getImage()
			.getScaledInstance((int) WIDTH, (int) HEIGHT, Image.SCALE_DEFAULT);

	private int currentLifeAmount, currentCoolDownAmount;

	private double lifeRectangleWidth = RECTANGLE_MAX_WIDTH, coolDownRegtangleWidth = RECTANGLE_MAX_WIDTH;

	private Font font = new Font("Arial", Font.PLAIN, 24);

	private ICharacter player;

	/**
	 * constructeur HUD
	 *
	 * @param player
	 *            : Le joueur
	 */
	public HUD(ICharacter player) {
		this.player = player;
		// currentLifeAmount = player.getHealth();
		lifeRectangleWidth = 1;
		coolDownRegtangleWidth = 1;
	}

	public void applyDamage(double damage) {
		if (damage > LIFE_MAX_AMOUNT)
			damage = LIFE_MAX_AMOUNT;
		player.setHealth(LIFE_MAX_AMOUNT - damage);
		lifeRectangleWidth = ((damage / 100.0) * RECTANGLE_MAX_WIDTH);
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
		applyDamage(70);
		Rectangle lifeRect = new Rectangle(
				(int) (RECTANGLE_X + ((RECTANGLE_MAX_WIDTH * player.getHealth()) / LIFE_MAX_AMOUNT)),
				(int) (22 * rescaleRatio), (int) lifeRectangleWidth, (int) RECTANGLE_HEIGHT);
		g.fill(lifeRect);

		g.fillRect((int) RECTANGLE_X, (int) (76 * rescaleRatio), (int) coolDownRegtangleWidth, (int) RECTANGLE_HEIGHT);
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
