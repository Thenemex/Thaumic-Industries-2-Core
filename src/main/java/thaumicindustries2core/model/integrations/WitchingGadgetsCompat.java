package thaumicindustries2core.model.integrations;

import nemexlib.api.integrations.ACompat;
import nemexlib.api.recipes.infusion.InfusionAdder;
import nemexlib.api.recipes.infusion.InfusionRemover;
import nemexlib.api.thaumcraft.API;
import nemexlib.api.thaumcraft.aspects.Aspects;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.config.ConfigExpertTweaks;
import witchinggadgets.common.WGResearch;

import static nemexlib.api.items.ItemFinder.findItem;
import static nemexlib.api.items.ItemFinder.findItemTC;
import static thaumcraft.api.aspects.Aspect.*;

public class WitchingGadgetsCompat extends ACompat {

    public WitchingGadgetsCompat(String mod) {
        super(mod);
    }

    @Override
    public void loadIntegration() {
        addYarnToExpertBoneBow();
        if (Config.seraphShoulders)
            loadExpertSeraphShoulders_INFUSION();
    }

    protected void addYarnToExpertBoneBow() {
        ConfigExpertTweaks.specialString = findItem(mod, "item.WG_Material");
    }

    protected void loadExpertSeraphShoulders_INFUSION() {
        ResearchItem research = API.getResearch("WITCHGADG", "WGBAUBLES");
        // Adding Arcane Bellows as prereq
        API.addParents(research, true, "BELLOWS");
        // Naming items
        ItemStack seraphShoulders = findItem(mod, "item.WG_Baubles", 0),
                airShard = findItemTC("ItemShard"),
                bellows = findItemTC("blockWoodenDevice"),
                feather = new ItemStack(Items.feather);
        // Removing recipe for Seraph Shoulders
        new InfusionRemover(2, ThaumcraftApi.getCraftingRecipes(), WGResearch.recipeList.values()).removePrecise(seraphShoulders);
        // Replacing the page with new recipe
        InfusionRecipe recipe = InfusionAdder.addInfusion("WGBAUBLES", 4,
                new Aspects(new int[]{60, 40, 30, 20, 15}, FLIGHT, AIR, ORDER, MOTION, GREED),
                seraphShoulders, findItem("TravellersGear", "simpleGear", 4),
                airShard,
                bellows,
                feather,
                bellows,
                airShard,
                bellows,
                feather,
                bellows);
        API.replacePage(research, new ResearchPage(recipe), 8);
    }
}
