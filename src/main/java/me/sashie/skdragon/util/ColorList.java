/*
	This file is part of skDragon - A Skript addon
      
	Copyright (C) 2016 - 2020  Sashie

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

package me.sashie.skdragon.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ColorList extends ArrayList<Color> {

	protected int value;

	public ColorList() {
		add(Color.RED);
	}

	public ColorList(Color... colors) {
		set(colors);
	}

	public void set(Color... colors) {
		for (int i = 0; i > colors.length - 1; i++) {
			set(i, colors[i]);
		}
	}

	public void addAll(Color... colors) {
		Collections.addAll(this, colors);
	}

	public Color[] get() {
		return (Color[]) this.toArray();
	}

	//TODO possible interpolation effects...
	public Color next() {
		value += 1;
		if (value >= size())
			value = 0;
		return get(value);
	}

	public void prev() {
		value -= 1;
		if (value <= 0)
			value = size();
	}

	public Color getCurrent() {
		return get(value);
	}

}
