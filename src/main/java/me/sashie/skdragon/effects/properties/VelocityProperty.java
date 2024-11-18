package me.sashie.skdragon.effects.properties;

import me.sashie.skdragon.math.Value3d;
import me.sashie.skdragon.util.MathUtils;
import me.sashie.skdragon.util.VectorUtils;
import org.bukkit.util.Vector;

public class VelocityProperty {

	private Value3d angularVelocity = new Value3d(MathUtils.PI / 200, MathUtils.PI / 170, MathUtils.PI / 155);
	boolean rotating;

	public float getAngularVelocityX() {
		return angularVelocity.getX();
	}

	public float getAngularVelocityY() {
		return angularVelocity.getY();
	}

	public float getAngularVelocityZ() {
		return angularVelocity.getZ();
	}

	public void setAngularVelocity(float x, float y, float z) {
		this.angularVelocity.setX(MathUtils.PI / (x == 0 ? 1 : x));
		this.angularVelocity.setY(MathUtils.PI / (y == 0 ? 1 : y));
		this.angularVelocity.setZ(MathUtils.PI / (z == 0 ? 1 : z));
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
