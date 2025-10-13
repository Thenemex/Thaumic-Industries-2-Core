package thaumicindustries2core.model.integrations;

import fox.spiteful.forbidden.ForbiddenResearch;
import nemexlib.api.integrations.ACompat;
import nemexlib.api.items.thaumcraft.JarMaker;
import nemexlib.api.recipes.arcane.ArcaneRemover;
import nemexlib.api.recipes.infusion.InfusionAdder;
import nemexlib.api.thaumcraft.API;
import nemexlib.api.thaumcraft.aspects.Aspects;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
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
        // Naming items
        ItemStack crystalWell = findItem(mod,"Crystalwell"),
                balancedShard = findItemTC("ItemShard", 6);
        // Removing recipe for Crystal Well
        new ArcaneRemover(2, ThaumcraftApi.getCraftingRecipes(), ForbiddenResearch.recipes.values()).removeItem(crystalWell);
        // Replacing the page with Infusion recipe
        InfusionRecipe recipe = InfusionAdder.addInfusion("CRYSTALWELL", 2,
                new Aspects(10, SENSES, ORDER, WATER),
                crystalWell, findItemTC("ItemInkwell", 32767), // Output & Input
                findItemTC("ItemResource", 9), // Knowledge Fragment
                balancedShard,
                JarMaker.make(CRYSTAL, 64),
                balancedShard);
        API.replacePage(research, new ResearchPage(recipe), 2);
    }
}
