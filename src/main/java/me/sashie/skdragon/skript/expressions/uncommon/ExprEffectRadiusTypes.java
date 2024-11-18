package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.IRadius;
import me.sashie.skdragon.skript.expressions.CustomArrayPropertyExpression;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Effect radius types")
@Description({"Sets or gets the radius types that are active for the effects use them",
		"Oscillation/swing makes the effects radius \"swing\" back and forth between the start and end radius values",
		"Repeat makes the radius reset to the start radius when it reaches the end or vise versa in reverse",
		"Both of these options require that start and end radius properties be set",
		"Setting either oscillation or repeat will disable the other if it is enabled"})
@Examples({"set radius oscillation of effect \"uniqueID\" to true",
		"set radius repeat of effect \"uniqueID\" to true"})
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
		return "radius " + switch (mark) {
			case 1 -> "oscillation/swing";
			case 2 -> "repeat";
			default -> "";
		};
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.RADIUS;
	}
}
