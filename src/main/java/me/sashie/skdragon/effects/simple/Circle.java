package me.sashie.skdragon.effects.simple;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SimpleEffect;
import me.sashie.skdragon.util.Utils;

public class Circle extends SimpleEffect {

	public Circle() {
		this.getRadiusProperty().initRadius(1.5f, 1.5f);
	}

	@Override
	public void math(float step) {
		double angle = step * Utils.PI2 / this.getDensityProperty().getDensity(1);
		v.setX(Math.cos(angle) * this.getRadiusProperty().getRadius(1));
		v.setZ(Math.sin(angle) * this.getRadiusProperty().getRadius(2));
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return new EffectProperty[0];
	}
}
