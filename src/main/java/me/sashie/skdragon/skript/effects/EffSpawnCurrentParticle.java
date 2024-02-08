package me.sashie.skdragon.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.skript.sections.ParticleSection;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

/**
 * Created by Sashie on 12/12/2024.
 */

@Name("Particles - Draw last created/current particle")
@Description({"Draws the last created particle"})
@Examples({ "create particle of flame:",
            "   set count of particle to 100",
            "   set offset of particle to vector 1, 1 and 1",
            "draw current particle at player"})
public class EffSpawnCurrentParticle extends Effect {

    static {
        Skript.registerEffect(EffSpawnCurrentParticle.class,
                "(draw|send) [the] ([last] created|current) particle (at|to) %objects%");
    }

    private Expression<Object> location;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        location = (Expression<Object>) exprs[0];
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (ParticleSection.getParticle() == null)
            return;

        for (Object locationObj : location.getArray(e)) {
            DynamicLocation l = null;
            if (locationObj instanceof Entity) {
                l = ObjectPoolManager.getDynamicLocationPool().acquire((Entity) locationObj);
            } else if (locationObj instanceof Location) {
                l = ObjectPoolManager.getDynamicLocationPool().acquire((Location) locationObj);
            }
            if (l != null) {
                ParticleSection.getParticle().sendParticles(l);
                ObjectPoolManager.getDynamicLocationPool().release(l);
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "draw current particle at " + location.toString(e, debug);
    }

}
