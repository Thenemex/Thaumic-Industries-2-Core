package thaumicindustries2core.model.integrations;

import nemexlib.api.integrations.ACompat;
import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.events.CampfireHandler;
import thaumicindustries2core.model.events.campfire.CampfireIgniterHandler;
import thaumicindustries2core.model.events.campfire.CampfireSoulIgniterHandler;
import thaumicindustries2core.model.research.CampfireCompoundRecipe;
import thaumicindustries2core.model.research.CampfireSoulCompoundRecipe;

public class CampfireBackportCompat extends ACompat {

    public CampfireBackportCompat(String mod) {
        super(mod);
    }

    @Override
    public void loadIntegration() {
        if (Config.campfire) loadExpertCampfire_COMPOUND();
        if (Config.campfireSoul) loadExpertCampfireSoul_COMPOUND();
    }

    private void loadExpertCampfire_COMPOUND() {
        new CampfireCompoundRecipe().setHandler(new CampfireHandler());
        // Handler for igniting the campfire with custom aspects
        new CampfireIgniterHandler();
    }

    private void loadExpertCampfireSoul_COMPOUND() {
        new CampfireSoulCompoundRecipe();
        // Handler for igniting the campfire with custom aspects
        new CampfireSoulIgniterHandler();
    }
}
