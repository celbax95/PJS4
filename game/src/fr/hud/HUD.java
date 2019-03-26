package fr.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

import fr.application.Application;
import fr.itemsApp.Drawable;
import fr.itemsApp.Manageable;
import fr.itemsApp.character.ICharacter;
/**
 * HUD (interface utilisateur) 
 * Utilise pour afficher la barre de vie et le  cooldown du joueur
 * et des eventuels changements statuts de celui ci. 
 * @author renar
 */
public class HUD implements Drawable, Serializable, Manageable{
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 510, HEIGHT = 230;
	
	private static final int RECTANGLE_HEIGHT = 26, RECTANGLE_MAX_WIDTH = 200;
	
	private static final int LIFE_MAX_AMOUNT = 100;
	
	private int currentLifeAmount, currentCoolDownAmount;
	
	private float lifeRectangleWidth = RECTANGLE_MAX_WIDTH, coolDownRegtangleWidth = RECTANGLE_MAX_WIDTH;
	
	private static Image hudGif = (new ImageIcon(HUD.class.getResource("/images/HUD/hud.gif")))
	.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
	
	private static Image hudPng = (new ImageIcon(HUD.class.getResource("/images/HUD/hud.png")))
			.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
	
	
	private Font font = new Font("Arial", Font.PLAIN, 24);
	
	/**
	 * constructeur HUD
	 * @param player : Le joueur 
	 */
	public HUD(ICharacter player) {
		currentLifeAmount = player.getHealth();
	}
	/**
	 * Permet d'afficher les elements de l'HUD
	 * @param g : Permet l'affichage
	 */
	public void draw(Graphics2D g) {
		g.drawImage(hudPng, 0, 0, null);
		g.drawImage(hudGif, 0, 0, null);
		g.setColor(Color.BLACK);
		// test
		currentLifeAmount = 30;
		if(currentLifeAmount < LIFE_MAX_AMOUNT)
			lifeRectangleWidth = ((float)((LIFE_MAX_AMOUNT - currentLifeAmount) / 100.0) * RECTANGLE_MAX_WIDTH);
		coolDownRegtangleWidth = 1;
        g.fillRect(222 + (RECTANGLE_MAX_WIDTH - ((LIFE_MAX_AMOUNT - currentLifeAmount)*2)),
        		22,(int)lifeRectangleWidth, RECTANGLE_HEIGHT);
        g.fillRect(222, 76,(int)coolDownRegtangleWidth, RECTANGLE_HEIGHT);
	}
	/**
	 * Gestion de l'element
	 * @param app               : Application
	 * @param timeSinceLastCall : temps depuis le dernier appel, pour la
	 *                            synchro @see Application.run
	 */
	@Override
	public void manage(Application app, double timeSinceLastCall) {
		
		
	}
}
