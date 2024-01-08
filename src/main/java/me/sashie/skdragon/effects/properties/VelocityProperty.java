package me.sashie.skdragon.effects.properties;

import me.sashie.skdragon.particles.Value3d;
import me.sashie.skdragon.util.MathUtils;
import me.sashie.skdragon.util.Utils;

public class VelocityProperty {

	private Value3d angularVelocity = new Value3d();

	/*public VelocityProperty(float x, float y, float z) {
		this.angularVelocity.setX(MathUtils.PI / x);
		this.angularVelocity.setY(MathUtils.PI / y);
		this.angularVelocity.setZ(MathUtils.PI / z);
	}*/

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
	 * Values in degrees
	 *
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setAngularVelocity(float x, float y, float z) {
		this.angularVelocity.setX(MathUtils.PI / x);
		this.angularVelocity.setY(MathUtils.PI / y);
		this.angularVelocity.setZ(MathUtils.PI / z);
	}

}
