package thaumicindustries2core.model.research;

import nemexlib.api.recipes.arcane.ArcaneAdder;
import nemexlib.api.thaumcraft.aspects.Aspects;
import nemexlib.api.thaumcraft.research.AResearch;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.research.ResearchPage;

import static thaumcraft.api.aspects.Aspect.*;
import static thaumcraft.common.config.ConfigBlocks.*;
import static thaumcraft.common.config.ConfigItems.*;

@SuppressWarnings("unused")
public class AlternativeVisFilterArcaneRecipe extends AResearch {

    public AlternativeVisFilterArcaneRecipe() {
        super("ALCHEMY", "ALTERNATIVEVISFILTER", itemResource, 8);
    }

    @Override
    public void init() {
        this.setResearchAspects(new Aspect[]{ORDER, GREED, TREE, CRYSTAL}, 5, 3, 3, 3);
        this.setNewResearch(6, -3).setPages(newTextPage(1),
                new ResearchPage(addArcaneRecipeSilverwoodPlanks()));
    }

    protected IArcaneRecipe addArcaneRecipeSilverwoodPlanks() {
        return ArcaneAdder.addArcane(tag,
                new Aspects(20, 0, 0, 0, 55, 10),
                false, false,
                new ItemStack(blockWoodenDevice, 0, 7), // Silverwood Plank
                "SMS", "QBQ", "SMS",
                'S', "nuggetSilver", // Silver Nugget
                'M', new ItemStack(itemResource, 0, 10), // Mirrored Glass
                'Q', new ItemStack(itemNugget, 0, 5), // Quicksilver Nugget
                'B', new ItemStack(Blocks.log, 0, 2)); // Birch Log
    }

    @Override
    public void setResearchProperties() {
        this.research.setParents("DISTILESSENTIA");
        this.research.setSecondary().setConcealed();
    }
}
