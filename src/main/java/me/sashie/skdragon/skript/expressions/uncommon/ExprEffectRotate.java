package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.IRotation;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Auto rotation")
@Description({"If an effect uses this it makes the effect rotate using the rotation vector expression"})
@Examples({"set auto rotation of effect \"uniqueID\" to true"})
public class ExprEffectRotate extends CustomEffectPropertyExpression<Boolean> {

	static {
		register(ExprEffectRotate.class, Boolean.class, "auto rotation");
	}

	@Override
	public Boolean getPropertyValue(EffectData effect) {
		if (effect instanceof IRotation) {
			return ((IRotation) effect).getRotateProperty().isRotating();
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		if (effect instanceof IRotation) {
			Boolean b = (Boolean) delta[0];
			((IRotation) effect).getRotateProperty().setRotating(b);
		}
	}

	@Override
	public @NotNull Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public String getPropertyName() {
		return "auto rotation";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.AUTO_ROTATE;
	}
}
