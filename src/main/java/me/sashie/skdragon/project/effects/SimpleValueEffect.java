package me.sashie.skdragon.project.effects;

import me.sashie.skdragon.project.effects.properties.ExtraProperty;
import me.sashie.skdragon.project.effects.properties.IExtra;
import me.sashie.skdragon.project.util.EffectUtils;

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
