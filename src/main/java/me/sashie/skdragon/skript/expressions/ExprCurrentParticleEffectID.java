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
import me.sashie.skdragon.skript.sections.ParticleEffectSection;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Current/last created particle effect ID")
@Description({"Gets the last created particle effect ID for use in particle effect sections"})
@Examples({""})
public class ExprCurrentParticleEffectID extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprCurrentParticleEffectID.class, String.class, ExpressionType.SIMPLE,
				"[the] ([last] created|current) particle effect id");
	}

	@Override
	public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parser) {
		return true;
	}

	@Override
	protected String @NotNull [] get(@NotNull Event e) {
		return new String[]{ParticleEffectSection.getID()};
	}

	@Override
	public @NotNull Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public @NotNull String toString(Event e, boolean debug) {
		return "the last created particle effect id";
	}

	@Override
	public boolean isSingle() {
		return true;
	}
}
