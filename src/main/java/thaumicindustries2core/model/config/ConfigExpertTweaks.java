package thaumicindustries2core.model.config;

import nemexlib.api.recipes.arcane.ArcaneAdder;
import nemexlib.api.thaumcraft.API;
import nemexlib.api.thaumcraft.aspects.Aspects;
import nemexlib.api.thaumcraft.research.Research;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigResearch;
import thaumicindustries2core.model.research.VanillaFurnaceCompoundRecipes;

import static net.minecraftforge.oredict.OreDictionary.getOres;
import static thaumcraft.api.aspects.Aspect.*;
import static thaumcraft.common.config.ConfigItems.itemEssence;
import static thaumcraft.common.config.ConfigItems.itemInkwell;
import static thaumicindustries2core.config.Config.scribingTools;
import static thaumicindustries2core.config.Config.vanillaFurnace;

public class ConfigExpertTweaks {

    public static void init() {
        Research.modID = "tci2core";
        if (scribingTools) loadExpertScribingTools_ARCANE();
        if (vanillaFurnace) loadExpertVanillaFurnace_COMPOUND();
    }

    protected static void loadExpertScribingTools_ARCANE() {
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
        IArcaneRecipe[] recipes = new IArcaneRecipe[getOres("dyeBlack").size()];
        for (int i = 0; i < getOres("dyeBlack").size(); i++)
            recipes[i] = ArcaneAdder.addArcane("RESEARCH", new Aspects(new Aspect[]{ORDER, AIR, WATER}, 5, 3, 3), true, false, new ItemStack(itemInkwell),
                    new ItemStack(itemEssence, 1, 0), new ItemStack(Items.feather), getOres("dyeBlack").get(i));
        API.addPage(research, new ResearchPage(recipes), 6);
    }

    protected static void loadExpertVanillaFurnace_COMPOUND() {
        // Loading the research
        new VanillaFurnaceCompoundRecipes();
    }
}
