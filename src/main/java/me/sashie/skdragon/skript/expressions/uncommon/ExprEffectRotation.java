package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.PropertyAPI;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.IAxis;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Effect rotation axis")
@Description({"Rotates specific effects using a vector"})
@Examples({"set rotation axis of effect \"uniqueID\" to {_v}"})
public class ExprEffectRotation extends CustomEffectPropertyExpression<Vector> {

	static {
		register(ExprEffectRotation.class, Vector.class, "rotation axis");
	}

	@Override
	public Vector getPropertyValue(EffectData effect) {
		if (effect instanceof IAxis) {
			return ((IAxis) effect).getAxisProperty().getAxisAsVector();
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		PropertyAPI.setAxis(effect, (Vector) (delta[0]));
	}

	@Override
	public @NotNull Class<? extends Vector> getReturnType() {
		return Vector.class;
	}

	@Override
	public String getPropertyName() {
		return "rotation axis";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.AXIS;
	}
}
