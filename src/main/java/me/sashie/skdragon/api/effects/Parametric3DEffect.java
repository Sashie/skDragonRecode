package me.sashie.skdragon.api.effects;

import me.sashie.skdragon.api.util.MathUtils;

public abstract class Parametric3DEffect extends SimpleValueEffect {

	public Parametric3DEffect() {
		this.getDensityProperty().initDensity(60, 60);
	}

	public abstract double vectorX(double angle, double angle2);

	public abstract double vectorY(double angle, double angle2);

	public abstract double vectorZ(double angle, double angle2);

	@Override
	public void math(float step) {
		double angle = step * MathUtils.PI2 / this.getDensityProperty().getDensity(1);
		double angle2 = step * MathUtils.PI2 / this.getDensityProperty().getDensity(2);
		v.setX(vectorX(angle, angle2));
		v.setY(vectorY(angle, angle2));
		v.setZ(vectorZ(angle, angle2));
	}

}
