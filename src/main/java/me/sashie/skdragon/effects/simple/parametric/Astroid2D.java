package me.sashie.skdragon.effects.simple.parametric;

import me.sashie.skdragon.effects.Parametric2DEffect;
import me.sashie.skdragon.util.MathUtils;

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
	public float vectorX(float angle) {
		return (float) (this.getRadiusProperty().getRadius(1) * Math.pow(MathUtils.cos(angle), 3));
	}

	@Override
	public float vectorZ(float angle) {
		return (float) (this.getRadiusProperty().getRadius(1) * Math.pow(MathUtils.sin(angle), 3));
	}

}
