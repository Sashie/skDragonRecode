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

package me.sashie.skdragon.skript.expressions.particle;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.expressions.CustomParticlePropertyExpression;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Sashie on 12/12/2016.
 */
@Name("Particles - Offset vector")
@Description({"This property works best when the amount of particles is more than 1, it makes the particle offset randomly between the start location and the offset value"})
//[the] %number%(st|nd|rd|th) particle rgb value of [the] [particle] effect[s] %strings%
@Examples({"set the 1st particle offset of the particle effect \"uniqueID\" to {_v}"})
public class ExprParticleOffset extends CustomParticlePropertyExpression<Vector> {

    static {
        register(ExprParticleOffset.class, Vector.class, "offset [vector]");
    }

    @Override
    public Vector getParticle(ParticleBuilder<?> p) {
        return p.getParticleData().getOffset();
    }

    @Override
    public void setParticle(ParticleBuilder<?> p, Object[] delta) {
        Vector v = (Vector) (delta[0]);
        p.getParticleData().setOffset(v);
    }

    @Override
    public @NotNull Class<? extends Vector> getReturnType() {
        return Vector.class;
    }

    @Override
    protected String getPropertyName() {
        return "offset vector";
    }
}
