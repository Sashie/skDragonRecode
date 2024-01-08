package me.sashie.skdragon.skript.expressions.special;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.util.Vector;

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

/**
 * Created by Sashie on 12/12/2016.
 */

@Name("New Vector")
@Description({"Constructs a new vector either from a location/entity"})
@Examples({	"new vector from location of player",
	"new vector from player"})
public class ExprNewVector extends SimpleExpression<Vector> {

	static {
		Skript.registerExpression(ExprNewVector.class, Vector.class, ExpressionType.SIMPLE,
				"new vector from %object%");
	}

	private Expression<Object> entLoc;
	private int matchedPattern;

	public Class<? extends Vector> getReturnType() {
		return Vector.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		this.entLoc = (Expression<Object>) exprs[0];
		this.matchedPattern = matchedPattern;

		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "new vector from " + this.entLoc.toString(e, debug);
	}

	@Override
	@Nullable
	protected Vector[] get(Event e) {
		Object input = this.entLoc.getSingle(e);
		if (input == null)
			return null;
		DynamicLocation location = DynamicLocation.init(input);
		return new Vector[]{new Vector(location.getX(), location.getY(), location.getZ())};
	}
}