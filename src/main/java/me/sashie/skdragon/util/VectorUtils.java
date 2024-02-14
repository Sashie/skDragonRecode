package me.sashie.skdragon.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public final class VectorUtils {

    public static Vector add(Vector vector, double x, double y, double z) {
        return vector.add(new Vector(x, y, z));
    }

    public static Vector add(Vector vector, Location add) {
        return add(vector, add.getX(), add.getY(), add.getZ());
    }

    public static Vector subtract(Vector vector, Location subtract) {
        return vector.subtract(new Vector(subtract.getX(), subtract.getY(), subtract.getZ()));
    }

    public static Vector rotateAroundAxisX(Vector v, double angle) {
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;
        //return new Vector(v.getX(), y, z);//v.setY(y).setZ(z);
        return v.setY(y).setZ(z);
        //return v.clone();
    }

    public static Vector rotateAroundAxisY(Vector v, double angle) {
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        //return new Vector(x, v.getY(), z);//v.setX(x).setZ(z);
        return v.setX(x).setZ(z);
        //return v.clone();
    }

    public static Vector rotateAroundAxisY(Vector v, float angle) {
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        //return new Vector(x, v.getY(), z);//v.setX(x).setZ(z);
        return v.setX(x).setZ(z);
        //return v.clone();
    }

    public static Vector rotateAroundAxisZ(Vector v, double angle) {
        double x, y, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos - v.getY() * sin;
        y = v.getX() * sin + v.getY() * cos;
        //return new Vector(x, y, v.getZ());//v.setX(x).setY(y);
        return v.setX(x).setY(y);
        //return v.clone();
    }

    public static Vector rotateVector(Vector v, double angleX, double angleY, double angleZ) {
        // double x = v.getX(), y = v.getY(), z = v.getZ();
        // double cosX = Math.cos(angleX), sinX = Math.sin(angleX), cosY =
        // Math.cos(angleY), sinY = Math.sin(angleY), cosZ = Math.cos(angleZ),
        // sinZ = Math.sin(angleZ);
        // double nx, ny, nz;
        // nx = (x * cosY + z * sinY) * (x * cosZ - y * sinZ);
        // ny = (y * cosX - z * sinX) * (x * sinZ + y * cosZ);
        // nz = (y * sinX + z * cosX) * (-x * sinY + z * cosY);
        // return v.setX(nx).setY(ny).setZ(nz);
        // Having some strange behavior up there.. Have to look in it later. TODO
        rotateAroundAxisX(v, angleX);
        rotateAroundAxisY(v, angleY);
        rotateAroundAxisZ(v, angleZ);
        return v;
    }

    public static Vector getBackVector(final Location location) {
        final float newZ = (float) (location.getZ() + 1.0 * Math.sin(Math.toRadians(location.getYaw() + 90.0f)));
        final float newX = (float) (location.getX() + 1.0 * Math.cos(Math.toRadians(location.getYaw() + 90.0f)));
        return new Vector(newX - location.getX(), 0.0, newZ - location.getZ());
    }

}
