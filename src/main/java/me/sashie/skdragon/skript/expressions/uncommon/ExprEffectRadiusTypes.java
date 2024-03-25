package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.PropertyAPI;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.IRadius;
import me.sashie.skdragon.skript.expressions.CustomArrayPropertyExpression;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Effect radius types")
@Description({"Sets or gets the radius of most effects",
		"Also used for parametric equation effect types as well(can use negative numbers)"})
@Examples({"set radius of effect \"uniqueID\" to 2"})
public class ExprEffectRadiusTypes extends CustomArrayPropertyExpression<Boolean> {

	static {
		register(ExprEffectRadiusTypes.class, Boolean.class, "radius (1¦(oscillation|swing)|2¦repeat)");
	}

	@Override
	public Object getPropertyArray(EffectData effect) {
		if (effect instanceof IRadius) {
			return ((IRadius) effect).getRadiusProperty().getArray();
		}
		return null;
	}

	@Override
	public Boolean getPropertyValue(int propertyNumber, EffectData effect) {
		if (effect instanceof IRadius) {
			switch (mark) {
				case 1:
					return ((IRadius) effect).getRadiusProperty().isOscillating(propertyNumber);
				case 2:
					return ((IRadius) effect).getRadiusProperty().isRepeating(propertyNumber);
				default:
					return null;
			}
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, int propertyNumber, Object delta) {
		Boolean value = (Boolean) delta;
		if (effect instanceof IRadius) {
			switch (mark) {
				case 1:
					((IRadius) effect).getRadiusProperty().setOscillation(propertyNumber, value.booleanValue());
					break;
				case 2:
					((IRadius) effect).getRadiusProperty().setRepeating(propertyNumber, value.booleanValue());
					break;
			}
		}
	}

	@Override
	public @NotNull Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public String getPropertyName() {
		return "radius types";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.RADIUS;
	}
}
