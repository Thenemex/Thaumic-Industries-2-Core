package thaumicindustries2core.model.config;

import nemexlib.api.events.WandEventHandler;
import nemexlib.api.recipes.arcane.ArcaneAdder;
import nemexlib.api.recipes.workbench.WorkbenchRemover;
import nemexlib.api.thaumcraft.API;
import nemexlib.api.thaumcraft.aspects.Aspects;
import nemexlib.api.thaumcraft.research.AResearch;
import nemexlib.api.thaumcraft.research.Research;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.config.ConfigResearch;
import thaumicindustries2core.ThaumicIndustries2Core;
import thaumicindustries2core.model.events.VanillaFurnaceHandler;
import thaumicindustries2core.model.research.VanillaFurnaceCompoundRecipes;

import java.util.HashMap;

import static net.minecraftforge.oredict.OreDictionary.getOres;
import static thaumcraft.api.aspects.Aspect.*;
import static thaumcraft.common.config.ConfigItems.*;
import static thaumicindustries2core.config.Config.*;

public class ConfigExpertTweaks {

    public static WandEventHandler vanillaFurnaceHandler;
    public final static HashMap<String, AResearch> researchMap = new HashMap<>();

    public static void init() {
        if (scribingTools) loadExpertScribingTools_ARCANE();
        if (thaumometer) loadExpertThaumometer_ARCANE();
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
            recipes[i] = ArcaneAdder.addArcane("RESEARCH", new Aspects(new Aspect[]{ORDER, AIR, WATER}, 5, 3, 3),
                    true, false, new ItemStack(itemInkwell),
                    new ItemStack(itemEssence, 1, 0),
                    new ItemStack(Items.feather),
                    getOres("dyeBlack").get(i)); // ToDo try to implement ore recipe for lighter NEI
        API.addPage(research, new ResearchPage(recipes), 6);
    }

    // Remove recipes from CraftingManager AND ConfigRecipes TC4
    // Fish OreDict Like-> new ItemStack(Items.field_151115_aP, 1, 32767)
    protected static void loadExpertThaumometer_ARCANE() {
        ResearchItem research = API.getResearch("ARTIFICE", "THAUMOMETER");
        // Removing recipe pages for research
        API.removePage(research, 2);
        // Removing recipes for Thaumometer
        ItemStack thaumometer = new ItemStack(itemThaumometer);
        WorkbenchRemover.i().removeItem(thaumometer);
        ConfigResearch.recipes.remove("Thaumometer");
        // Adding the page with Arcane recipe
        IArcaneRecipe recipe = ArcaneAdder.addArcane("THAUMOMETER", new Aspects(new Aspect[]{AIR, EARTH, ORDER}, 3, 8, 5),
                false, false, thaumometer,
                " S ", "GVG", " S ",
                'S', new ItemStack(itemShard, 1, 32767),
                'G', new ItemStack(Items.gold_ingot),
                'V', new ItemStack(Blocks.glass));
        API.addPage(research, new ResearchPage(recipe), 2);
    }

    protected static void loadExpertVanillaFurnace_COMPOUND() {
        VanillaFurnaceHandler.initBlueprint();
        vanillaFurnaceHandler = new VanillaFurnaceHandler();
        // Loading the research
        researchMap.put(VanillaFurnaceCompoundRecipes.tag, new VanillaFurnaceCompoundRecipes());
    }
}
