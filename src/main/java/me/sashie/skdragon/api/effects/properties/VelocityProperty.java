package me.sashie.skdragon.api.effects.properties;

import me.sashie.skdragon.api.particles.Value3d;
import me.sashie.skdragon.api.util.MathUtils;

public class VelocityProperty {

	private Value3d angularVelocity = new Value3d(MathUtils.PI / 200, MathUtils.PI / 170, MathUtils.PI / 155);

	public double getAngularVelocityX() {
		return angularVelocity.getX();
	}

	public double getAngularVelocityY() {
		return angularVelocity.getY();
	}

	public double getAngularVelocityZ() {
		return angularVelocity.getZ();
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setAngularVelocity(double x, double y, double z) {
		this.angularVelocity.setX(MathUtils.PI / (x == 0 ? 1 : x));
		this.angularVelocity.setY(MathUtils.PI / (y == 0 ? 1 : y));
		this.angularVelocity.setZ(MathUtils.PI / (z == 0 ? 1 : z));
	}

}
