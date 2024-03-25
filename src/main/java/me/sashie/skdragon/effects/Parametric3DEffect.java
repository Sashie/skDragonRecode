package me.sashie.skdragon.effects;

import me.sashie.skdragon.util.Utils;

public abstract class Parametric3DEffect extends SimpleEffect {

	public Parametric3DEffect() {
		this.getDensityProperty().initDensity(60, 60);
	}

	public abstract double vectorX(double angle, double angle2);
	public abstract double vectorY(double angle, double angle2);
	public abstract double vectorZ(double angle, double angle2);

	@Override
	public void math(float step) {
		double angle = step * Utils.PI2 / this.getDensityProperty().getDensity(1);
		double angle2 = step * Utils.PI2 / this.getDensityProperty().getDensity(2);
		v.setX(vectorX(angle, angle2));
		v.setY(vectorY(angle, angle2));
 		v.setZ(vectorZ(angle, angle2));
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return new EffectProperty[0];
	}

}
