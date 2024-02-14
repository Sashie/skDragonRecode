package me.sashie.skdragon.project.effects.simple.parametric;

import me.sashie.skdragon.project.effects.Parametric2DEffect;

/**
 * x = a cos3(t)
 * z = a sin3(t)
 */
public class Astroid2D extends Parametric2DEffect {

	public Astroid2D() {
		this.getExtraProperty().initValue(1.5f);
		this.getSolidProperty().setSolid(true);
	}

	@Override
	public double vectorX(double angle) {
		return this.getExtraProperty().getValue(1) * Math.pow(Math.cos(angle), 3);
	}

	@Override
	public double vectorZ(double angle) {
		return this.getExtraProperty().getValue(1) * Math.pow(Math.sin(angle), 3);
	}

}
