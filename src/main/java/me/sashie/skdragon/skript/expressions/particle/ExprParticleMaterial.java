package me.sashie.skdragon.skript.expressions.particle;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.project.particles.MaterialParticle;
import me.sashie.skdragon.project.particles.ParticleBuilder;
import me.sashie.skdragon.skript.expressions.CustomParticlePropertyExpression;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


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
