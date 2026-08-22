package thaumicindustries2core.model.integrations;

import nemexlib.api.integrations.ACompat;
import nemexlib.api.items.ItemFinder;
import nemexlib.api.thaumcraft.API;
import nemexlib.model.config.RecipeHelpers;
import thaumicindustries2core.config.Config;

public class ThaumicTinkererCompat extends ACompat {

    public ThaumicTinkererCompat(String mod, String tab) {
        super(mod, tab);
    }

    @Override
    public void loadIntegration() {
        if (Config.removeDislocationFocus) removeDislocationFocus();
    }

    private void removeDislocationFocus() {
        // Removing the recipe for item
        RecipeHelpers.infusionRemover.removeItem(ItemFinder.findItem(mod, "focusDislocation"));
        // Removing the associated research
        API.removeResearch(tab, "FOCUS_DISLOCATION");
    }
}
