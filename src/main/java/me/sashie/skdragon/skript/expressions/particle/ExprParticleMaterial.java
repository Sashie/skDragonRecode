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

package me.sashie.skdragon.skript.expressions.particle;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.particles.MaterialParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.expressions.CustomParticlePropertyExpression;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Sashie on 12/12/2016.
 */
@Name("Particles - Material type")
@Description({"Certain particles allow you to change the material type they use ie. blockcrack etc.."})
@Examples({"set particle material of effect \"uniqueID\" to dirt block"})
public class ExprParticleMaterial extends CustomParticlePropertyExpression<ItemStack> {

	static {
		register(ExprParticleMaterial.class, ItemStack.class, "material");
	}

	@Override
	public ItemStack getParticle(ParticleBuilder<?> p) {
		if (p instanceof MaterialParticle) {
			return new ItemStack(((MaterialParticle) p).getParticleData().material);
		}
		return null;
	}

	@Override
	public void setParticle(ParticleBuilder<?> p, Object[] delta) {
		if (p instanceof MaterialParticle) {
			ItemStack i = (ItemStack) (delta[0]);
			((MaterialParticle) p).getParticleData().material = i.getType();
		}
	}

	@Override
	public @NotNull Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	@Override
	protected String getPropertyName() {
		return "material";
	}

}
