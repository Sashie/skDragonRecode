package me.sashie.skdragon.effects.simple;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SimpleEffect;
import me.sashie.skdragon.util.MathUtils;

public class Ellipse extends SimpleEffect {

	public Ellipse() {
		this.getRadiusProperty().initRadius(1.5f, 1.5f);
	}

	@Override
	public void math(float step) {
		float angle = step * MathUtils.PI2 / this.getDensityProperty().getDensity(1);
		v.setX(MathUtils.cos(angle) * this.getRadiusProperty().getRadius(1));
		v.setZ(MathUtils.sin(angle) * this.getRadiusProperty().getRadius(2));
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return new EffectProperty[0];
	}
}
