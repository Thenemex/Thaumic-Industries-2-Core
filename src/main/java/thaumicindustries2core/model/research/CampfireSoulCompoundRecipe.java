package thaumicindustries2core.model.research;

import connor135246.campfirebackport.common.blocks.CampfireBackportBlocks;
import connor135246.campfirebackport.config.CampfireBackportConfig;
import nemexlib.api.items.ItemFinder;
import nemexlib.api.recipes.mystical.CompoundAdder;
import nemexlib.api.thaumcraft.aspects.Aspects;
import nemexlib.api.thaumcraft.research.AResearch;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;

import java.util.List;

import static nemexlib.api.items.ItemFinder.findItemTC;
import static thaumcraft.api.aspects.Aspect.*;
import static thaumicindustries2core.model.config.ConfigIntegrations.cb;

@SuppressWarnings("rawtypes")
public class CampfireSoulCompoundRecipe extends AResearch {

    public final static String tag = "CAMPFIRE_SOUL";
    public final static Aspects compound = new Aspects(0, 0, 0, 50, 0, 50);

    public CampfireSoulCompoundRecipe() {
        super("ARTIFICE", tag, ItemFinder.findItem(cb, "soul_campfire"));
    }

    @Override
    public void init() {
        this.setResearchAspects(new Aspects(new Aspect[]{SOUL, DEATH, FIRE, ORDER}, 6, 6, 3, 3));
        this.setNewResearch(5, -7).setPages(newTextPage(1),
                new ResearchPage(addCompoundRecipeCampfireSoul()),
                newTextPage(3),
                new ResearchPage(addCompoundRecipeLitCampfireSoul()));
    }

    protected List addCompoundRecipeCampfireSoul() {
        ItemStack woodF = new ItemStack(ConfigBlocks.blockMagicalLog, 1, 8),
                woodL = new ItemStack(ConfigBlocks.blockMagicalLog, 1, 4),
                slabW = new ItemStack(ConfigBlocks.blockSlabWood),
                soulS = new ItemStack(Blocks.soul_sand),
                grave = ItemFinder.findItem("gravestone", "gravestone");
        Object[] structure = new Object[]{
                woodF, slabW, woodF,    woodF, grave, woodF,    woodF, slabW, woodF,
                woodL, woodL, woodL,    slabW, soulS, slabW,    woodL, woodL, woodL};
        return CompoundAdder.addCompoundRecipe(tag, compound, 3, 2, 3, structure);
    }

    protected List addCompoundRecipeLitCampfireSoul() {
        return CompoundAdder.addCompoundRecipe(tag, new Aspects(Aspect.FIRE, 70), 1, 2, 1,
                findItemTC("WandCasting"), new ItemStack(CampfireBackportBlocks.soul_campfire_base));
    }


    @Override
    public void setResearchProperties() {

    }
}
