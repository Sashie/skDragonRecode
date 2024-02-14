package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.api.effects.EffectData;
import me.sashie.skdragon.api.effects.EffectProperty;
import me.sashie.skdragon.api.effects.properties.IVelocity;
import me.sashie.skdragon.api.effects.properties.VelocityProperty;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;


@Name("Particles - Effect rotation vector")
@Description({"Rotates specific effects using a vector, requires auto rotation expression for the effect be set to true"})
@Examples({"set rotation vector of effect \"uniqueID\" to vector 20, 50 and 100"})
public class ExprEffectRotationalVelocity extends CustomEffectPropertyExpression<Vector> {

	static {
		register(ExprEffectRotationalVelocity.class, Vector.class, "rotation vector");
	}

	@Override
	public Vector getPropertyValue(EffectData effect) {
		if (effect instanceof IVelocity) {
			VelocityProperty property = ((IVelocity) effect).getVelocityProperty();
			double x = property.getAngularVelocityX();
			double y = property.getAngularVelocityX();
			double z = property.getAngularVelocityX();
			return new Vector(x, y, z);
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		if (effect instanceof IVelocity) {
			Vector v = (Vector) (delta[0]);
			((IVelocity) effect).getVelocityProperty().setAngularVelocity(v.getX(), v.getY(), v.getZ());
		}
	}

	@Override
	public @NotNull Class<? extends Vector> getReturnType() {
		return Vector.class;
	}

	@Override
	public String getPropertyName() {
		return "rotation vector";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.XYZ_ANGULAR_VELOCITY;
	}
}
