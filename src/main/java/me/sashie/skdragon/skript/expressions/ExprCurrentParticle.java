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
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.sections.ParticleSection;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Sashie on 12/12/2016.
 */

@Name("Particles - Current/last created particle")
@Description({"Gets the last created particle for use in particle sections"})
@Examples({""})
public class ExprCurrentParticle extends SimpleExpression<ParticleBuilder> {

    static {
        Skript.registerExpression(
                ExprCurrentParticle.class,
                ParticleBuilder.class,
                ExpressionType.SIMPLE,
                "[the] ([last] created|current) particle"
        );
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parser) {
        return true;
    }

    @Override
    protected ParticleBuilder @NotNull [] get(@NotNull Event e) {
        return new ParticleBuilder[]{ParticleSection.getParticle()};
    }

    @Override
    public @NotNull Class<? extends ParticleBuilder> getReturnType() {
        return ParticleBuilder.class;
    }

    @Override
    public @NotNull String toString(Event e, boolean debug) {
        return "the last created particle";
    }

    @Override
    public boolean isSingle() {
        return true;
    }
}
