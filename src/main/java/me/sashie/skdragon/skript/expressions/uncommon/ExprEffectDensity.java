package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.project.effects.EffectData;
import me.sashie.skdragon.project.effects.EffectProperty;
import me.sashie.skdragon.project.effects.properties.IDensity;
import me.sashie.skdragon.skript.expressions.CustomArrayPropertyExpression;
import org.jetbrains.annotations.NotNull;


@Name("Particles - Effect density")
@Description({"Get or set the density of an effect if it has this property"})
@Examples({"set 1st density of effect \"uniqueID\" to 20"})
public class ExprEffectDensity extends CustomArrayPropertyExpression<Number> {

	static {
		register(
				ExprEffectDensity.class,
				Number.class,
				"density"
		);
	}

	@Override
	public Object getPropertyArray(EffectData effect) {
		if (effect instanceof IDensity) {
			return ((IDensity) effect).getDensityProperty().getArray();
		}
		return null;
	}

	@Override
	public Number getPropertyValue(int propertyNumber, EffectData effect) {
		if (effect instanceof IDensity) {
			return ((IDensity) effect).getDensityProperty().getDensity(propertyNumber);
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, int propertyNumber, Number value) {
		if (effect instanceof IDensity) {
			((IDensity) effect).getDensityProperty().setDensity(propertyNumber, value.intValue());
		}
	}

	@Override
	public @NotNull Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public String getPropertyName() {
		return "density";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.DENSITY;
	}
}
