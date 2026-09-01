package thaumicindustries2core.model.integrations;

import nemexlib.api.integrations.ACompat;
import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.events.campfire.CampfireHandler;
import thaumicindustries2core.model.events.campfire.CampfireSoulHandler;
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
    }

    private void loadExpertCampfireSoul_COMPOUND() {
        new CampfireSoulCompoundRecipe().setHandler(new CampfireSoulHandler());
    }
}
