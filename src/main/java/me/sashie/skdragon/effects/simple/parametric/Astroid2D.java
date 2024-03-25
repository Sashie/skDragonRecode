package me.sashie.skdragon.effects.simple.parametric;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.Parametric2DEffect;

/**
 * x = a cos3(t)
 * z = a sin3(t)
 */
public class Astroid2D extends Parametric2DEffect {

	public Astroid2D() {
		this.getRadiusProperty().initRadius(1.5f);
		this.getStepTypesProperty().setSolid(true);
	}

	@Override
	public double vectorX(double angle) {
		return this.getRadiusProperty().getRadius(1) * Math.pow(Math.cos(angle), 3);
	}

	@Override
	public double vectorZ(double angle) {
		return this.getRadiusProperty().getRadius(1) * Math.pow(Math.sin(angle), 3);
	}

}
