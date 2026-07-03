package thaumicindustries2core.model.config;

import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.integrations.*;
import thaumicindustries2core.model.integrations.nested.GardenTreesNestedCompat;

import static nemexlib.api.integrations.ACompat.isModLoaded;

public class ConfigIntegrations {

    public static final String fm = "ForbiddenMagic",
            gt = "GardenTrees",
            mc = "MagicCookie",
            tb = "thaumicbases",
            tk = "benway_knowledge",
            tm = "TaintedMagic",
            tt = "ThaumicTinkerer",
            wg = "WitchingGadgets";

    public static void init() {
        if (isModLoaded(fm, Config.fmEnabled)) new ForbiddenMagicCompat(fm, "FORBIDDEN");
        if (isModLoaded(mc, Config.mcEnabled)) new MagicCookiesCompat(mc, "DARKSIDE");
        if (isModLoaded(tk, Config.tkEnabled)) new ThaumaturgicalKnowledgeCompat(tk, "KNOWLEDGE");
        if (isModLoaded(tb, Config.tbEnabled)) new ThaumicBasesCompat(tb, "THAUMICBASES");
        if (isModLoaded(wg, Config.wgEnabled)) new WitchingGadgetsCompat(wg, "WITCHGADG");

        initNestedCompats();
    }

    protected static void initNestedCompats() {
        if (isModLoaded(gt, Config.gtEnabled)) new GardenTreesNestedCompat(gt);
    }
}
