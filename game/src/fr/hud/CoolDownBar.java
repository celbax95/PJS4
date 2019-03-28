package fr.hud;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import fr.application.Application;
import fr.itemsApp.character.ICharacter;

public class CoolDownBar extends ABar {

	private static final long serialVersionUID = 1L;

	public CoolDownBar(double barX, double barY, double barWidth, double barHeight, ICharacter player,
			double rescaleRatio) {
		super(barX, barY, barWidth, barHeight, player, rescaleRatio);
	}

	@Override
	public void draw(Graphics2D g) {
		bar = new Rectangle((int) barX, (int) barY, (int) barWidth, (int) barHeight);
		g.fill(bar);
	}

	@Override
	public void manage(Application app, double timeSinceLastCall) {

	}

}
