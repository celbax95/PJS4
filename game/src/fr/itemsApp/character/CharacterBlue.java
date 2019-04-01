package fr.itemsApp.character;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

/**
 * Personnage
 */
public class CharacterBlue extends ACharacter {

	protected static final long serialVersionUID = 1L;

	private static Image imgS = (new ImageIcon(CharacterBlue.class.getResource("/images/characters/blue/stand.png")))
			.getImage();
	private static Image[] imgD = {
			(new ImageIcon(CharacterBlue.class.getResource("/images/characters/blue/walk_1.png"))).getImage(),
			(new ImageIcon(CharacterBlue.class.getResource("/images/characters/blue/walk_2.png"))).getImage() };

	/**
	 * @param x            : Position x
	 * @param y            : Position y
	 * @param bombCoolDown : Temps entre chaque pose de bombe
	 * @param speed        : Vitesse du personnage
	 */
	public CharacterBlue(double x, double y, int health, int bombCoolDown, int speed) {
		super(x, y, health, bombCoolDown, speed);
	}

	@Override
	public void draw(Graphics2D g) {

		AffineTransform af = new AffineTransform();
		af.translate(pos.x, pos.y);
		af.rotate(angleOfView, DEFAULT_SIZE / 2, DEFAULT_SIZE / 2);

		// Affichage du skin statique ou en mouvement
		if (moves.x == 0 && moves.y == 0) {
			g.drawImage(imgS, af, null);
		} else {
			g.drawImage(imgD[(walkStep < walkFrequence / 2) ? 0 : 1], af, null);
		}
	}

}
