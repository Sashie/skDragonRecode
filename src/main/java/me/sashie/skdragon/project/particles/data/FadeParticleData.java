package me.sashie.skdragon.project.particles.data;

import me.sashie.skdragon.project.util.DynamicList;
import org.bukkit.Color;

public class FadeParticleData extends ColoredParticleData {

	//public Color color = Color.fromRGB(0, 0, 0);
	//public ObjectList<Color> colors = new ObjectList<Color>(new Color(0, 0, 0));
	public DynamicList<Color> fadeColors = new DynamicList<Color>(Color.fromRGB(0, 0, 0));
	
}
