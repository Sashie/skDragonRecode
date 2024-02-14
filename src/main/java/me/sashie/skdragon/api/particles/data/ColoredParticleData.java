package me.sashie.skdragon.api.particles.data;

import me.sashie.skdragon.api.util.DynamicList;
import org.bukkit.Color;

public class ColoredParticleData extends ParticleData {

	public DynamicList<Color> colors = new DynamicList<Color>(Color.fromRGB(0, 0, 0));

	public void setColor(Color color) {
		this.colors = new DynamicList<Color>(color);
	}

	public void setColor(Color[] color) {
		DynamicList<Color> colors = new DynamicList<>();
		for (int i = 0; i < color.length - 1; i++) {
			colors.add(color[i]);
		}
		this.colors = new DynamicList<>(color);
	}

	public void setColor(int r, int g, int b) {
		this.colors = new DynamicList<>(Color.fromRGB(r, g, b));
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
