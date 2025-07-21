package thaumicindustries2core.model.research;

import nemexlib.api.recipes.mystical.CompoundAdder;
import nemexlib.api.thaumcraft.aspects.Aspects;
import nemexlib.api.thaumcraft.research.AResearch;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.research.ResearchPage;

import java.util.List;

import static thaumcraft.api.aspects.Aspect.*;

@SuppressWarnings("rawtypes")
public class VanillaFurnaceCompoundRecipes extends AResearch {

    public VanillaFurnaceCompoundRecipes() {
        super("ARTIFICE", "VANILLAFURNACE", new ItemStack(Blocks.furnace));
    }

    @Override
    public void init() {
        this.setResearchAspects(new Aspect[]{FIRE, ENTROPY, EARTH}, 2, 6, 6);
        this.setNewResearch(0, -5).setPages(newTextPage(1),
                new ResearchPage(addRecipeMagicalFurnace()));
    }

    protected List addRecipeMagicalFurnace() {
        Aspects aspects = new Aspects(new Aspect[]{FIRE, EARTH, ORDER, ENTROPY}, 10, 10, 5, 5);
        ItemStack cobble = new ItemStack(Blocks.cobblestone),
                  cbhalf = new ItemStack(Blocks.stone_slab, 1, 3),
                    coal = new ItemStack(Blocks.coal_block);
        Object[] structure = new Object[]{
                cobble, cobble, cobble,     cobble, cobble, cobble,     cobble, cobble, cobble,
                cobble, cbhalf, cobble,     cbhalf, coal,   cbhalf,     cobble, cbhalf, cobble,
                cobble, cbhalf, cobble,     cbhalf, cobble, cbhalf,     cobble, cbhalf, cobble};
        return CompoundAdder.addCompoundRecipe(tag, aspects, 3, 3, 3, structure);
    }

    // ToDo - Add recipe with 8 Arcane Stone
    // ToDo - Remove Vanilla furnace default recipe

    @Override
    public void setResearchProperties() {

    }
}
