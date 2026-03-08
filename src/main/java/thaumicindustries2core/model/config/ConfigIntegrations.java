package thaumicindustries2core.model.config;

import cpw.mods.fml.common.Loader;
import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.integrations.ForbiddenMagicCompat;
import thaumicindustries2core.model.integrations.MagicCookiesCompat;
import thaumicindustries2core.model.integrations.WitchingGadgetsCompat;

public class ConfigIntegrations {

    public static final String fm = "ForbiddenMagic", mc = "MagicCookie", wg = "WitchingGadgets";

    public static void init() {
        if (modIsLoaded(wg, Config.wgEnabled))
            new WitchingGadgetsCompat(wg, "WITCHGADG");
        if (modIsLoaded(fm, Config.fmEnabled))
            new ForbiddenMagicCompat(fm, "FORBIDDEN");
        if (modIsLoaded(mc, Config.mcEnabled))
            new MagicCookiesCompat(mc, "DARKSIDE");
    }

    public static boolean modIsLoaded(String mod, boolean config) {
        return Loader.isModLoaded(mod) && config;
    }
}
