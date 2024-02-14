package me.sashie.skdragon.effects.properties;

import me.sashie.skdragon.particles.Value3d;
import org.bukkit.util.Vector;

public class AxisProperty {

	private final Value3d axis = new Value3d();

	public Value3d getAxis() {
		return axis;
	}

	/**
	 * Values in degrees
	 */
	public void setAxis(Value3d axis) {
		setAxis(axis.toVector());
	}

	/**
	 * Values in degrees
	 */
	public void setAxis(Vector axis) {
		setAxis(axis.getX(), axis.getY(), axis.getZ());
	}

	/**
	 * Values in degrees
	 */
	public void setAxis(double x, double y, double z) {
		this.axis.setX(Math.toRadians(x));
		this.axis.setY(Math.toRadians(y));
		this.axis.setZ(Math.toRadians(z));
	}

}
