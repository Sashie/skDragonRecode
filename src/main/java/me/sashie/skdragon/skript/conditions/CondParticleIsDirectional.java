/*
	This file is part of skDragon - A Skript addon
      
	Copyright (C) 2016 - 2021  Sashie

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package me.sashie.skdragon.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.particles.ParticleProperty;
import me.sashie.skdragon.util.Utils;
import org.bukkit.Particle;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Sashie on 12/12/2016.
 */
@Name("Particles - Is Directional")
@Description("Checks whether a particle is directional.")
@Examples({"particle redstone is directional"})
public class CondParticleIsDirectional extends BaseConditions {

    static {
        Skript.registerCondition(
                CondParticleIsDirectional.class,
                "particle %particle% is directional",
                "particle %particle% is not directional"
        );
    }

    private Expression<Particle> exprParticle;

    @Override
    public boolean initCondition(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        exprParticle = (Expression<Particle>) exprs[0];
        return true;
    }

    @Override
    public boolean checkCondition(@NotNull Event e) {
        Particle particle = Utils.verifyVar(e, exprParticle, null);
        return ParticleProperty.DIRECTIONAL.hasProperty(particle);
    }

    @Override
    public String toStringCondition(Event e, boolean debug) {
        return exprParticle.toString(e, debug) + " particle " + (isNegated() ? "does not require" : "requires") + " material";
    }

}
