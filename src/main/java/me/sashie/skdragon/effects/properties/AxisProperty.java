package me.sashie.skdragon.effects.properties;

import org.bukkit.util.Vector;

import me.sashie.skdragon.particles.Value3d;

public class AxisProperty {

	private Value3d axis = new Value3d();

	public Vector getAxisAsVector() {
		return new Vector(axis.getX(), axis.getY(), axis.getZ());
	}

	public Value3d getAxis() {
		return axis;
	}

	/**
	 * Values in degrees
	 *
	 * @param axis
	 */
	public void setAxis(Value3d axis) {
		this.axis.setX(Math.toRadians(axis.getX()));
		this.axis.setY(Math.toRadians(axis.getY()));
		this.axis.setZ(Math.toRadians(axis.getZ()));
	}

	/**
	 * Values in degrees
	 *
	 * @param axis
	 */
	public void setAxis(Vector axis) {
		this.axis.setX(Math.toRadians(axis.getX()));
		this.axis.setY(Math.toRadians(axis.getY()));
		this.axis.setZ(Math.toRadians(axis.getZ()));
	}

	/**
	 * Values in degrees
	 *
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setAxis(double x, double y, double z) {
		this.axis.setX(Math.toRadians(x));
		this.axis.setY(Math.toRadians(y));
		this.axis.setZ(Math.toRadians(z));
	}
}
