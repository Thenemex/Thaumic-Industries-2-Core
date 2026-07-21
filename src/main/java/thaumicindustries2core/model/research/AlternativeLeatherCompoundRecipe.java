package thaumicindustries2core.model.research;

import nemexlib.api.recipes.mystical.CompoundAdder;
import nemexlib.api.thaumcraft.aspects.Aspects;
import nemexlib.api.thaumcraft.research.AResearch;
import net.minecraft.init.Items;
import thaumcraft.api.research.ResearchPage;

import java.util.List;

import static nemexlib.api.items.ItemFinder.findBlockTC;
import static nemexlib.api.items.ItemFinder.findItemTC;

@SuppressWarnings("rawtypes")
public class AlternativeLeatherCompoundRecipe extends AResearch {

    // ToDo Transfer those compound aspects to superclass in AResearch
    public final static Aspects compound = new Aspects(15, 15, 0, 0, 15, 0);

    public AlternativeLeatherCompoundRecipe() {
        super("ARTIFICE", "ALTERNATIVELEATHER", Items.leather);
    }

    @Override
    public void init() {
        this.setResearchAspects(new Aspects(0, 3, 0, 3, 3, 0));
        this.setNewResearch(-1, -7).setPages(newTextPage(1),
                new ResearchPage(addCompoundRecipeLeather()));
    }

    protected List addCompoundRecipeLeather() {
        // ToDo Move this to automatic creation in SingleBlockWithDropsHandler
        return CompoundAdder.addCompoundRecipe(tag, compound, 1, 2, 1,
                findItemTC("WandCasting"), findBlockTC("blockTaint", 2).toItemStack());
    }

    @Override
    public void setResearchProperties() {
        this.research.setRound().setAutoUnlock();
    }
}
