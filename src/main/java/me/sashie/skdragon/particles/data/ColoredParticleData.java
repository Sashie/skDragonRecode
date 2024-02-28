package me.sashie.skdragon.particles.data;

import me.sashie.skdragon.util.DynamicList;
import org.bukkit.Color;

public class ColoredParticleData extends ParticleData {

	public DynamicList<Color> colors = new DynamicList<Color>(Color.fromRGB(0, 0, 0));

	public void setColor(Color color) {
		this.colors.set(0, color);
	}

	public void setColor(int r, int g, int b) {
		this.colors.set(0, Color.fromRGB(r, g, b));
	}

	public void setColor(int index, Color color) {
		this.colors.set(index, color);
	}

	public void setColor(int index, int r, int g, int b) {
		this.colors.set(index, Color.fromRGB(r, g, b));
	}

	public Color getColor(int index) {
		return colors.get(index);
	}
}
