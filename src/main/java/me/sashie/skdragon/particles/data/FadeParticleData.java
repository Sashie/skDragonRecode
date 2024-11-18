package me.sashie.skdragon.particles.data;

import me.sashie.skdragon.particles.ColoredFadeParticle;
import me.sashie.skdragon.util.DynamicList;
import org.bukkit.Color;

public class FadeParticleData extends ParticleData<ColoredFadeParticle> {

	private DynamicList<Color> colors = new DynamicList<Color>(Color.fromRGB(0, 0, 0));
	private DynamicList<Color> fadeColors = new DynamicList<Color>(Color.fromRGB(0, 0, 0));

	public FadeParticleData(ColoredFadeParticle particle) {
		super(particle);
	}

	public ColoredFadeParticle addColor(Color color) {
		this.colors.add(color);
		return returnType;
	}

	public ColoredFadeParticle addColor(int r, int g, int b) {
		this.colors.add(Color.fromRGB(r, g, b));
		return returnType;
	}

	public ColoredFadeParticle addColor(int index, Color color) {
		this.colors.add(index, color);
		return returnType;
	}

	public ColoredFadeParticle addColor(int index, int r, int g, int b) {
		this.colors.add(index, Color.fromRGB(r, g, b));
		return returnType;
	}

	public ColoredFadeParticle setColor(Color color) {
		this.colors.set(0, color);
		//particle.getParticleData().colors.set(0, color);
		return returnType;
	}

	public ColoredFadeParticle setColor(int r, int g, int b) {
		this.colors.set(0, Color.fromRGB(r, g, b));
		return returnType;
	}

	public ColoredFadeParticle setColor(int index, Color color) {
		this.colors.set(index, color);
		return returnType;
	}

	public ColoredFadeParticle setColor(int index, int r, int g, int b) {
		this.colors.set(index, Color.fromRGB(r, g, b));
		return returnType;
	}

	public Color getColor(int index) {
		return colors.get(index);
	}

	public DynamicList<Color> getColors() {
		return colors;
	}

	public ColoredFadeParticle setColors(DynamicList<Color> colors) {
		this.colors = colors;
		return returnType;
	}

	public DynamicList<Color> getFadeColors() {
		return fadeColors;
	}

	public ColoredFadeParticle setFadeColors(DynamicList<Color> fadeColors) {
		this.fadeColors = fadeColors;
		return returnType;
	}
}
