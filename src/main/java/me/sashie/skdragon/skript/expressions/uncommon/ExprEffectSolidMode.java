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

package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.ISolid;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.jetbrains.annotations.NotNull;


/**
 * Created by Sashie on 12/12/2016.
 */
@Name("Particles - Effect solid mode")
@Description({"Some effects particles can either appear solid or moves along its path, such as the circle or other simple effects."})
@Examples({"set solid mode of effect \"uniqueID\" to true"})
public class ExprEffectSolidMode extends CustomEffectPropertyExpression<Boolean> {

    static {
        register(ExprEffectSolidMode.class, Boolean.class, "solid mode");
    }

    @Override
    public Boolean getPropertyValue(EffectData effect) {
        if (effect instanceof ISolid) {
            return ((ISolid) effect).getSolidProperty().isSolid();
        }
        return null;
    }

    @Override
    public void setPropertyValue(EffectData effect, Object[] delta) {
        if (effect instanceof ISolid) {
            Boolean b = (Boolean) delta[0];
            ((ISolid) effect).getSolidProperty().setSolid(b);
        }
    }

    @Override
    public @NotNull Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String getPropertyName() {
        return "solid mode";
    }

    @Override
    protected EffectProperty getEffectProperty() {
        return EffectProperty.SOLID_SHAPE;
    }
}