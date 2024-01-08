package me.sashie.skdragon.util.color;

import java.awt.Color;

import org.bukkit.util.Vector;

public class ColoredVector extends Vector {

	private Color color;
	
	public ColoredVector(Vector vector, Color color) {
		super(vector.getX(), vector.getY(), vector.getZ());
		setColor(color);
	}

	public ColoredVector(ColoredVector coloredVector) {
		this(coloredVector.getPoint(), coloredVector.getColor());
	}
	
	public static ColoredVector createFromRGB(Vector vector, Vector color) {
		int r = (int) color.getX();
		int g = (int) color.getY();
		int b = (int) color.getZ();
		return new ColoredVector(vector, new Color(r,g,b));
	}
	
	public static ColoredVector createFromHSB(Vector vector, Vector color) {
		float h = (float) color.getX();
		float s = (float) color.getY();
		float b = (float) color.getZ();
		return new ColoredVector(vector, Color.getHSBColor(h, s, b));
	}
	
	public Vector getPoint() {
		return this;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Vector getVectorFromColor() {
		return new Vector(color.getRed(), color.getGreen(), color.getBlue());
	}
	
	public void setPoint(Vector point) {
		this.setX(point.getX()).setY(point.getY()).setZ(point.getZ());
	}
	
	public void setColor(Color color) {
		this.color = new Color(color.getRGB());
	}
	
	public void setColor(Vector color) {
		int r = (int) color.getX();
		int g = (int) color.getY();
		int b = (int) color.getZ();
		this.color = new Color(r,g,b);
	}
	
	@Override
	public ColoredVector clone() {
		return new ColoredVector(this);
	}
}
