package thaumicindustries2core.model.config;

import cpw.mods.fml.common.Loader;
import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.integrations.WitchingGadgetsCompat;

public class ConfigIntegrations {

    public static final String wg = "WitchingGadgets";

    public static void init() {
        if (modIsLoaded(wg, Config.wgEnabled))
            new WitchingGadgetsCompat(wg);
    }

    public static boolean modIsLoaded(String mod, boolean config) {
        return Loader.isModLoaded(mod) && config;
    }
}
