package me.sashie.skdragon.effects;

import me.sashie.skdragon.util.MathUtils;

public abstract class Parametric3DEffect extends SimpleEffect {

	public Parametric3DEffect() {
		this.getDensityProperty().initDensity(60, 60);
	}

	public abstract float vectorX(float angle, float angle2);
	public abstract float vectorY(float angle, float angle2);
	public abstract float vectorZ(float angle, float angle2);

	@Override
	public void math(float step) {
		float angle = step * MathUtils.PI2 / this.getDensityProperty().getDensity(1);
		float angle2 = step * MathUtils.PI2 / this.getDensityProperty().getDensity(2);
		v.setX(vectorX(angle, angle2));
		v.setY(vectorY(angle, angle2));
 		v.setZ(vectorZ(angle, angle2));
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return new EffectProperty[0];
	}

}
