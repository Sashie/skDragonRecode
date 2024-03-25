package me.sashie.skdragon.particles;

import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

/**
 * Simple class to store three values
 */
public class Value3d implements Cloneable {

	public static final Value3d ONE = new Value3d(1, 1, 1);

	double x, y, z;

	public Value3d(Vector vector) {
		this(vector.getX(), vector.getY(), vector.getZ());
	}

	public Value3d() {
	}

	public Value3d(Value3d vector) {
		this(vector.getX(), vector.getY(), vector.getZ());
	}

	public Value3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public Value3d normalize() {
		double length = Math.sqrt(NumberConversions.square(x) + NumberConversions.square(y) + NumberConversions.square(z));

		x /= length;
		y /= length;
		z /= length;

		return this;
	}

//	public Vector toBukkitVector() {
//		return new Vector(this.getX(), this.getY(), this.getZ());
//	}

	@Override
	public Value3d clone() {
		return new Value3d(this);
	}
}
