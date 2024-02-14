package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.project.effects.EffectData;
import me.sashie.skdragon.project.effects.EffectProperty;
import me.sashie.skdragon.project.effects.properties.IExtra;
import me.sashie.skdragon.skript.expressions.CustomArrayPropertyExpression;
import org.jetbrains.annotations.NotNull;


@Name("Particles - Effect extra value")
@Description({"Get or set the extra value of an effect if it has this property. To learn more please see the individual particle effects!"})
@Examples({"set extra value of effect \"uniqueID\" to 20"})
public class ExprEffectExtra extends CustomArrayPropertyExpression<Number> {

	static {
		register(
				ExprEffectExtra.class,
				Number.class,
				"extra [value]"
		);
	}

	@Override
	public Object getPropertyArray(EffectData effect) {
		if (effect instanceof IExtra) {
			return ((IExtra) effect).getExtraProperty().getArray();
		}
		return null;
	}

	@Override
	public Number getPropertyValue(int propertyNumber, EffectData effect) {
		if (effect instanceof IExtra) {
			return ((IExtra) effect).getExtraProperty().getValue(propertyNumber);
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, int propertyNumber, Number value) {
		if (effect instanceof IExtra) {
			((IExtra) effect).getExtraProperty().setValue(propertyNumber, value.floatValue());
		}
	}

	@Override
	public @NotNull Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public String getPropertyName() {
		return "extra";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.EXTRA;
	}

}
