package me.sashie.skdragon.effects;

import me.sashie.skdragon.util.MathUtils;

/**
 * Created by Sashie on 8/11/2017.
 *
 */

public abstract class Parametric2DEffect extends SimpleEffect {

	public Parametric2DEffect() {
		this.getDensityProperty().initDensity(60);
	}

	public abstract float vectorX(float angle);
	public abstract float vectorZ(float angle);

	@Override
	public void math(float step) {
		float angle = step * MathUtils.PI2 / this.getDensityProperty().getDensity(1);
		v.setX(vectorX(angle));
 		v.setZ(vectorZ(angle));
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return new EffectProperty[0];
	}

}
