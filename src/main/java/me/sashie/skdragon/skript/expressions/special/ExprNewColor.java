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


import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Color;
import ch.njol.skript.util.ColorRGB;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.util.Utils;
import me.sashie.skdragon.util.color.ColorUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Name("New Color")
@Description({"Constructs a new color"})
@Examples({"custom color using rgb 255, 0, 0",
		"gradient between custom color using rgb 255, 0, 0 and custom color using rgb 255, 255, 0 with 100 steps"})
public class ExprNewColor extends SimpleExpression<Color> {

	static {
		Skript.registerExpression(
				ExprNewColor.class,
				Color.class,
				ExpressionType.SIMPLE,
				"[a] custom colo[u]r (using|from) ((1¦rgb|2¦(hsb|hsv)) %-number%, %-number%, %-number%|3¦hex %-string%)",
				"[a] random [%-number%] colo[u]r[s] [(using|from) ((1¦rgb|2¦(hsb|hsv)) %-number%, %-number%, %-number%|3¦hex %-string%|4¦%-color%)]",
				"(1¦darken[ed]|2¦brighten[ed]) %color% [by %-number% percent]",
				"([a] gradient|colo[u]rs) between %colors% [with %-number% steps]",
				"(1¦rainbow|2¦heat|3¦jet) (gradient|colo[u]rs) [with %-number% steps]",
				"complementary colo[u]rs of %color%"
		);

	}

	private int mark;
	private int matchedPattern;
	private boolean isSingle = true;

	private Expression<Number> r, g, b;
	private Expression<String> h;
	private Expression<Color> exprColor;
	private Expression<Number> n;

	public @NotNull Class<? extends Color> getReturnType() {
		return Color.class;
	}

	@Override
	public boolean isSingle() {
		return isSingle;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
		this.matchedPattern = matchedPattern;
		if (matchedPattern == 2) {
			exprColor = (Expression<Color>) exprs[0];
			n = (Expression<Number>) exprs[1];
		} else if (matchedPattern == 3) {
			isSingle = false;
			exprColor = (Expression<Color>) exprs[0];
			n = (Expression<Number>) exprs[1];
		} else if (matchedPattern == 4) {
			isSingle = false;
			n = (Expression<Number>) exprs[0];
		} else if (matchedPattern == 5) {
			isSingle = false;
			exprColor = (Expression<Color>) exprs[0];
		} else {
			this.mark = parseResult.mark;
			int i = 0;
			if (matchedPattern == 1) {
				isSingle = false;
				n = (Expression<Number>) exprs[0];
				i = 1;
			}
			if (mark == 1 || mark == 2) {
				r = (Expression<Number>) exprs[i];
				g = (Expression<Number>) exprs[1 + i];
				b = (Expression<Number>) exprs[2 + i];
			} else if (mark == 3) {
				h = (Expression<String>) exprs[i];
			} else if (mark == 4) {
				exprColor = (Expression<Color>) exprs[i];
			}
		}

		return true;
	}

