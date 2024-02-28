package me.sashie.skdragon.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.util.ParticleUtil;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Name("All particle names")
@Description({"Gets a list of all particle names used in skDragon"})
@Examples({"all particle names"})
public class ExprAllParticleTypeNames extends SimpleExpression<String> {

	static {
		Skript.registerExpression(
				ExprAllParticleTypeNames.class,
				String.class,
				ExpressionType.SIMPLE,
				"[all] particle names"
		);
	}

	@Override
	protected String @NotNull [] get(@NotNull Event e) {
		return ParticleUtil.getAllNames();
	}

	@Override
	public boolean init(Expression<?> @NotNull [] e, int i, @NotNull Kleenean k, SkriptParser.@NotNull ParseResult p) {
		return true;
	}

	@Override
	public @NotNull Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public @NotNull String toString(@Nullable Event e, boolean b) {
		return "particle names";
	}
}
