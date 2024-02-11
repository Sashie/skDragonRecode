package me.sashie.skdragon.skript.expressions;

import ch.njol.skript.classes.Converter;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.registrations.Converters;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class CustomPropertyExpression<F, T> extends SimpleExpression<T> {
	private Expression<? extends F> expr;

	protected final void setExpr(Expression<? extends F> expr) {
		this.expr = expr;
	}

	public final Expression<? extends F> getExpr() {
		return this.expr;
	}

	protected T @NotNull [] get(@NotNull Event e) {
		return this.get(e, this.expr.getArray(e));
	}

	public final T @NotNull [] getAll(@NotNull Event e) {
		return this.get(e, this.expr.getAll(e));
	}

	protected abstract T[] get(Event arg0, F[] arg1);

	protected T[] get(F[] source, Converter<? super F, ? extends T> converter) {
		assert source != null;

		assert converter != null;

		return Converters.convertUnsafe(source, this.getReturnType(), converter);
	}

	//removed 'final' from original class
	public boolean isSingle() {
		return this.expr.isSingle();
	}

	public final boolean getAnd() {
		return this.expr.getAnd();
	}

	public @NotNull Expression<? extends T> simplify() {
		this.expr = this.expr.simplify();
		return this;
	}
}