package thaumicindustries2core.model.integrations;

import fox.spiteful.forbidden.ForbiddenResearch;
import nemexlib.api.integrations.ACompat;
import nemexlib.api.items.thaumcraft.JarMaker;
import nemexlib.api.recipes.infusion.InfusionAdder;
import nemexlib.api.thaumcraft.API;
import nemexlib.api.thaumcraft.aspects.Aspects;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigResearch;
import thaumicindustries2core.config.Config;

import static nemexlib.api.items.ItemFinder.findItem;
import static nemexlib.api.items.ItemFinder.findItemTC;
import static thaumcraft.api.aspects.Aspect.*;

public class ForbiddenMagicCompat extends ACompat {

    public ForbiddenMagicCompat(String mod) {
        super(mod);
    }

    @Override
    public void loadIntegration() {
        if (Config.crystalWell) loadExpertCrystalWell_INFUSION();
    }

    protected void loadExpertCrystalWell_INFUSION() {
        ResearchItem research = API.getResearch("FORBIDDEN", "CRYSTALWELL");
        // Adding Warded Jar as prereq
        API.addParents(research, true, "JARLABEL");
        API.addParents(research, true, "INFUSION");
        // Removing recipe for Crystal Well
        IArcaneRecipe crystalWellRecipe = (IArcaneRecipe) ConfigResearch.recipes.remove("Crystalwell");
        ThaumcraftApi.getCraftingRecipes().remove(crystalWellRecipe);
        ForbiddenResearch.recipes.remove("Crystalwell");
        ForbiddenResearch.recipes.remove("Crystalwell");
        // Replacing the page with Infusion recipe
        ItemStack balancedShard = findItemTC("ItemShard", 6);
        InfusionRecipe recipe = InfusionAdder.addInfusion("CRYSTALWELL", 2,
                new Aspects(10, SENSES, ORDER, WATER),
                findItem(mod,"Crystalwell"), findItemTC("ItemInkwell", 32767), // Output & Input
                findItemTC("ItemResource", 9), // Knowledge Fragment
                balancedShard,
                JarMaker.make(CRYSTAL, 64),
                balancedShard);
        API.replacePage(research, new ResearchPage(recipe), 2);
    }
}
