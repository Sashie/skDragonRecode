package me.sashie.skdragon.effects.properties;

import me.sashie.skdragon.particles.Value3d;
import me.sashie.skdragon.util.VectorUtils;
import org.bukkit.util.Vector;

public class AxisProperty {

	private Value3d axis = new Value3d();

	/**
	 * Should not be used in effects
	 *
	 * @return Vector with values in degrees
	 */
	public Vector getAxisAsVector() {
		return new Vector(Math.toDegrees(axis.getX()), Math.toDegrees(axis.getY()), Math.toDegrees(axis.getZ()));
	}

	/**
	 *
	 * @return Values in radians
	 */
	public Value3d getAxis() {
		return axis;
	}

	/**
	 * Input values in degrees
	 */
	public void setAxis(Value3d axis) {
		setAxis(axis.getX(), axis.getY(), axis.getZ());
	}

	/**
	 * Input values in degrees
	 */
	public void setAxis(Vector axis) {
		setAxis(axis.getX(), axis.getY(), axis.getZ());
	}

	/**
	 * Input values in degrees
	 */
	public void setAxis(double x, double y, double z) {
		this.axis.setX(Math.toRadians(x));
		this.axis.setY(Math.toRadians(y));
		this.axis.setZ(Math.toRadians(z));
		this.axis.normalize();
	}

	public void rotateAxis(Vector v) {
		VectorUtils.rotateVector(v, axis.getX(), axis.getY(), axis.getZ());
	}
}
