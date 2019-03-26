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
	//private static int SIZE = (int) (DEFAULT_SIZE * Scale.getScale());
	
	private int lifeBarAmount, lifeBarMaxAmount;
	private static final int WIDTH = 200, HEIGHT = 50;
	private static Image image = (new ImageIcon(HUD.class.getResource("/images/HUD/hud.png")))
	.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
	private Font font = new Font("Arial", Font.PLAIN, 24);
	
	/**
	 * constructeur HUD
	 * @param player : Le joueur 
	 */
	public HUD(ICharacter player) {
		lifeBarAmount = player.getHealth();
		lifeBarMaxAmount = 100;
	}
	/**
	 * Permet d'afficher les elements de l'HUD
	 * @param g : Permet l'affichage
	 */
	public void draw(Graphics2D g) {
		g.drawImage(image, 0, 20, null);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(lifeBarAmount + "/" + lifeBarMaxAmount, 70, 55);
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
