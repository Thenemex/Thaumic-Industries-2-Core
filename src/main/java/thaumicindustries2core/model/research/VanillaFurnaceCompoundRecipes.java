package thaumicindustries2core.model.research;

import nemexlib.api.recipes.arcane.ArcaneAdder;
import nemexlib.api.recipes.mystical.CompoundAdder;
import nemexlib.api.recipes.workbench.WorkbenchAdder;
import nemexlib.api.recipes.workbench.WorkbenchRemover;
import nemexlib.api.thaumcraft.aspects.Aspects;
import nemexlib.api.thaumcraft.research.AResearch;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;

import java.util.List;

import static nemexlib.api.items.ItemFinder.findItemTC;
import static thaumcraft.api.aspects.Aspect.*;

@SuppressWarnings("rawtypes")
public class VanillaFurnaceCompoundRecipes extends AResearch {

    public final static String tag = "VANILLAFURNACE";
    public final static Aspects compound = new Aspects(new Aspect[]{FIRE, EARTH, ORDER, ENTROPY}, 10, 10, 5, 5);

    public VanillaFurnaceCompoundRecipes() {
        super("ARTIFICE", tag, new ItemStack(Blocks.furnace));
    }

    @Override
    public void init() {
        this.setResearchAspects(new Aspect[]{FIRE, ENTROPY, EARTH}, 2, 6, 6);
        this.setNewResearch(0, -5).setPages(newTextPage(1),
                new ResearchPage(addCompoundRecipeFurnace()),
                new ResearchPage(addArcaneRecipeFurnace()));
    }

    protected IArcaneRecipe addArcaneRecipeFurnace() {
        ItemStack furnace = new ItemStack(Blocks.furnace, 2);
        // Remove vanilla recipe
        WorkbenchRemover.i().removeItem(furnace);
        // Adds an arcane recipe from arcane stone
        return ArcaneAdder.addArcane(tag,
                new Aspects(new Aspect[]{EARTH, ENTROPY, FIRE}, 6, 6, 3),
                false, false, furnace,
                "SBS", "B B", "SBS",
                'S', new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6),  // Arcane Stone Block
                'B', new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 7));
    }

    protected List addCompoundRecipeFurnace() {
        ItemStack cobble = new ItemStack(Blocks.cobblestone),
                  cbhalf = new ItemStack(Blocks.stone_slab, 1, 3),
                    coal = new ItemStack(Blocks.coal_block);
        Object[] structure = new Object[]{
                cobble, cobble, cobble,     cobble, cobble, cobble,     cobble, cobble, cobble,
                cobble, cbhalf, cobble,     cbhalf, coal,   cbhalf,     cobble, cbhalf, cobble,
                cobble, cbhalf, cobble,     cbhalf, cobble, cbhalf,     cobble, cbhalf, cobble};
        return CompoundAdder.addCompoundRecipe(tag, compound, 3, 3, 3, structure);
    }

    @Override
    public void setResearchProperties() {}
}
