package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.IOrbitStep;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Effect orbit axis")
@Description({"Makes an effect orbit around an axis"})
@Examples({"set orbit axis of effect \"uniqueID\" to vector 20, 50 and 100"})
public class ExprEffectOrbitVelocity extends CustomEffectPropertyExpression<Vector> {

	static {
		register(ExprEffectOrbitVelocity.class, Vector.class, "orbit axis");
	}

	@Override
	public Vector getPropertyValue(EffectData effect) {
		if (effect instanceof IOrbitStep) {
			return ((IOrbitStep) effect).getOrbitStepProperty().getAxisAsVector();
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		if (effect instanceof IOrbitStep) {
			Vector v = (Vector) (delta[0]);
			((IOrbitStep) effect).getOrbitStepProperty().setAxis(v);
		}
	}

	@Override
	public @NotNull Class<? extends Vector> getReturnType() {
		return Vector.class;
	}

	@Override
	public String getPropertyName() {
		return "orbit axis";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.ORBIT;
	}
}
