package me.sashie.skdragon.particles.data;

import me.sashie.skdragon.util.DynamicList;
import org.bukkit.Color;

public class FadeParticleData extends ColoredParticleData {

	public DynamicList<Color> fadeColors = new DynamicList<Color>(Color.fromRGB(0, 0, 0));
	
}
