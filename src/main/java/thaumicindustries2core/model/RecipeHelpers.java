package thaumicindustries2core.model;

import nemexlib.api.recipes.arcane.ArcaneRemover;
import nemexlib.api.recipes.crucible.CrucibleRemover;
import nemexlib.api.recipes.infusion.InfusionRemover;
import nemexlib.api.recipes.workbench.WorkbenchRemover;

public class RecipeHelpers {

    public static final WorkbenchRemover workbenchRemover = WorkbenchRemover.i();
    public static final ArcaneRemover arcaneRemover = new ArcaneRemover();
    public static final CrucibleRemover crucibleRemover = new CrucibleRemover();
    public static final InfusionRemover infusionRemover = new InfusionRemover();

}
