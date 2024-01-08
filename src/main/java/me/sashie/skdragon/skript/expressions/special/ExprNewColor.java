/*
	This file is part of skDragon - A Skript addon
      
	Copyright (C) 2016 - 2023  Sashie

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

package me.sashie.skdragon.skript.expressions.special;

import java.awt.Color;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.util.color.ColorUtils;

@Name("New Color")
@Description({"Constructs a new color"})
@Examples({	"custom color using rgb 255, 0, 0"})
public class ExprNewColor extends SimpleExpression<Color> {

	static {
		Skript.registerExpression(ExprNewColor.class, Color.class, ExpressionType.SIMPLE,
/*0*/				"[a] custom colo[u]r (using|from) ((1¦rgb|2¦(hsb|hsv)) %-number%, %-number%, %-number%|3¦hex %-string%)",
/*1*/				"[a] random [%-number%] colo[u]r[s] [(using|from) ((1¦rgb|2¦(hsb|hsv)) %-number%, %-number%, %-number%|3¦hex %-string%|4¦%-dragoncolor%)]",
				
/*2*/				"(1¦darken[ed]|2¦brighten[ed]) %dragoncolor% [by %-number% percent]",

/*3*/				"([a] gradient|colo[u]rs) between %dragoncolors% [with %-number% steps]",

/*4*/				"(1¦rainbow|2¦heat|3¦jet) (gradient|colo[u]rs) [with %-number% steps]",

/*5*/				"[a|%-number%] complementary colo[u]rs of %dragoncolor%");

	}

	private int mark;
	private int matchedPattern;
	private boolean isSingle = true;
	
	private Expression<Number> r, g, b;
	private Expression<String> h;
	private Expression<Color> c;
	private Expression<Number> n;
	
	public Class<? extends Color> getReturnType() {
		return Color.class;
	}

	@Override
	public boolean isSingle() {
		return isSingle;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		this.matchedPattern = matchedPattern;
		if (matchedPattern == 2) {
			c = (Expression<Color>) exprs[0];
			n = (Expression<Number>) exprs[1];
		} else if (matchedPattern == 3) {
			isSingle = false;
			c = (Expression<Color>) exprs[0];
			n = (Expression<Number>) exprs[1];
		} else if (matchedPattern == 4) {
			isSingle = false;
			n = (Expression<Number>) exprs[0];
		} else if (matchedPattern == 5) {
			isSingle = false;
			c = (Expression<Color>) exprs[0];
		} else {
			this.mark = parseResult.mark;
			int i = 0;
			if (matchedPattern == 1) {
				isSingle = false;
				n = (Expression<Number>) exprs[0];
				i = 1;
			}
			if (mark == 1 || mark == 2) {
				r = (Expression<Number>) exprs[0 + i];
				g = (Expression<Number>) exprs[1 + i];
				b = (Expression<Number>) exprs[2 + i];
			} else if (mark == 3) {
				h = (Expression<String>) exprs[0 + i];
			} else if (mark == 4) {
				c = (Expression<Color>) exprs[0 + i];
			}
		}
		
		return true;
	}

	@Override	//TODO get matched pattern and stuff
	public String toString(@Nullable Event e, boolean debug) {
		return "new custom color " + r.toString(e, debug) + ", " + g.toString(e, debug) + ", " + b.toString(e, debug);		//TODO
	}

	@Override
	@Nullable
	protected Color[] get(Event e) {
		if (matchedPattern == 2) {
			Color color;
			double percent = this.n == null ? 0.7 : this.n.getSingle(e).intValue() / 100;
			if (mark == 1)
				color = ColorUtils.darken(this.c.getSingle(e), percent);
			else
				color = ColorUtils.brighten(this.c.getSingle(e), percent);
			return new Color[] { color };
		} else if (matchedPattern == 3) {
			return ColorUtils.generateMultiGradient(this.c.getArray(e), this.n == null ? 255 : this.n.getSingle(e).intValue());
		} else if (matchedPattern == 4) {
			Color[] colors = null;
			int value = this.n == null ? 255 : this.n.getSingle(e).intValue();
			if (mark == 1)
				colors = ColorUtils.rainbow(value);
			else if (mark == 2)
				colors = ColorUtils.heat(value);
			else if (mark == 3)
				colors = ColorUtils.jet(value);
			return colors;
		} else if (matchedPattern == 5) {
			Color color = this.c.getSingle(e);
			return ColorUtils.complementaryColors(color);
		} else {
			Color color = null;
			if (mark == 1)
				color = new Color(this.r.getSingle(e).intValue(), this.g.getSingle(e).intValue(), this.b.getSingle(e).intValue());
			else if (mark == 2)
				color = Color.getHSBColor(this.r.getSingle(e).floatValue(), this.g.getSingle(e).floatValue(), this.b.getSingle(e).floatValue());
			else if (mark == 3)
				color = Color.decode(this.h.getSingle(e));
			else if (mark == 4)
				color = this.c.getSingle(e);
			if (matchedPattern == 1) {
				if (this.n == null)
					color = ColorUtils.generateRandomColor(color);
				else
					return ColorUtils.generateRandomColors(color, this.n.getSingle(e).intValue());
			}

			return new Color[] { color };
		}
	}
}