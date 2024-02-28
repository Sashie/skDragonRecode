package me.sashie.skdragon.effects.simple;

import me.sashie.skdragon.effects.SimpleRadiusEffect;
import me.sashie.skdragon.util.Utils;

public class SphericalWave extends SimpleRadiusEffect {

	public SphericalWave() {
		this.getRadiusProperty().initRadius(1.5f, 0.3f); //radius, wave amplitude
		this.getDensityProperty().initDensity(80, 6); // particle, wave frequency
		this.getSolidProperty().setSolid(true);
	}

	@Override
	public void math(float step) {
		double t = step / this.getDensityProperty().getDensity(1);
		double angle1 = Math.acos(1 - 2 * t);
		double angle2 = Utils.GOLDEN_ANGLE_INCREMENT * step;

		double waveMotion = this.getRadiusProperty().getRadius(2) * Math.sin(this.getDensityProperty().getDensity(2) * angle2);

		double x = (this.getRadiusProperty().getRadius(1) + waveMotion) * Math.sin(angle1) * Math.cos(angle2);
		double y = (this.getRadiusProperty().getRadius(1) + waveMotion) * Math.sin(angle1) * Math.sin(angle2);
		double z = (this.getRadiusProperty().getRadius(1) + waveMotion) * Math.cos(angle1);
		v.setX(x).setY(y).setZ(z);
	}
}
