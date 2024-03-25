package me.sashie.skdragon.effects.simple.parametric;

import me.sashie.skdragon.effects.Parametric3DEffect;

/**
 * x = a * cos(t)
 * z = a * sin(t)
 * y = b * cos(u)
 */
public class Lissajous3D extends Parametric3DEffect {

	public Lissajous3D() {
		this.getRadiusProperty().initRadius(1.5f, 1.5f);
		this.getStepTypesProperty().setSolid(true);
	}

	@Override
	public double vectorX(double angle, double angle2) {
		return this.getRadiusProperty().getRadius(1) * Math.cos(angle);
	}

	@Override
	public double vectorY(double angle, double angle2) {
		return this.getRadiusProperty().getRadius(2) * Math.cos(angle2);
	}

	@Override
	public double vectorZ(double angle, double angle2) {
		return this.getRadiusProperty().getRadius(1) * Math.sin(angle);
	}

}
