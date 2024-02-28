package me.sashie.skdragon.effects.properties;

import me.sashie.skdragon.particles.Value3d;

public class VelocityProperty {

	private Value3d angularVelocity = new Value3d(Math.PI / 200, Math.PI / 170, Math.PI / 155);

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
	 *
	 *
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setAngularVelocity(double x, double y, double z) {
		this.angularVelocity.setX(Math.PI / (x == 0 ? 1 : x));
		this.angularVelocity.setY(Math.PI / (y == 0 ? 1 : y));
		this.angularVelocity.setZ(Math.PI / (z == 0 ? 1 : z));
	}

}
