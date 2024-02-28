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
import me.sashie.skdragon.effects.ParticleEffect;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;

@Name("All particle effect type names")
@Description({"Gets a list of all particle effect type names used in skDragon ie. circle, dot, sphere"})
@Examples({"all particle effect names"})
public class ExprAllEffectTypeNames extends SimpleExpression<String> {

	static {
		Skript.registerExpression(
				ExprAllEffectTypeNames.class,
				String.class,
				ExpressionType.SIMPLE,
				"[all] particle effect names"
		);
	}

	@Override
	public boolean init(Expression<?> @NotNull [] e, int i, @NotNull Kleenean k, SkriptParser.@NotNull ParseResult p) {
		return true;
	}

	@Override
	protected String @NotNull [] get(@NotNull Event e) {
		return Arrays.stream(ParticleEffect.values())
				.map(effect -> effect.toString().toLowerCase().replace("_", " "))
				.toArray(String[]::new);
	}

	@Override
	public @NotNull String toString(@Nullable Event e, boolean b) {
		return "all particle effect names";
	}

	@Override
	public @NotNull Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}
}
