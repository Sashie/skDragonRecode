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
import me.sashie.skdragon.effects.properties.IStyle;
import me.sashie.skdragon.effects.special.Wings;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Sashie on 12/12/2016.
 */

@Name("Particles - Effect style")
@Description({"Get or set the style number of an effect returns 1 if that effect only has one style"})
@Examples({"set style of effect \"uniqueID\" to 2"})
public class ExprEffectStyle extends CustomEffectPropertyExpression<Number> {

	static {
		register(ExprEffectStyle.class, Number.class, "style");
	}

	@Override
	public Number getPropertyValue(EffectData effect) {
		if (effect instanceof IStyle) {
			return ((IStyle) effect).getStyleProperty().getStyle();
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		if (effect instanceof IStyle) {
			Number b = (Number) (delta[0]);
			if (effect instanceof Wings) {
				((Wings) effect).setStyle(b.intValue());
			} else {
				((IStyle) effect).getStyleProperty().setStyle(b.intValue());
			}
		}
	}

	@Override
	public @NotNull Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public String getPropertyName() {
		return "style";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.STYLE;
	}
}
