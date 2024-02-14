package me.sashie.skdragon.skript.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.api.effects.EffectData;
import me.sashie.skdragon.api.effects.EffectProperty;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;


@Name("Particles - Vector displacement")
@Description({"Sets or gets the displacement of a given particle effect from its location"})
@Examples({"set displacement vector of effect \"uniqueID\" to vector 0, 1 and 0"})
public class ExprEffectDisplacement extends CustomEffectPropertyExpression<Vector> {

	static {
		register(
				ExprEffectDisplacement.class,
				Vector.class,
				"displacement [vector]"
		);
	}

	@Override
	public Vector getPropertyValue(EffectData effect) {
		return new Vector(effect.getDisplacement().getX(), effect.getDisplacement().getY(), effect.getDisplacement().getZ());
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		Vector v = (Vector) (delta[0]);
		effect.setDisplacement(v);
	}

	@Override
	public @NotNull Class<? extends Vector> getReturnType() {
		return Vector.class;
	}

	@Override
	public String getPropertyName() {
		return "displacement vector";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.DISPLACEMENT;
	}
}
