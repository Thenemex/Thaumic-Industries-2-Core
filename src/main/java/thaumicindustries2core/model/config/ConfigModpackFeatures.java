package thaumicindustries2core.model.config;

import nemexlib.api.events.WandEventHandler;
import thaumicindustries2core.model.events.AlternativeLeatherHandler;
import thaumicindustries2core.model.research.AlternativeLeatherCompoundRecipe;
import thaumicindustries2core.model.research.AlternativeVisFilterArcaneRecipe;

import static thaumicindustries2core.config.Config.alternativeCompoundLeather;
import static thaumicindustries2core.config.Config.alternativeVisFilter;
import static thaumicindustries2core.model.config.ConfigExpertTweaks.put;

public class ConfigModpackFeatures {

    public static WandEventHandler alternativeLeatherHandler;

    public static void init() {
        if (alternativeVisFilter) loadFeatureVisFilter_ARCANE();
        if (alternativeCompoundLeather) loadAlternativeLeather_COMPOUND();
    }

    protected static void loadFeatureVisFilter_ARCANE() {
        put(new AlternativeVisFilterArcaneRecipe());
    }

    protected static void loadAlternativeLeather_COMPOUND() {
        alternativeLeatherHandler = new AlternativeLeatherHandler();
        // Loading the research
        ConfigExpertTweaks.put(new AlternativeLeatherCompoundRecipe());
    }
}
