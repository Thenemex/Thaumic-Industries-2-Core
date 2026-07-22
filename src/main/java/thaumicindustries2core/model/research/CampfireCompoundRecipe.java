package thaumicindustries2core.model.research;

import nemexlib.api.items.ItemFinder;
import nemexlib.api.recipes.mystical.CompoundAdder;
import nemexlib.api.thaumcraft.aspects.Aspects;
import nemexlib.api.thaumcraft.research.AResearch;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import thaumcraft.api.research.ResearchPage;

import java.util.List;

import static thaumicindustries2core.model.config.ConfigIntegrations.*;

@SuppressWarnings("rawtypes")
public class CampfireCompoundRecipe extends AResearch {

    public final static String tag = "CAMPFIRE";
    public final static Aspects compound = new Aspects(0, 0, 0, 5, 0, 5);

    public CampfireCompoundRecipe() {
        super("ARTIFICE", tag, ItemFinder.findItem(cb, "campfire"));
    }

    @Override
    public void init() {
        this.setResearchAspects(new Aspects(2, 6, 0, 2, 6, 0));
        this.setNewResearch(3, -7).setPages(newTextPage(1),
                new ResearchPage(addCompoundRecipeCampfire())); // ToDo Do the localization text
    }

    protected List addCompoundRecipeCampfire() {
        ItemStack woodF = new ItemStack(Blocks.log, 1, 8),
                  woodL = new ItemStack(Blocks.log, 1, 4),
                  slabW = new ItemStack(Blocks.wooden_slab),
                  coalB = new ItemStack(Blocks.coal_block),
                  carpB = new ItemStack(Blocks.carpet, 1, 15);
        Object[] structure = new Object[]{
                woodF, slabW, woodF,    woodF, carpB, woodF,    woodF, slabW, woodF,
                woodL, woodL, woodL,    slabW, coalB, slabW,    woodL, woodL, woodL};
        return CompoundAdder.addCompoundRecipe(tag, compound, 3, 2, 3, structure);
    }

    @Override
    public void setResearchProperties() {
        this.research.setRound().setAutoUnlock();
    }
}
