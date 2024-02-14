package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.project.effects.EffectData;
import me.sashie.skdragon.project.effects.EffectProperty;
import me.sashie.skdragon.project.effects.properties.IRotation;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.jetbrains.annotations.NotNull;


@Name("Particles - Auto yaw")
@Description({"Make certain particle effects turn with the player or turn to face the player(depending on the effect type)"})
@Examples({"set auto face of effect \"uniqueID\" to true"})
public class ExprEffectRotateWithPlayer extends CustomEffectPropertyExpression<Boolean> {

	static {
		register(ExprEffectRotateWithPlayer.class, Boolean.class, "auto face");
	}

	@Override
	public Boolean getPropertyValue(EffectData effect) {
		if (effect instanceof IRotation) {
			return ((IRotation) effect).getRotateProperty().isRotatingWithPlayer();
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		if (effect instanceof IRotation) {
			Boolean b = (Boolean) delta[0];
			((IRotation) effect).getRotateProperty().setRotatingWithPlayer(b);
		}
	}

	@Override
	public @NotNull Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public String getPropertyName() {
		return "auto face";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.AUTO_FACE;
	}
}
