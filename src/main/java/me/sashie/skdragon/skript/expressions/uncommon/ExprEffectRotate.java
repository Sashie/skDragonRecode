package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.PropertyAPI;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.IVelocity;
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
		if (effect instanceof IVelocity) {
			return ((IVelocity) effect).getVelocityProperty().isRotating();
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		PropertyAPI.setRotate(effect, (Boolean) delta[0]);
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
		return EffectProperty.ROTATE_VELOCITY;
	}
}
