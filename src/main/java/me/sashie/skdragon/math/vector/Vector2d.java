package me.sashie.skdragon.math.vector;

import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

public class Vector2d implements Cloneable {

    public static final Vector2d ZERO = new Vector2d(0, 0);
    public static final Vector2d ONE = new Vector2d(1, 1);

    double x, y;

    public Vector2d(Vector vector) {
        this.x = vector.getX();
        this.y = vector.getY();
    }

    public Vector2d() {
    }

    public Vector2d(Vector2d vector) {
        this.x = vector.getX();
        this.y = vector.getY();
    }

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector2d add(Vector2d vec) {
        x += vec.x;
        y += vec.y;
        return this;
    }

    public Vector2d subtract(Vector2d vec) {
        x -= vec.x;
        y -= vec.y;
        return this;
    }

    public Vector2d multiply(Vector2d vec) {
        x *= vec.x;
        y *= vec.y;
        return this;
    }

    public Vector2d multiply(double m) {
        x *= m;
        y *= m;
        return this;
    }

    public Vector2d divide(Vector2d vec) {
        x /= vec.x;
        y /= vec.y;
        return this;
    }

    public double length() {
        return Math.sqrt(NumberConversions.square(x) + NumberConversions.square(y));
    }

    public Vector2d normalize() {
        double length = length();

        x /= length;
        y /= length;

        return this;
    }

    @Override
    public Vector2d clone() {
        return new Vector2d(this);
    }
}