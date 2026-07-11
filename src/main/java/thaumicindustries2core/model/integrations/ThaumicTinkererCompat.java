package thaumicindustries2core.model.integrations;

import nemexlib.api.integrations.ACompat;
import nemexlib.api.items.ItemFinder;
import nemexlib.api.thaumcraft.API;
import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.RecipeHelpers;

public class ThaumicTinkererCompat extends ACompat {

    public ThaumicTinkererCompat(String mod, String tab) {
        super(mod, tab);
    }

    @Override
    public void loadIntegration() {
        if (Config.removeDislocationFocus) removeDislocationFocus();
    }

    protected void removeDislocationFocus() {
        // Removing the recipe for item
        RecipeHelpers.infusionRemover.removeItem(ItemFinder.findItem(mod, "focusDislocation"));
        // Removing the associated research
        API.removeResearch(tab, "FOCUS_DISLOCATION");
        // Removing the bugged null element from Ichor parents
        // ToDo Remove null tag from Ichor parentsHidden
    }
}
