package thaumicindustries2core.model.integrations;

import nemexlib.api.integrations.ACompat;
import nemexlib.api.thaumcraft.API;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.RecipeHelpers;

public class ThaumicBasesCompat extends ACompat {

    public ThaumicBasesCompat(String mod, String tab) {
        super(mod, tab);
    }

    @Override
    public void loadIntegration() {
        if (Config.woolToStringMerge) deleteWoolToStringCrucibleRecipe();
    }

    protected void deleteWoolToStringCrucibleRecipe() {
        String key1 = "TB.AdvancedEntropy", key2 = "TB.MasterEntropy";
        RecipeHelpers.crucibleRemover.removeItem(new ItemStack(Items.string), key1);
        API.removePage(tab, key1, 2);
        RecipeHelpers.crucibleRemover.removeItem(new ItemStack(Items.string), key2);
        API.removePage(tab, key2, 2);
    }
}
