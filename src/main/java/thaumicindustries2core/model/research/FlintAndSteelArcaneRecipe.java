package thaumicindustries2core.model.research;

import nemexlib.api.recipes.arcane.ArcaneAdder;
import nemexlib.api.thaumcraft.aspects.Aspects;
import nemexlib.api.thaumcraft.research.AResearch;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import thaumicindustries2core.model.RecipeHelpers;

import static thaumcraft.api.aspects.Aspect.*;

public class FlintAndSteelArcaneRecipe extends AResearch {

    public FlintAndSteelArcaneRecipe() {
        super("ARTIFICE", "FLINTANDSTEEL", Items.flint_and_steel);
    }

    @Override
    public void init() {
        this.setResearchAspects(new Aspects(new int[]{3, 3, 6}, FIRE, TOOL, METAL));
        this.setNewResearch(-1, -7).setPages(newTextPage(1),
                new ResearchPage(addArcaneFlintAndSteel()));
    }

    protected IArcaneRecipe addArcaneFlintAndSteel() {
        return ArcaneAdder.addArcane(tag,
                new Aspects(2, 6, 0, 0, 3, 3),
                true, true,
                new ItemStack(Items.flint_and_steel), // Flint and Steel
                new ItemStack(Items.iron_ingot), // Iron Ingot
                new ItemStack(Items.flint), // Flint
                new ItemStack(Items.fire_charge), // Fire Charge
                new ItemStack(ConfigItems.itemNugget, 1, 6)); // Thaumium Nugget
    }

    @Override
    public void removeRecipes() {
        RecipeHelpers.workbenchRemover.removeItem(new ItemStack(Items.flint_and_steel));
    }

    @Override
    public void setResearchProperties() {
        this.research.setParentsHidden("THAUMIUM");
    }
}
