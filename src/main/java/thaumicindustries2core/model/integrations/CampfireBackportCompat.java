package thaumicindustries2core.model.integrations;

import nemexlib.api.integrations.ACompat;
import thaumicindustries2core.config.Config;

public class CampfireBackportCompat extends ACompat {

    public CampfireBackportCompat(String mod) {
        super(mod);
    }

    @Override
    public void loadIntegration() {
        if (Config.campfire) loadExpertCampfire_COMPOUND();
    }

    protected void loadExpertCampfire_COMPOUND() {

    }
}
