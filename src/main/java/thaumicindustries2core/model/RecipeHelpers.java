package thaumicindustries2core.model;

import nemexlib.api.recipes.arcane.ArcaneRemover;
import nemexlib.api.recipes.crucible.CrucibleRemover;
import nemexlib.api.recipes.infusion.InfusionRemover;
import nemexlib.api.recipes.workbench.WorkbenchRemover;
import thaumcraft.api.ThaumcraftApi;

import java.util.List;

@SuppressWarnings("rawtypes")
public class RecipeHelpers {

    public static WorkbenchRemover workbenchRemover;
    public static ArcaneRemover arcaneRemover;
    public static CrucibleRemover crucibleRemover;
    public static InfusionRemover infusionRemover;

    public static void init() {
        List tcRecipes = ThaumcraftApi.getCraftingRecipes();
        workbenchRemover = WorkbenchRemover.i();
        arcaneRemover = new ArcaneRemover(tcRecipes);
        crucibleRemover = new CrucibleRemover(tcRecipes);
        infusionRemover = new InfusionRemover(tcRecipes);
    }
}
