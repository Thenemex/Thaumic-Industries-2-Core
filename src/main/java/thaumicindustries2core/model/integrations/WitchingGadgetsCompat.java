package thaumicindustries2core.model.integrations;

import nemexlib.api.integrations.ACompat;
import nemexlib.api.items.ItemFinder;
import thaumicindustries2core.model.config.ConfigExpertTweaks;

public class WitchingGadgetsCompat extends ACompat {

    public WitchingGadgetsCompat(String mod) {
        super(mod);
    }

    @Override
    public void loadIntegration() {
        addYarnToExpertBoneBow();
    }

    protected void addYarnToExpertBoneBow() {
        ConfigExpertTweaks.specialString = ItemFinder.findItem(mod, "item.WG_Material", 0);
    }
}
