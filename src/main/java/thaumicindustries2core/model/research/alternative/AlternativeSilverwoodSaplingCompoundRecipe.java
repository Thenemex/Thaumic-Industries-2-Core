package thaumicindustries2core.model.research.alternative;

import nemexlib.api.recipes.mystical.CompoundAdder;
import nemexlib.api.thaumcraft.aspects.Aspects;
import nemexlib.api.thaumcraft.research.AResearch;
import net.minecraft.item.ItemStack;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;

import java.util.List;

import static nemexlib.api.items.ItemFinder.findItemTC;

@SuppressWarnings("rawtypes")
public class AlternativeSilverwoodSaplingCompoundRecipe extends AResearch {

    public final static Aspects compound = new Aspects(20, 0, 0, 20, 60, 0);

    public AlternativeSilverwoodSaplingCompoundRecipe() {
        super("BASICS", "ALTERNATIVESILVERWOOD", ConfigBlocks.blockCustomPlant, 1);
    }

    @Override
    public void init() {
        this.setResearchAspects(new Aspects(3, 0, 0, 3, 6, 0));
        this.setNewResearch(-6, -4).setPages(newTextPage(1),
                new ResearchPage(addCompoundRecipeSilverwoodSapling()));
    }

    protected List addCompoundRecipeSilverwoodSapling() {
        // ToDo Move this to automatic creation in the handler
        return CompoundAdder.addCompoundRecipe(tag, compound, 1, 2, 1,
                findItemTC("WandCasting"), new ItemStack(ConfigBlocks.blockCustomPlant, 1, 1));
    }

    @Override
    public void setResearchProperties() {
        this.research.setRound().setAutoUnlock();
    }
}
