package thaumicindustries2core.model.config;

import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.integrations.ForbiddenMagicCompat;
import thaumicindustries2core.model.integrations.MagicCookiesCompat;
import thaumicindustries2core.model.integrations.ThaumicBasesCompat;
import thaumicindustries2core.model.integrations.WitchingGadgetsCompat;

import static nemexlib.api.integrations.ACompat.isModLoaded;

public class ConfigIntegrations {

    public static final String fm = "ForbiddenMagic", mc = "MagicCookie", tb = "thaumicbases", wg = "WitchingGadgets";

    public static void init() {
        if (isModLoaded(fm, Config.fmEnabled)) new ForbiddenMagicCompat(fm, "FORBIDDEN");
        if (isModLoaded(mc, Config.mcEnabled)) new MagicCookiesCompat(mc, "DARKSIDE");
        if (isModLoaded(tb, Config.tbEnabled)) new ThaumicBasesCompat(tb, "THAUMICBASES");
        if (isModLoaded(wg, Config.wgEnabled)) new WitchingGadgetsCompat(wg, "WITCHGADG");
    }
}
