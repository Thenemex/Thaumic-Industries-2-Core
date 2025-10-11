package thaumicindustries2core.model.config;

import nemexlib.api.events.WandEventHandler;
import nemexlib.api.recipes.arcane.ArcaneAdder;
import nemexlib.api.recipes.workbench.WorkbenchRemover;
import nemexlib.api.thaumcraft.API;
import nemexlib.api.thaumcraft.aspects.Aspects;
import nemexlib.api.thaumcraft.research.AResearch;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigResearch;
import thaumicindustries2core.model.events.VanillaFurnaceHandler;
import thaumicindustries2core.model.research.VanillaFurnaceCompoundRecipes;

import java.util.HashMap;

import static nemexlib.api.items.ItemFinder.findItemTC;
import static net.minecraftforge.oredict.OreDictionary.getOres;
import static thaumcraft.api.aspects.Aspect.*;
import static thaumcraft.common.config.ConfigItems.*;
import static thaumicindustries2core.config.Config.*;

public class ConfigExpertTweaks {

    public static WandEventHandler vanillaFurnaceHandler;
    public final static HashMap<String, AResearch> researchMap = new HashMap<>();

    public static ItemStack specialString = new ItemStack(Items.string);

    public static void init() {
        if (scribingTools) loadExpertScribingTools_ARCANE();
        if (thaumometer) loadExpertThaumometer_ARCANE();
        if (vanillaFurnace) loadExpertVanillaFurnace_COMPOUND();
        if (boneBow) loadExpertBoneBow_ARCANE();
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
        // Adding the page with Arcane recipe
        IArcaneRecipe[] recipes = new IArcaneRecipe[getOres("dyeBlack").size()];
        for (int i = 0; i < getOres("dyeBlack").size(); i++)
            recipes[i] = ArcaneAdder.addArcane("RESEARCH", new Aspects(new Aspect[]{ORDER, AIR, WATER}, 5, 3, 3),
                    true, false, new ItemStack(itemInkwell),
                    new ItemStack(itemEssence, 1, 0),
                    new ItemStack(Items.feather),
                    getOres("dyeBlack").get(i)); // ToDo try to implement ore recipe for lighter NEI
        API.addPage(research, new ResearchPage(recipes), 6);
    }
    protected static void loadExpertThaumometer_ARCANE() {
        ResearchItem research = API.getResearch("ARTIFICE", "THAUMOMETER");
        // Removing recipe pages for research
        API.removePage(research, 2);
        // Instanciating items
        ItemStack thaumometer = new ItemStack(itemThaumometer),
                shardOreDict = new ItemStack(itemShard, 1, 32767),
                goldIngot = new ItemStack(Items.gold_ingot);
        // Removing recipes for Thaumometer
        WorkbenchRemover.i().removeItem(thaumometer);
        ConfigResearch.recipes.remove("Thaumometer");
        // Adding the page with Arcane recipe
        IArcaneRecipe[] recipes = new IArcaneRecipe[2]; // ToDo Add boolean config for rotated recipe
        for (int i = 0; i < recipes.length; i++)
                recipes[i] = ArcaneAdder.addArcane("THAUMOMETER", new Aspects(new Aspect[]{AIR, EARTH, ORDER}, 3, 8, 5),
                false, false, thaumometer,
                " S ", "GVG", " S ",
                'S', (i == 0) ? shardOreDict : goldIngot,
                'G', (i == 0) ? goldIngot : shardOreDict,
                'V', new ItemStack(Blocks.glass));
        API.addPage(research, new ResearchPage(recipes), 2);
    }
    protected static void loadExpertVanillaFurnace_COMPOUND() {
        VanillaFurnaceHandler.initBlueprint();
        vanillaFurnaceHandler = new VanillaFurnaceHandler();
        // Loading the research
        researchMap.put(VanillaFurnaceCompoundRecipes.tag, new VanillaFurnaceCompoundRecipes());
    }
    protected static void loadExpertBoneBow_ARCANE() {
        ResearchItem research = API.getResearch("ARTIFICE", "BONEBOW");
        // Adding Bone Rod as prereq
        API.addParents(research, true, "ROD_BONE");
        // Removing recipe for BoneBow
        IArcaneRecipe boneBowRecipe = (IArcaneRecipe) ConfigResearch.recipes.remove("BoneBow");
        ThaumcraftApi.getCraftingRecipes().remove(boneBowRecipe);
        // Replacing the page with Arcane recipe
        IArcaneRecipe recipe = ArcaneAdder.addArcane("BONEBOW", new Aspects(new Aspect[]{ENTROPY, AIR}, 60, 35),
                false, false, new ItemStack(itemBowBone),
                "SB ", "YWR", "SB ",
                'S', new ItemStack(Items.string),
                'B', new ItemStack(Items.bone),
                'Y', specialString, // Will turn to Yarn if Witching Gagdets is loaded
                'W', new ItemStack(Items.bow, 1, 32767),
                'R', findItemTC("WandRod", 7)); // Bone Rod
        API.replacePage(research, new ResearchPage(recipe), 2);
    }
}
