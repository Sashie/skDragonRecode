package me.sashie.skdragon.particles.data;

import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.util.DynamicList;
import org.bukkit.Color;

public class ColoredParticleData extends ParticleData<ColoredParticle> {

	private DynamicList<Color> colors = new DynamicList<Color>(Color.fromRGB(0, 0, 0));

	public ColoredParticleData(ColoredParticle particle) {
		super(particle);
	}

	public ColoredParticle addColor(Color color) {
		this.colors.add(color);
		return returnType;
	}

	public ColoredParticle addColor(int r, int g, int b) {
		this.colors.add(Color.fromRGB(r, g, b));
		return returnType;
	}

	public ColoredParticle addColor(int index, Color color) {
		this.colors.add(index, color);
		return returnType;
	}

	public ColoredParticle addColor(int index, int r, int g, int b) {
		this.colors.add(index, Color.fromRGB(r, g, b));
		return returnType;
	}

	public ColoredParticle setColor(Color color) {
		this.colors.set(0, color);
		//particle.getParticleData().colors.set(0, color);
		return returnType;
	}

	public ColoredParticle setColor(int r, int g, int b) {
		this.colors.set(0, Color.fromRGB(r, g, b));
		return returnType;
	}

	public ColoredParticle setColor(int index, Color color) {
		this.colors.set(index, color);
		return returnType;
	}

	public ColoredParticle setColor(int index, int r, int g, int b) {
		this.colors.set(index, Color.fromRGB(r, g, b));
		return returnType;
	}

	public Color getColor(int index) {
		return colors.get(index);
	}

	public DynamicList<Color> getColors() {
		return colors;
	}

	public ColoredParticle setColors(DynamicList<Color> colors) {
		this.colors = colors;
		return returnType;
	}
}
