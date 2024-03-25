package me.sashie.skdragon.effects.simple.parametric;

import me.sashie.skdragon.effects.Parametric2DEffect;

/**
 * x = (a + b) * cos(t) - b * cos((a / b + 1) * t)
 * z = (a + b) * sin(t) - b * sin((a / b + 1) * t)
 */

public class Epicycloid2D extends Parametric2DEffect {

	public Epicycloid2D() {
		this.getRadiusProperty().initRadius(1.5f, 1.5f);
		this.getStepTypesProperty().setSolid(true);
	}

	@Override
	public double vectorX(double angle) {
		return (this.getRadiusProperty().getRadius(1) + this.getRadiusProperty().getRadius(2)) * Math.cos(angle) - this.getRadiusProperty().getRadius(2) * Math.cos((this.getRadiusProperty().getRadius(1) / this.getRadiusProperty().getRadius(2) + 1) * angle);
	}

	@Override
	public double vectorZ(double angle) {
		return (this.getRadiusProperty().getRadius(1) + this.getRadiusProperty().getRadius(2)) * Math.sin(angle) - this.getRadiusProperty().getRadius(2) * Math.sin((this.getRadiusProperty().getRadius(1) / this.getRadiusProperty().getRadius(2) + 1) * angle);
	}

}
