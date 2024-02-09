/*
	This file is part of skDragon - A Skript addon
      
	Copyright (C) 2016 - 2024  Sashie

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

package me.sashie.skdragon.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.sashie.skdragon.EffectAPI;
import me.sashie.skdragon.SkDragonRecode;
import me.sashie.skdragon.debug.ParticleException;
import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.sections.EffectSection;
import me.sashie.skdragon.skript.sections.ParticleEffectSection;
import me.sashie.skdragon.util.Utils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Created by Sashie on 10/30/2017.
 */

@Name("Particles - Particle type")
@Description({"Gets or sets the particles in an effect, some effects have more than one particle"})
@Examples({"set 3rd particle of effect \"uniqueID\" to flame"})
public class ExprEffectParticle extends PropertyExpression<String, ParticleBuilder> implements Converter<String, ParticleBuilder<?>> {

    static {
        Skript.registerExpression(
                ExprEffectParticle.class,
                ParticleBuilder.class,
                ExpressionType.PROPERTY,
                "[the] [%-number%(st|nd|rd|th)] particle of [the] [particle] effect %string%",
                "[particle] effect %string%'[s] [%-number%(st|nd|rd|th)] particle",
                "[%-number%(st|nd|rd|th)] particle of [the] [particle] effect"
        );
    }

    protected boolean scope = false;
    private Expression<Number> exprParticleNumber;
    private int particleNumber;
    private SkriptNode skriptNode;

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
        if (matchedPattern == 2) {
            if (EffectSection.isCurrentSection(ParticleEffectSection.class)) {
                this.scope = true;
                this.exprParticleNumber = (Expression<Number>) exprs[0];
            } else {
                return false;
            }
        } else if (matchedPattern == 1) {
            this.setExpr((Expression<? extends String>) exprs[0]);
            this.exprParticleNumber = (Expression<Number>) exprs[1];
        } else if (matchedPattern == 0) {
            this.exprParticleNumber = (Expression<Number>) exprs[0];
            this.setExpr((Expression<? extends String>) exprs[1]);
        }

        skriptNode = new SkriptNode(SkriptLogger.getNode());

        return true;
    }

    @Override
    @Nullable
    public ParticleBuilder<?> convert(String id) {
        EffectData effect = EffectAPI.get(id, skriptNode);
        if (effect == null) return null;

        if (particleNumber > effect.getParticleBuilders().length)
            throw new ParticleException("The effect with id " + id + " does not support more than " + effect.getParticleBuilders().length + " particle" + (effect.getParticleBuilders().length > 1 ? "s" : ""), skriptNode);

        synchronized (effect) {
            return effect.getParticleBuilders()[particleNumber - 1];
        }
    }

    @Override
    public void change(@NotNull Event e, Object @NotNull [] delta, Changer.@NotNull ChangeMode mode) {
        particleNumber = Utils.verifyVar(e, exprParticleNumber, 1).intValue();

        ParticleBuilder<?> builder = (ParticleBuilder<?>) delta[0];

        ArrayList<String> failedEffects = new ArrayList<>();
        if (scope) {
            set(ParticleEffectSection.getID(), builder);
        } else {
            String[] effectIds = Utils.verifyVars(e, getExpr(), null);
            if (effectIds == null) return;

            for (String id : effectIds) {
                if (!EffectAPI.ALL_EFFECTS.containsKey(id)) {
                    failedEffects.add(id);
                    continue;
                }
                set(id, builder);
            }

            if (!failedEffects.isEmpty()) {
                SkDragonRecode.warn("One or more particle effects didn't exist! (" + String.join(", ", failedEffects) + ")", skriptNode);
            }
        }
    }

    private void set(String id, ParticleBuilder<?> p) {
        EffectData effect = EffectAPI.get(id, skriptNode);
        if (effect == null) return;

        if (particleNumber > effect.getParticleBuilders().length)
            throw new ParticleException("The effect with id " + id + " does not support more than " + effect.getParticleBuilders().length + " particle" + (effect.getParticleBuilders().length > 1 ? "s" : ""), skriptNode);

        synchronized (effect) {
            effect.getParticleBuilders()[particleNumber - 1] = p;
        }
    }

    @Override
    protected ParticleBuilder<?> @NotNull [] get(@NotNull Event e, String @NotNull [] source) {
        particleNumber = Utils.verifyVar(e, exprParticleNumber, 1).intValue();
        if (scope) {
            throw new ParticleException("Incorrect use of syntax, can't get values inside a scope", skriptNode);
        }
        return super.get(source, this);
    }


    @Override
    public Class<?> @NotNull [] acceptChange(final Changer.@NotNull ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET)
            return CollectionUtils.array(ParticleBuilder.class);
        return CollectionUtils.array();
    }

    @Override
    public @NotNull Class<? extends ParticleBuilder> getReturnType() {
        return ParticleBuilder.class;
    }

    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "the particle of effect " + (scope ? ParticleEffectSection.getID() : this.getExpr().toString(e, debug));
    }
}