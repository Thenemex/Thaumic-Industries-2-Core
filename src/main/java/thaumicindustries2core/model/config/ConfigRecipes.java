package thaumicindustries2core.model.config;

import nemexlib.api.recipes.arcane.ArcaneAdder;
import nemexlib.api.thaumcraft.API;
import nemexlib.api.thaumcraft.aspects.Aspects;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigResearch;
import thaumicindustries2core.config.Config;

import static thaumcraft.api.aspects.Aspect.*;
import static thaumcraft.common.config.ConfigItems.itemEssence;
import static thaumcraft.common.config.ConfigItems.itemInkwell;

public class ConfigRecipes {

    public static void init() {
        if (Config.scribingTools) loadExpertScribingTools();
    }

    protected static void loadExpertScribingTools() {
        ResearchItem research = API.getResearch("BASICS", "RESEARCH");
        // Removing recipe pages for research
        API.removePage(research, 6);
        API.removePage(research, 6);
        // Removing recipes for Scribing Tools, excepted the one used to fill it
        CraftingManager.getInstance().getRecipeList().remove(ConfigResearch.recipes.get("Scribe1"));
        ConfigResearch.recipes.remove("Scribe1");
        CraftingManager.getInstance().getRecipeList().remove(ConfigResearch.recipes.get("Scribe2"));
        ConfigResearch.recipes.remove("Scribe2");
        // Adding the page with Arcane Recipe
        IArcaneRecipe recipe;
        for (ItemStack ink : OreDictionary.getOres("dyeBlack")) {
            recipe = ArcaneAdder.addArcane("RESEARCH", new Aspects(new Aspect[]{ORDER, AIR, WATER}, 5, 3, 3), true, false, new ItemStack(itemInkwell),
                    new ItemStack(itemEssence, 1, 0), new ItemStack(Items.feather), ink);
            if (ink.getItem().equals(Items.dye) && ink.getItemDamage() == 0) // Ink Sac
                API.addPage(research, new ResearchPage(recipe), 6);
        }
    }
}
