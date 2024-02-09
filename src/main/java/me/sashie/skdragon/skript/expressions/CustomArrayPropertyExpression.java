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
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.sashie.skdragon.EffectAPI;
import me.sashie.skdragon.SkDragonRecode;
import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.skript.sections.EffectSection;
import me.sashie.skdragon.skript.sections.ParticleEffectSection;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sashie on 10/30/2017.
 */
public abstract class CustomArrayPropertyExpression<T> extends CustomPropertyExpression<String, T> implements Converter<String, T> {

    public static <T> void register(final Class<? extends Expression<T>> c, final Class<T> type, final String property) {
        Skript.registerExpression(
                c,
                type,
                ExpressionType.PROPERTY,
                "[the] [%-number%(st|nd|rd|th)] " + property + " of [the] [particle] effect %string%",
                "[particle] effect %string%'[s] [%-number%(st|nd|rd|th)] " + property,
                "[%-number%(st|nd|rd|th)] " + property + " of [the] [particle] effect"
        );
    }

    protected boolean scope = false;
    private List<String> failedEffects;
    private Expression<Number> propertyNumberExpr;
    private int propertyNumber;
    protected int mark;
    protected SkriptNode skriptNode;

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
        if (matchedPattern == 2) {
            if (EffectSection.isCurrentSection(ParticleEffectSection.class)) {
                this.scope = true;
                this.setPropertyExpr((Expression<Number>) exprs[0]);
            } else {
                return false;
            }
        } else if (matchedPattern == 0) {
            this.setPropertyExpr((Expression<Number>) exprs[0]);
            this.setExpr((Expression<? extends String>) exprs[1]);
        } else if (matchedPattern == 1) {
            this.setExpr((Expression<? extends String>) exprs[0]);
            this.setPropertyExpr((Expression<Number>) exprs[1]);
        }
        this.mark = parseResult.mark;
        skriptNode = new SkriptNode(SkriptLogger.getNode());
        return true;
    }

    /*
        public int getMark() {
            return mark;
        }
    */
    protected final void setPropertyExpr(Expression<Number> expr) {
        this.propertyNumberExpr = expr;
    }

    public final Expression<Number> getPropertyExpr() {
        return this.propertyNumberExpr;
    }

    protected final int getPropertyNumber() {
        return this.propertyNumber;
    }

    /**
     * Property name
     */
    public abstract String getPropertyName();

    /**
     * To be overridden
     *
     * @return
     */
    protected EffectProperty getEffectProperty() {
        return null;
    }

    public abstract Object getPropertyArray(EffectData effect);

    /**
     * Place code to get a particle property
     *
     * @param effect
     * @return
     */
    public abstract T getPropertyValue(int propertyNumber, EffectData effect);

    /**
     * Place code to set a particle property
     *
     * @param value Skript input value for property expressions
     */
    public abstract void setPropertyValue(EffectData effect, int propertyNumber, Number value);

    @Override
    @Nullable
    public T convert(String id) {
        if (id == null)
            return null;
        if (EffectAPI.ALL_EFFECTS.containsKey(id)) {
            EffectData effect = EffectAPI.get(id, skriptNode);

            synchronized (effect) {
                Object property = getPropertyArray(effect);
                int length = Array.getLength(property);
                if (propertyNumber > length) {
                    SkDragonRecode.warn("The " + /*'" + data.getName() + "'*/"effect with id " + id + " does not support more than " + length + " " + getEffectProperty().getName().toLowerCase() + " propert" + (length > 1 ? "ies" : "y"), skriptNode);
                    return null;
                }

                return getPropertyValue(propertyNumber, effect);
            }
        } else {
            return null;
        }
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        failedEffects = new ArrayList<String>();
        propertyNumber = 1;
        if (propertyNumberExpr != null && propertyNumberExpr.getSingle(e) != null)
            propertyNumber = propertyNumberExpr.getSingle(e).intValue();

        if (scope) {
            set(ParticleEffectSection.getID(), delta);
        } else {
            String[] effectIDs = (String[]) getExpr().getAll(e);

            if (effectIDs == null)
                return;

            for (String id : effectIDs) {
                if (!EffectAPI.ALL_EFFECTS.containsKey(id)) {
                    failedEffects.add(id);
                    continue;
                }
                set(id, delta);
            }
            if (!failedEffects.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (String s : failedEffects) {
                    sb.append(s);
                    sb.append(", ");
                }
                SkDragonRecode.warn("One or more particle effects didn't exist! (" + sb.toString() + ")", skriptNode);
            }
        }
    }

    private void set(String id, Object[] delta) {
        EffectData effect = EffectAPI.get(id, skriptNode);

        synchronized (effect) {
            Object property = getPropertyArray(effect);
            if (property != null && property.getClass().isArray()) {
                int length = Array.getLength(property);
                if (propertyNumber > length) {
                    SkDragonRecode.warn("The " + /*'" + effect.getName() + "'*/"effect with id " + id + " does not support more than " + length + " " + getEffectProperty().getName() + " propert" + (length > 1 ? "ies" : "y"), skriptNode);
                    return;
                }

                Number value = (Number) delta[0];
                setPropertyValue(effect, propertyNumber, value);
            }
        }
    }

    @Override
    protected T[] get(Event e, String[] source) {
        propertyNumber = 1;
        if (propertyNumberExpr != null && propertyNumberExpr.getSingle(e) != null)
            propertyNumber = propertyNumberExpr.getSingle(e).intValue();

        return super.get(source, this);
    }

    @Override
    public Class<? extends T> @NotNull [] acceptChange(final Changer.@NotNull ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET)
            return CollectionUtils.array(getReturnType());
        return null;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "the " + (propertyNumberExpr == null ? "" : propertyNumberExpr.toString(e, debug) + "(st|nd|rd|th) ") + this.getPropertyName() + (this.getExpr() == null ? "" : " of effect " + (scope ? ParticleEffectSection.getID() : this.getExpr().toString(e, debug)));
    }
}