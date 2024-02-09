package me.sashie.skdragon.skript.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class BaseConditions extends Condition {

    public abstract boolean initCondition(Expression<?> @NotNull [] expressions, int matchedPattern, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult);

    public abstract boolean checkCondition(@NotNull Event event);

    public abstract String toStringCondition(Event event, boolean debug);

    @Override
    public boolean init(Expression<?> @NotNull [] expressions, int matchedPattern, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        setNegated(matchedPattern == 1);
        return initCondition(expressions, matchedPattern, kleenean, parseResult);
    }

    @Override
    public boolean check(@NotNull Event event) {
        return isNegated() != checkCondition(event);
    }

    @Override
    public @NotNull String toString(Event event, boolean debug) {
        return toStringCondition(event, debug);
    }

}
