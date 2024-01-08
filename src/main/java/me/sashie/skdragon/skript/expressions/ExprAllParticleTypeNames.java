package me.sashie.skdragon.skript.expressions;

import java.util.ArrayList;

import javax.annotation.Nullable;

import me.sashie.skdragon.util.ParticleUtil;
import org.bukkit.Particle;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

/**
 * Edited by Sashie on 1/20/2017
 */

@Name("All particle names")
@Description({"Gets a list of all particle names used in skDragon"})
@Examples({	"all particle names"})
public class ExprAllParticleTypeNames extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprAllParticleTypeNames.class, String.class, ExpressionType.SIMPLE,
				"[all] particle names");
	}

    @Override
    @Nullable
    protected String[] get(Event e) {
        return ParticleUtil.getAllNames();
    }

    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean k, SkriptParser.ParseResult p) {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "particle names";
    }
}
