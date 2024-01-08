package me.sashie.skdragon.effects;

import me.sashie.skdragon.effects.properties.ExtraProperty;
import me.sashie.skdragon.effects.properties.IExtra;
import me.sashie.skdragon.util.EffectUtils;

/**
 * Created by Sashie on 8/9/2021.
 */

public abstract class SimpleValueEffect extends SimpleEffect implements IExtra {

	private ExtraProperty extraProperty;

	public SimpleValueEffect() {
		extraProperty = new ExtraProperty();
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.EXTRA);
	}

	@Override
	public ExtraProperty getExtraProperty() {
		return extraProperty;
	}

}