	public @NotNull String toString(@Nullable Event e, boolean debug) {
		StringBuilder sb = new StringBuilder();

		if (matchedPattern == 2) {
			switch (mark) {
				case 1:
					sb.append("darken ");
					break;
				case 2:
					sb.append("brighten ");
					break;
			}
			sb.append(exprColor.toString(e, debug));
			if (n != null) {
				sb.append(" by ");
				sb.append(n.toString(e, debug));
				sb.append(" percent");
			}
		} else if (matchedPattern == 3) {
			sb.append("gradient between ");
			sb.append(exprColor.toString(e, debug));
			if (n != null) {
				sb.append(" with ");
				sb.append(n.toString(e, debug));
				sb.append(" steps");
			}
		} else if (matchedPattern == 4) {
			switch (mark) {
				case 1:
					sb.append("rainbow");
					break;
				case 2:
					sb.append("heat");
					break;
				case 3:
					sb.append("jet");
					break;
			}
			sb.append(" gradient");
			if (n != null) {
				sb.append(" with ");
				sb.append(n.toString(e, debug));
				sb.append(" steps");
			}
		} else if (matchedPattern == 5) {
			sb.append("complementary colors of ");
			sb.append(exprColor.toString(e, debug));
		} else {
			if (matchedPattern == 0) {
				sb.append("custom color");
			} else if (matchedPattern == 1) {
				sb.append("random");
				if (n != null) {
					sb.append(" ");
					sb.append(n.toString(e, debug));
					sb.append(" colors");
				} else {
					sb.append(" color");
				}
			}
			if (mark == 1 || mark == 2) {
				switch (mark) {
					case 1:
						sb.append(" using rgb ");
						break;
					case 2:
						sb.append(" using hsb/hsv ");
						break;
				}
				if (r != null && g != null && b != null) {
					sb.append(r.toString(e, debug));
					sb.append(g.toString(e, debug));
					sb.append(b.toString(e, debug));
				}
			} else if (mark == 3) {
				sb.append(" using hex ");
				sb.append(h.toString(e, debug));
			} else if (mark == 4) {
				sb.append(" using ");
				sb.append(exprColor.toString(e, debug));
			}
		}
		return sb.toString();
	}

	@Override
	protected Color @NotNull [] get(@NotNull Event e) {
		if (matchedPattern == 2) {
			Color color = Utils.verifyVar(e, exprColor, null);
			if (color == null) return new Color[0];

			double percent = (double) Utils.verifyVar(e, this.n, 70).intValue() / 100;
			if (mark == 1)
				color = ColorUtils.darken(color, percent);
			else
				color = ColorUtils.brighten(color, percent);
			
			return new Color[]{color};
		} else if (matchedPattern == 3) {
			return ColorUtils.generateMultiGradient(this.exprColor.getArray(e), Utils.verifyVar(e, this.n, 255).intValue());
		} else if (matchedPattern == 4) {
			Color[] colors = null;

			int value = Utils.verifyVar(e, this.n, 255).intValue();
			if (mark == 1)
				colors = ColorUtils.rainbow(value);
			else if (mark == 2)
				colors = ColorUtils.heat(value);
			else if (mark == 3)
				colors = ColorUtils.jet(value);

			if (colors == null) return new Color[0];

			return colors;
		} else if (matchedPattern == 5) {
			Color color = this.exprColor.getSingle(e);

			if (color == null) return new Color[0];

			return ColorUtils.complementaryColors(color);
		} else {
			Color color = null;
			if (mark == 1) {
				Number r = this.r.getSingle(e);
				Number g = this.g.getSingle(e);
				Number b = this.b.getSingle(e);

				if (r == null || g == null || b == null) return new Color[0];

				color = new ColorRGB(r.intValue(), g.intValue(), b.intValue());
			} else if (mark == 2) {
				Number h = Utils.verifyVar(e, this.r, null);
				Number s = Utils.verifyVar(e, this.g, null);
				Number b = Utils.verifyVar(e, this.b, null);

				if (r == null || g == null || b == null) return new Color[0];

				java.awt.Color c = java.awt.Color.getHSBColor(h.floatValue(), s.floatValue(), b.floatValue());
				color = new ColorRGB(c.getRed(), c.getGreen(), c.getBlue());
			} else if (mark == 3) {
				String hex = this.h.getSingle(e);

				if (hex == null) return new Color[0];

				java.awt.Color c = java.awt.Color.decode(hex);
				color = new ColorRGB(c.getRed(), c.getGreen(), c.getBlue());
			} else if (mark == 4) {
				color = this.exprColor.getSingle(e);
				if (color == null) return new Color[0];
			}
			if (matchedPattern == 1) {
				if (this.n == null) {
					color = ColorUtils.generateRandomColor(color);
				} else {
					Number n = this.n.getSingle(e);

					if (n == null) return new Color[0];

					return ColorUtils.generateRandomColors(color, n.intValue());
				}
			}

			return new Color[]{color};
		}
	}
}