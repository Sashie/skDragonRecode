package me.sashie.skdragon.effects.simple;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SimpleEffect;
import me.sashie.skdragon.util.MathUtils;
import me.sashie.skdragon.util.Utils;

public class SphericalWave extends SimpleEffect {

	public SphericalWave() {
		this.getRadiusProperty().initRadius(1.5f, 1.5f, 1.5f, 0.3f); //radius, wave amplitude
		this.getDensityProperty().initDensity(80, 6); // particle, wave frequency
		this.getStepTypesProperty().setSolid(true);
	}

	@Override
	public void math(float step) {
		double t = step / this.getDensityProperty().getDensity(1);
		float angle1 = (float) Math.acos(1 - 2 * t);
		float angle2 = (float) (Utils.GOLDEN_ANGLE_INCREMENT * step);

		double waveMotion = this.getRadiusProperty().getRadius(4) * MathUtils.sin(this.getDensityProperty().getDensity(2) * angle2);

		double x = (this.getRadiusProperty().getRadius(1) + waveMotion) * MathUtils.sin(angle1) * MathUtils.cos(angle2);
		double y = (this.getRadiusProperty().getRadius(2) + waveMotion) * MathUtils.sin(angle1) * MathUtils.sin(angle2);
		double z = (this.getRadiusProperty().getRadius(3) + waveMotion) * MathUtils.cos(angle1);
		v.setX(x).setY(y).setZ(z);
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return new EffectProperty[0];
	}
}
