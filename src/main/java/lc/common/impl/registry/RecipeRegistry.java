package lc.common.impl.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import lc.api.components.IRecipeRegistry;
import lc.api.components.RecipeType;
import lc.api.defs.IRecipeDefinition;
import lc.common.LCLog;
import lc.core.LCRuntime;

/**
 * Recipe registry implementation.
 * 
 * @author AfterLifeLochie
 * 
 */
public class RecipeRegistry implements IRecipeRegistry {

	/** Pool of all known definitions. */
	private final Map<String, IRecipeDefinition> definitionPool;

	public RecipeRegistry() {
		definitionPool = new HashMap<String, IRecipeDefinition>();
	}

	@Override
	public void addRecipe(IRecipeDefinition definition) {
		if (definitionPool.containsKey(definition.getName().toLowerCase()))
			throw new RuntimeException("Attempt to overwrite existing definition " + definition.getName());
		definitionPool.put(definition.getName().toLowerCase(), definition);
	}

	@Override
	public IRecipeDefinition getRecipe(String name) {
		return definitionPool.get(name.toLowerCase());
	}

	public void init(LCRuntime runtime, FMLInitializationEvent event) {
		for (Entry<String, IRecipeDefinition> entry : definitionPool.entrySet()) {
			IRecipeDefinition definition = entry.getValue();
			RecipeType type = definition.getType();
			if (type == RecipeType.SHAPELESS) {
				Map<Integer, ItemStack> in = definition.getInputStacks();
				Map<Integer, ItemStack> out = definition.getOutputStacks();
				if (out.size() != 1 || !out.containsKey(0))
					LCLog.fatal("Bad recipe %s: expected 1 output stack for shapeless, got %s.", definition.getName(),
							out.size());
				CraftingManager.getInstance().addShapelessRecipe(out.get(0), in.values().toArray());
			} else if (type == RecipeType.SHAPED) {
				Map<Integer, ItemStack> in = definition.getInputStacks();
				Map<Integer, ItemStack> out = definition.getOutputStacks();
				if (out.size() != 1 || !out.containsKey(0))
					LCLog.fatal("Bad recipe %s: expected 1 output stack for shapeless, got %s.", definition.getName(),
							out.size());
				// TODO: The shape of Map<Integer, ItemStack> in depends on the
				// grid of items.
			} else if (type == RecipeType.SMELTING) {
				Map<Integer, ItemStack> in = definition.getInputStacks();
				if (in.size() != 1 || !in.containsKey(0))
					LCLog.fatal("Bad recipe %s: expected 1 input stack for smelting, got %s.", definition.getName(),
							in.size());
				Map<Integer, ItemStack> out = definition.getOutputStacks();
				if (out.size() != 1 || !out.containsKey(0))
					LCLog.fatal("Bad recipe %s: expected 1 output stack for smelting, got %s.", definition.getName(),
							out.size());
				FurnaceRecipes.smelting().func_151394_a(in.get(0), out.get(0), 0.0f);
			}
		}
	}

}