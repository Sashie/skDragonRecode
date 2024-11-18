package me.sashie.skdragon.effects.simple.parametric;

import me.sashie.skdragon.effects.Parametric3DEffect;
import me.sashie.skdragon.util.MathUtils;

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
	public float vectorX(float angle, float angle2) {
		return this.getRadiusProperty().getRadius(1) * MathUtils.cos(angle);
	}

	@Override
	public float vectorY(float angle, float angle2) {
		return this.getRadiusProperty().getRadius(2) * MathUtils.cos(angle2);
	}

	@Override
	public float vectorZ(float angle, float angle2) {
		return this.getRadiusProperty().getRadius(1) * MathUtils.sin(angle);
	}

}
