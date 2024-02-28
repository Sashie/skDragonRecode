package me.sashie.skdragon.skript.expressions.special;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.util.DynamicLocation;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Name("New Vector")
@Description({"Constructs a new vector either from a location/entity"})
@Examples({"new vector from location of player",
		"new vector from player"})
public class ExprNewVector extends SimpleExpression<Vector> {

	static {
		Skript.registerExpression(
				ExprNewVector.class,
				Vector.class,
				ExpressionType.SIMPLE,
				"[new] vector from %object%"
		);
	}

	private Expression<Object> entLoc;

	public @NotNull Class<? extends Vector> getReturnType() {
		return Vector.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
		this.entLoc = (Expression<Object>) exprs[0];
		return true;
	}

	@Override
	public @NotNull String toString(@Nullable Event e, boolean debug) {
		return "new vector from " + this.entLoc.toString(e, debug);
	}

	@Override
	protected Vector @NotNull [] get(@NotNull Event e) {
		Object input = this.entLoc.getSingle(e);
		if (input == null)
			return null;
		DynamicLocation location = DynamicLocation.init(input);
		return new Vector[]{new Vector(location.getX(), location.getY(), location.getZ())};
	}
}