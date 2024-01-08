package me.sashie.skdragon.effects;

import org.bukkit.entity.Player;

import me.sashie.skdragon.effects.properties.IRadius;
import me.sashie.skdragon.effects.properties.RadiusProperty;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;

/**
 * Created by Sashie on 8/9/2021.
 */

public abstract class SimpleRadiusEffect extends SimpleEffect implements IRadius {

	private RadiusProperty radiusProperty;
	
	public SimpleRadiusEffect() {
		radiusProperty = new RadiusProperty();
//		this.initRadius(1.5f);
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.RADIUS);
	}

	@Override
	public RadiusProperty getRadiusProperty() {
		return radiusProperty;
	}

}
