package me.sashie.skdragon.skript.expressions.special;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import java.awt.Color;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.util.Vector;

/**
 * @author bi0qaw
 */
@Name("RGB Color")
@Description("Gets or sets the r, g or b value of a color")
@Examples({""})
@Since("1.0")
public class ExprColorRGB extends SimplePropertyExpression<Color, Number> {
	static {
		Skript.registerExpression(ExprColorRGB.class, Number.class, ExpressionType.PROPERTY, "(0¦r[ed]|1¦g[reen]|2¦b[lue]) value of %dragoncolor%");
	}

	private final static String[] values = {"red", "green", "blue"};

	private int mark;

	@Override
	public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
		super.init(exprs, matchedPattern, isDelayed, parseResult);
		mark = parseResult.mark;
		return true;
	}

	@Override
	public Integer convert(final Color c) {
		return mark == 0 ? c.getRed() : mark == 1 ? c.getGreen() : c.getBlue();
	}

	@Override
	protected String getPropertyName() {
		return "the " + values[mark] + " value";
	}

	@Override
	public Class<Number> getReturnType() {
		return Number.class;
	}

	@Override
	@SuppressWarnings("null")
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE) && getExpr().isSingle() && Changer.ChangerUtils.acceptsChange(getExpr(), Changer.ChangeMode.SET, Color.class))
			return new Class[] { Number.class };
		return null;
	}

	@Override
	public void change(final Event e, final @Nullable Object[] delta, final Changer.ChangeMode mode) throws UnsupportedOperationException {
		assert delta != null;
		Color c = getExpr().getSingle(e);
		if (c == null)
			return;
		int n = ((Number) delta[0]).intValue();
		switch (mode) {
			case REMOVE:
				n = -n;
				//$FALL-THROUGH$
			case ADD:
				if (mark == 0) {
					c = new Color(c.getRed() + n, c.getGreen(), c.getBlue());
				} else if (mark == 1) {
					c = new Color(c.getRed(), c.getGreen() + n, c.getBlue());
				} else {
					c = new Color(c.getRed(), c.getGreen(), c.getBlue() + n);
				}
				getExpr().change(e, new Color[] {c}, Changer.ChangeMode.SET);
				break;
			case SET:
				if (mark == 0) {
					c = new Color(n, c.getGreen(), c.getBlue());
				} else if (mark == 1) {
					c = new Color(c.getRed(), n, c.getBlue());
				} else {
					c = new Color(c.getRed(), c.getGreen(), n);
				}
				getExpr().change(e, new Color[] {c}, Changer.ChangeMode.SET);
				break;
			case DELETE:
			case REMOVE_ALL:
			case RESET:
				assert false;
		}
	}
}