package thaumicindustries2core.model.config;

import nemexlib.api.events.WandEventHandler;
import nemexlib.api.items.vanilla.EnchantedBookMaker;
import nemexlib.api.recipes.arcane.ArcaneAdder;
import nemexlib.api.recipes.infusion.InfusionAdder;
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
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigResearch;
import thaumicindustries2core.model.RecipeHelpers;
import thaumicindustries2core.model.events.VanillaFurnaceHandler;
import thaumicindustries2core.model.research.VanillaFurnaceCompoundRecipes;

import java.util.HashMap;

import static nemexlib.api.items.ItemFinder.findItemTC;
import static thaumcraft.api.aspects.Aspect.*;
import static thaumcraft.common.config.ConfigItems.*;
import static thaumicindustries2core.config.Config.*;

public class ConfigExpertTweaks {

    public static WandEventHandler vanillaFurnaceHandler;
    public final static HashMap<String, AResearch> researchMap = new HashMap<>();

    public static ItemStack specialString = new ItemStack(Items.string);

    public static void init() {
        if (boneBow) loadExpertBoneBow_ARCANE();
        if (golemCoreFishing) loadExpertGolemCoreFishing_INFUSION();
        if (scribingTools) loadExpertScribingTools_ARCANE();
        if (thaumometer) loadExpertThaumometer_ARCANE();
        if (vanillaFurnace) loadExpertVanillaFurnace_COMPOUND();
    }

    protected static void loadExpertBoneBow_ARCANE() {
        ResearchItem research = API.getResearch("ARTIFICE", "BONEBOW");
        // Adding Bone Rod as prereq
        API.addParents(research, true, "ROD_bone");
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
    protected static void loadExpertGolemCoreFishing_INFUSION() {
        ResearchItem research = API.getResearch("GOLEMANCY", "COREFISHING");
        // Remove current recipe for CoreFishing
        InfusionRecipe recipe = (InfusionRecipe) ConfigResearch.recipes.remove("CoreFishing");
        ThaumcraftApi.getCraftingRecipes().remove(recipe);
        // Replacing the page with Infusion recipe
        recipe = InfusionAdder.addInfusion("COREFISHING", 7,
                new Aspects(new Aspect[]{WATER, ORDER, TOOL, BEAST, HARVEST}, 45, 40, 32, 24, 20),
                new ItemStack(itemGolemCore, 1, 11), // Golem Core : Fishing
                new ItemStack(itemGolemCore, 1, 3), // Golem Core : Harvest
                new ItemStack(Items.fishing_rod, 1, 32767), // Fishing Rod
                new ItemStack(Items.fish), // Raw Fish
                new ItemStack(Blocks.waterlily), // Water Lily
                new ItemStack(Items.fish, 1, 2), // Clownfish
                new ItemStack(itemShard, 1, 2), // Water Shard
                new ItemStack(Items.fish, 1, 3), // Pufferfish
                EnchantedBookMaker.make((short) 1, (short) 6), // Book : Aqua Affinity I
                new ItemStack(Items.fish, 1, 1) // Raw Salmon
        );
        API.replacePage(research, new ResearchPage(recipe), 2);
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
        IArcaneRecipe recipe = ArcaneAdder.addArcane("RESEARCH", new Aspects(new Aspect[]{ORDER, AIR, WATER}, 5, 3, 3),
                true, false, new ItemStack(itemInkwell),
                new ItemStack(itemEssence, 1, 0),
                new ItemStack(Items.feather),
                "dyeBlack");
        API.addPage(research, new ResearchPage(recipe), 6);
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
        RecipeHelpers.workbenchRemover.removeItem(thaumometer);
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
}
