package thaumicindustries2core.model.patches;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.event.world.WorldEvent;

import java.util.ArrayList;

import static thaumicindustries2core.ThaumicIndustries2Core.logger;

public class BuggedRecipePatch implements IPatch {

    protected boolean alreadyRun = false;

    @SubscribeEvent
    public void apply(WorldEvent.Load event) {
        if (!alreadyRun) {
            // Finding the bugged recipes
            int cpt = 0;
            ArrayList<ShapedRecipes> buggyRecipes = new ArrayList<>(2);
            for (Object recipe : CraftingManager.getInstance().getRecipeList())
                if (recipe instanceof ShapedRecipes)
                    try {
                        Item output = ((ShapedRecipes) recipe).getRecipeOutput().getItem();
                        if (output == null) throw new NullPointerException();
                    } catch (NullPointerException exception) {
                        buggyRecipes.add((ShapedRecipes) recipe);
                    }
            for (ShapedRecipes recipe : buggyRecipes) {
                CraftingManager.getInstance().getRecipeList().remove(recipe);
                cpt++;
            }
            logger.info("Removed", cpt, "buggy recipes with null Item output !");
            this.alreadyRun = true;
        }
    }
}
