package thaumicindustries2core.model.integrations;

import nemexlib.api.integrations.ACompat;
import nemexlib.api.thaumcraft.API;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.RecipeHelpers;

public class ThaumaturgicalKnowledgeCompat extends ACompat {

    public ThaumaturgicalKnowledgeCompat(String mod, String tab) {
        super(mod, tab);
    }

    @Override
    public void loadIntegration() {
        if (Config.woolToStringMerge) deleteWoolToStringCrucibleRecipe();
        if (Config.redstoneDupeMerge) deleteRestoneDupeCrucibleRecipe();
    }

    protected void deleteWoolToStringCrucibleRecipe() {
        String key = "ENT";
        RecipeHelpers.crucibleRemover.removeItem(new ItemStack(Items.string), key);
        API.removePage(tab, key, 5);
    }
    protected void deleteRestoneDupeCrucibleRecipe() {
        String key = "DUPE";
        RecipeHelpers.crucibleRemover.removeItem(new ItemStack(Items.redstone), key);
        API.removePage(tab, key, 5);
    }
}
