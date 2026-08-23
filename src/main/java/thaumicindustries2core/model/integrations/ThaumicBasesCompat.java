package thaumicindustries2core.model.integrations;

import nemexlib.api.integrations.ACompat;
import nemexlib.api.items.thaumcraft.FocusMaker;
import nemexlib.api.recipes.infusion.InfusionAdder;
import nemexlib.api.thaumcraft.API;
import nemexlib.api.thaumcraft.aspects.Aspects;
import nemexlib.model.config.RecipeHelpers;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tb.init.TBBlocks;
import tb.init.TBItems;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumicindustries2core.config.Config;

import static nemexlib.api.items.ItemFinder.findItem;
import static thaumcraft.api.aspects.Aspect.*;
import static thaumcraft.common.config.ConfigItems.*;
import static thaumicindustries2core.model.config.ConfigIntegrations.tm;
import static thaumicindustries2core.model.config.ConfigIntegrations.tt;

public class ThaumicBasesCompat extends ACompat {

    public ThaumicBasesCompat(String mod, String tab) {
        super(mod, tab);
    }

    @Override
    public void loadIntegration() {
        if (Config.herobrineScythe) loadExpertHerobrineScythe_INFUSION();

        if (Config.woolToStringMerge) deleteWoolToStringCrucibleRecipe();
        if (Config.redstoneDupeMerge) deleteRestoneDupeCrucibleRecipe();
    }

    private void loadExpertHerobrineScythe_INFUSION() {
        ResearchItem research = API.getResearch("TB.HerobrinesScythe");
        // Remove current
        ItemStack scythe = new ItemStack(TBItems.herobrinesScythe);
        RecipeHelpers.infusionRemover.removeItem(scythe);
        // Replacing the page with the new recipe
        ItemStack star = new ItemStack(Items.nether_star),
                oldGold = new ItemStack(TBBlocks.oldGold),
                liquidDeath = new ItemStack(itemBucketDeath),
                eye = new ItemStack(itemEldritchObject),
                pearl = new ItemStack(itemEldritchObject, 1, 3),
                cloth = findItem(tm, "ItemMaterial", 2);
        InfusionRecipe recipe = InfusionAdder.addInfusion(research.key, 16,
                new Aspects(new Aspect[]{WEAPON, ELDRITCH, ENERGY, DEATH, GREED, AURA, MOTION}, 130, 100, 72, 64, 40, 18, 50),
                scythe,
                findItem(tm, "ItemShadowmetalHoe", 32767), // Shadowmetal Hoe
                FocusMaker.make((ItemFocusBasic) itemFocusShock, 0, 0, 17, 3, 3), // Focus : Shock with 5 upgrades
                findItem("Talismans 2", "Movement Talisman"), // Movement Talisman
                star, // Nether Star
                oldGold, // Ancient Gold Block
                liquidDeath, // Bucket of Liquid Death
                eye, // Eldritch Eye
                pearl, // Primordial Pearl
                cloth, // Crimson-stained Cloth
                new ItemStack(itemWandRod, 1, 100), // Staff Core of the Primal
                findItem(tt, "bloodSword", 32767), // Cursed Spirit's Blade
                star, // Nether Star
                oldGold, // Ancient Gold Block
                liquidDeath, // Bucket of Liquid Death
                eye, // Eldritch Eye
                pearl, // Primordial Pearl
                cloth // Crimson-stained Cloth
        );
        API.replacePage(research, new ResearchPage(recipe), 3);
    }

    private void deleteWoolToStringCrucibleRecipe() {
        String key1 = "TB.AdvancedEntropy", key2 = "TB.MasterEntropy";
        ItemStack string = new ItemStack(Items.string);
        RecipeHelpers.crucibleRemover.removeItem(string, key1);
        API.removePage(tab, key1, 2);
        RecipeHelpers.crucibleRemover.removeItem(string, key2);
        API.removePage(tab, key2, 2);
    }
    private void deleteRestoneDupeCrucibleRecipe() {
        String key = "TB.SimpleDublication"; // What the typo
        RecipeHelpers.crucibleRemover.removeItem(new ItemStack(Items.redstone), key);
        API.removePage(tab, key, 4);
    }
}
