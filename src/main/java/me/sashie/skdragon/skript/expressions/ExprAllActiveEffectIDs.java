package me.sashie.skdragon.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.project.EffectAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Set;


@Name("Particles - All active effects")
@Description({"Lists all currently running effects"})
@Examples({"set {list::*} to all active particle effects"})
public class ExprAllActiveEffectIDs extends SimpleExpression<String> {

	static {
		Skript.registerExpression(
				ExprAllActiveEffectIDs.class,
				String.class,
				ExpressionType.SIMPLE,
				"[all] active particle effects"
		);
	}

	@Override
	public boolean init(Expression<?> @NotNull [] args, int arg1, @NotNull Kleenean arg2, @NotNull ParseResult arg3) {
		return true;
	}

	@Override
	protected String @NotNull [] get(@NotNull Event arg0) {
		Set<String> effectIDs = EffectAPI.ALL_EFFECTS.keySet();
		return effectIDs.toArray(new String[0]);
	}

	@Override
	public @NotNull String toString(@Nullable Event arg0, boolean arg1) {
		return "all active particle effects";
	}

	public @NotNull Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}
}