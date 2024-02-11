package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.IRadius;
import me.sashie.skdragon.skript.expressions.CustomArrayPropertyExpression;
import org.jetbrains.annotations.NotNull;



@Name("Particles - Effect radius")
@Description({"Sets or gets the radius of most effects",
		"Also used for parametric equation effect types as well(can use negative numbers)"})
@Examples({"set radius of effect \"uniqueID\" to 2"})
public class ExprEffectRadius extends CustomArrayPropertyExpression<Number> {

	static {
		register(ExprEffectRadius.class, Number.class, "radius");
	}

	@Override
	public Object getPropertyArray(EffectData effect) {
		if (effect instanceof IRadius) {
			return ((IRadius) effect).getRadiusProperty().getArray();
		}
		return null;
	}

	@Override
	public Number getPropertyValue(int propertyNumber, EffectData effect) {
		if (effect instanceof IRadius) {
			return ((IRadius) effect).getRadiusProperty().getRadius(propertyNumber);
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, int propertyNumber, Number value) {
		if (effect instanceof IRadius) {
			((IRadius) effect).getRadiusProperty().setRadius(propertyNumber, value.floatValue());
		}
	}

	@Override
	public @NotNull Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public String getPropertyName() {
		return "radius";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.RADIUS;
	}
}
