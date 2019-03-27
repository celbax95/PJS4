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
	private static final double rescaleHUD = 0.7; 
	private static final double WIDTH = 510 * rescaleHUD, HEIGHT = 230 * rescaleHUD;
	
	private static final double RECTANGLE_HEIGHT = 26 * rescaleHUD, RECTANGLE_MAX_WIDTH = 200 * rescaleHUD;
	
	private static final double LIFE_MAX_AMOUNT = 100;
	
	private int currentLifeAmount, currentCoolDownAmount;
	
	private double lifeRectangleWidth = RECTANGLE_MAX_WIDTH, coolDownRegtangleWidth = RECTANGLE_MAX_WIDTH;
	
	private static Image hudGif = (new ImageIcon(HUD.class.getResource("/images/HUD/hud.gif")))
	.getImage().getScaledInstance((int)WIDTH, (int)HEIGHT, Image.SCALE_DEFAULT);
	
	private static Image hudPng = (new ImageIcon(HUD.class.getResource("/images/HUD/hud.png")))
			.getImage().getScaledInstance((int)WIDTH, (int)HEIGHT, Image.SCALE_DEFAULT);
	
	
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
			lifeRectangleWidth = ((float)(((LIFE_MAX_AMOUNT - currentLifeAmount) / LIFE_MAX_AMOUNT)) * RECTANGLE_MAX_WIDTH);
		coolDownRegtangleWidth = 1;
		System.out.println(lifeRectangleWidth);
        g.fillRect((int)(222 * rescaleHUD) + (int)(RECTANGLE_MAX_WIDTH - (((int)(LIFE_MAX_AMOUNT * rescaleHUD) - currentLifeAmount)*2)),(int)(22 * rescaleHUD),
        		(int)lifeRectangleWidth, (int)(RECTANGLE_HEIGHT));
        g.fillRect((int)(222 * rescaleHUD), (int)(76 * rescaleHUD),(int)coolDownRegtangleWidth, (int)RECTANGLE_HEIGHT);
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
