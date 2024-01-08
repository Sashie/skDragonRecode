package me.sashie.skdragon.math.vector;

import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

public class Vector3d implements Cloneable {

    double x, y, z;

    public Vector3d(Vector vector) {
        this.x = vector.getX();
        this.y = vector.getY();
        this.z = vector.getZ();
    }

    public Vector3d() {
    }

    public Vector3d(Vector3d vector) {
        this.x = vector.getX();
        this.y = vector.getY();
        this.z = vector.getZ();
    }

    public Vector3d(double x, double y, double z) {
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

    public Vector3d add(Vector3d vec) {
        x += vec.x;
        y += vec.y;
        z += vec.z;
        return this;
    }

    public Vector3d subtract(Vector3d vec) {
        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
        return this;
    }

    public Vector3d multiply(Vector3d vec) {
        x *= vec.x;
        y *= vec.y;
        z *= vec.z;
        return this;
    }

    public Vector3d multiply(double m) {
        x *= m;
        y *= m;
        z *= m;
        return this;
    }

    public Vector3d divide(Vector3d vec) {
        x /= vec.x;
        y /= vec.y;
        z /= vec.z;
        return this;
    }

    public double length() {
        return Math.sqrt(NumberConversions.square(x) + NumberConversions.square(y) + NumberConversions.square(z));
    }

    public Vector3d normalize() {
        double length = length();

        x /= length;
        y /= length;
        z /= length;

        return this;
    }

	public Vector toBukkitVector() {
		return new Vector(x, y, z);
	}

    public Vector toBukkitVector(Vector vector) {
        vector.setX(x);
        vector.setY(y);
        vector.setZ(z);
        return vector;
    }

    @Override
    public Vector3d clone() {
        return new Vector3d(this);
    }
}
