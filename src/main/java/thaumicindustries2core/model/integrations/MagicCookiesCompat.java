package thaumicindustries2core.model.integrations;

import nemexlib.api.integrations.ACompat;
import nemexlib.api.thaumcraft.API;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.RecipeHelpers;

public class MagicCookiesCompat extends ACompat {

    public MagicCookiesCompat(String mod, String tab) {
        super(mod, tab);
    }

    @Override
    public void loadIntegration() {
        if (Config.woolToStringMerge) deleteWoolToStringCrucibleRecipe();
    }

    protected void deleteWoolToStringCrucibleRecipe() {
        String key = "WOOLTOSTRING";
        // Removing recipe for String
        RecipeHelpers.crucibleRemover.removeItem(new ItemStack(Items.string), key);
        // Removing research
        API.removeResearch(tab, key);
    }
}
