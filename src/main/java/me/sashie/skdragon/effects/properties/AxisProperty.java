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

	public void setAxis(Value3d axis) {
		this.axis.setX(axis.getX());
		this.axis.setY(axis.getY());
		this.axis.setZ(axis.getZ());
	}

	public void setAxis(Vector axis) {
		this.axis.setX(axis.getX());
		this.axis.setY(axis.getY());
		this.axis.setZ(axis.getZ());
	}

	public void setAxis(double x, double y, double z) {
		this.axis.setX(x);
		this.axis.setY(y);
		this.axis.setZ(z);
	}
}
