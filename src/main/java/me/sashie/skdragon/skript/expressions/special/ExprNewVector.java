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
@Description({"Constructs a new vector either from an x, y, z value or a location/entity"})
@Examples({	"new vector 0, 1, 0", 
	"new vector from location of player",
	"new vector from player"})
public class ExprNewVector extends SimpleExpression<Vector> {

	static {
		Skript.registerExpression(ExprNewVector.class, Vector.class, ExpressionType.SIMPLE,
				"new vector %number%, %number%, %number%",
				"new vector from %object%");
	}

	private Expression<Number> x, y, z;
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
		if(matchedPattern == 0) {
			this.x = (Expression<Number>) exprs[0];
			this.y = (Expression<Number>) exprs[1];
			this.z = (Expression<Number>) exprs[2];
		} else {
			this.entLoc = (Expression<Object>) exprs[0];
		}

		this.matchedPattern = matchedPattern;

		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "new vector " + (this.matchedPattern == 0
				? this.x.toString(e, debug) + ", " + this.y.toString(e, debug) + ", " + this.z.toString(e, debug)
				: "from " + this.entLoc.toString(e, debug));
	}

	@Override
	@Nullable
	protected Vector[] get(Event e) {
		if(this.matchedPattern == 0) {
			return new Vector[]{new Vector(this.x.getSingle(e).doubleValue(), this.y.getSingle(e).doubleValue(), this.z.getSingle(e).doubleValue())};
		} else {
			DynamicLocation location = DynamicLocation.init(this.entLoc.getSingle(e));			
			return new Vector[]{new Vector(location.getX(), location.getY(), location.getZ())};
		}
	}
}