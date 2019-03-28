package fr.hud;

import java.awt.Rectangle;
import java.io.Serializable;

import fr.itemsApp.Drawable;
import fr.itemsApp.Manageable;
import fr.itemsApp.character.ICharacter;

public abstract class ABar implements Drawable, Serializable, Manageable {

	private static final long serialVersionUID = 1L;

	private static final double BAR_MAX_WIDTH = 200;
	protected double rescaledMaxBarWidth;
	protected double barY;

	protected double barX;

	protected double barWidth;

	protected double barHeight;
	protected Rectangle bar;
	ICharacter player;

	public ABar(double barX, double barY, double barWidth, double barHeight, ICharacter player, double rescaleRatio) {
		this.player = player;
		rescaledMaxBarWidth = BAR_MAX_WIDTH * rescaleRatio;
		this.barX = barX * rescaleRatio;
		this.barY = barY * rescaleRatio;
		this.barWidth = barWidth * rescaleRatio;
		this.barHeight = barHeight * rescaleRatio;
	}

}
