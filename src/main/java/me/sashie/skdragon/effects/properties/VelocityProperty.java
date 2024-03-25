package me.sashie.skdragon.effects.properties;

import me.sashie.skdragon.particles.Value3d;
import me.sashie.skdragon.util.VectorUtils;
import org.bukkit.util.Vector;

public class VelocityProperty {

	private Value3d angularVelocity = new Value3d(Math.PI / 200, Math.PI / 170, Math.PI / 155);
	boolean rotating;

	public double getAngularVelocityX() {
		return angularVelocity.getX();
	}

	public double getAngularVelocityY() {
		return angularVelocity.getY();
	}

	public double getAngularVelocityZ() {
		return angularVelocity.getZ();
	}

	public void setAngularVelocity(double x, double y, double z) {
		this.angularVelocity.setX(Math.PI / (x == 0 ? 1 : x));
		this.angularVelocity.setY(Math.PI / (y == 0 ? 1 : y));
		this.angularVelocity.setZ(Math.PI / (z == 0 ? 1 : z));
	}

	public boolean isRotating() {
		return rotating;
	}

	public void setRotating(boolean rotating) {
		this.rotating = rotating;
	}

	public Vector updateRotation(Vector v, float step) {
		if (rotating)
			return VectorUtils.rotateVector(v, angularVelocity.getX() * step, angularVelocity.getY() * step, angularVelocity.getZ() * step);
		return v;
	}
}
