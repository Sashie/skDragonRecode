package me.sashie.skdragon.particles.data;

import java.awt.Color;

import me.sashie.skdragon.util.DynamicList;

public class ColoredParticleData extends ParticleData {

	public DynamicList<Color> colors = new DynamicList<Color>(new Color(0, 0, 0));

	public void setColor(Color color) {
		this.colors = new DynamicList<Color>(color);
	}

	public void setColor(Color[] color) {
		DynamicList<Color> colors = new DynamicList<Color>();
		for (int i = 0; i < color.length - 1; i++) {
			colors.add(color[i]);
		}
		this.colors = new DynamicList<Color>(color);
	}

	public void setColor(int r, int g, int b) {
		this.colors = new DynamicList<Color>(new Color(r, g, b));
	}

	public void setColor(int index, Color color) {
		this.colors.set(index, color);
	}

	public void setColor(int index, int r, int g, int b) {
		this.colors.set(index, new Color(r, g, b));
	}

	public org.bukkit.Color getBukkitColor(int index) {
		Color c = colors.get(index);
		return org.bukkit.Color.fromRGB(c.getRed(), c.getGreen(), c.getBlue());
	}
}
