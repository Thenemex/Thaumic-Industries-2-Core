package thaumicindustries2core.model.integrations;

import nemexlib.api.integrations.ACompat;
import nemexlib.api.thaumcraft.API;
import thaumcraft.api.research.ResearchItem;
import thaumicindustries2core.config.Config;

public class MagicCookiesCompat extends ACompat {

    public MagicCookiesCompat(String mod, String tab) {
        super(mod, tab);
    }

    @Override
    public void loadIntegration() {
        if (Config.woolToStringMerge) deleteWoolToStringCrucibleRecipe();
    }

    protected void deleteWoolToStringCrucibleRecipe() {
        ResearchItem research = API.getResearch(tab, "WOOLTOSTRING");
        // Removing recipe for String

    }
}
