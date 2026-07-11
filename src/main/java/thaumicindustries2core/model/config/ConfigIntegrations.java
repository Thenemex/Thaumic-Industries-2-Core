package thaumicindustries2core.model.config;

import thaumicindustries2core.model.integrations.*;
import thaumicindustries2core.model.integrations.nested.GardenTreesNestedCompat;

import static nemexlib.api.integrations.ACompat.isModLoaded;
import static thaumicindustries2core.config.Config.*;

public class ConfigIntegrations {

    public static final String fm = "ForbiddenMagic",
            // ba = "BloodArsenal",
            bm = "AWWayofTime",
            bt = "Botania",
            gt = "GardenTrees",
            mc = "MagicCookie",
            // sg = "Sanguimancy",
            tb = "thaumicbases",
            tk = "benway_knowledge",
            tm = "TaintedMagic",
            tt = "ThaumicTinkerer",
            tw = "TwilightForest",
            wg = "WitchingGadgets";

    public static void init() {
        if (isModLoaded(fm, fmEnabled)) new ForbiddenMagicCompat(fm, "FORBIDDEN");
        if (isModLoaded(mc, mcEnabled)) new MagicCookiesCompat(mc, "DARKSIDE");
        if (isModLoaded(tb, tbEnabled)) new ThaumicBasesCompat(tb, "THAUMICBASES");
        if (isModLoaded(tk, tkEnabled)) new ThaumaturgicalKnowledgeCompat(tk, "KNOWLEDGE");
        if (isModLoaded(tt, ttEnabled)) new ThaumicTinkererCompat(tt, "TT_CATEGORY");
        if (isModLoaded(wg, wgEnabled)) new WitchingGadgetsCompat(wg, "WITCHGADG");

        initNestedCompats();
    }

    protected static void initNestedCompats() {
        if (isModLoaded(gt, gtEnabled)) new GardenTreesNestedCompat(gt);
    }
}
