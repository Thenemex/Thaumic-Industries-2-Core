package thaumicindustries2core.model.config;

import thaumicindustries2core.model.research.AlternativeVisFilterArcaneRecipe;

import static thaumicindustries2core.config.Config.alternativeVisFilter;
import static thaumicindustries2core.model.config.ConfigExpertTweaks.put;

public class ConfigModpackFeatures {

    public static void init() {
        if (alternativeVisFilter) loadFeatureVisFilter_ARCANE();
    }

    protected static void loadFeatureVisFilter_ARCANE() {
        put(new AlternativeVisFilterArcaneRecipe());
    }
}
