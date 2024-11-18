package me.sashie.skdragon.math;

import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

/**
 * Simple class to store three values
 */
public class Value3d implements Cloneable {

	public static final Value3d ZERO = new Value3d(0, 0, 0);
	public static final Value3d ONE = new Value3d(1, 1, 1);

	float x, y, z;

	public Value3d(Vector vector) {
		this((float) vector.getX(), (float) vector.getY(), (float) vector.getZ());
	}

	public Value3d() {
	}

	public Value3d(Value3d vector) {
		this(vector.getX(), vector.getY(), vector.getZ());
	}

	public Value3d(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public Value3d normalize() {
		float length = (float) Math.sqrt(NumberConversions.square(x) + NumberConversions.square(y) + NumberConversions.square(z));

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

	public boolean isSame(Value3d obj) {
		return (this.x == obj.getX() && this.y == obj.getY() && this.z == obj.getZ());
	}

	public boolean isZero() {
		return isSame(ZERO);
	}
}
