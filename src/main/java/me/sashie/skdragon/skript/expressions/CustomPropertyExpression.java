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

package me.sashie.skdragon.skript.expressions;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Converter;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.registrations.Converters;

public abstract class CustomPropertyExpression<F, T> extends SimpleExpression<T> {
	private Expression<? extends F> expr;

	protected final void setExpr(Expression<? extends F> expr) {
		this.expr = expr;
	}

	public final Expression<? extends F> getExpr() {
		return this.expr;
	}

	protected T[] get(Event e) {
		return this.get(e, this.expr.getArray(e));
	}

	public final T[] getAll(Event e) {
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

	public Expression<? extends T> simplify() {
		this.expr = this.expr.simplify();
		return this;
	}
}