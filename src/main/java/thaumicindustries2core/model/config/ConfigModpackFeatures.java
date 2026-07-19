package thaumicindustries2core.model.config;

import thaumicindustries2core.model.events.AlternativeLeatherHandler;
import thaumicindustries2core.model.research.AlternativeLeatherCompoundRecipe;
import thaumicindustries2core.model.research.AlternativeVisFilterArcaneRecipe;

import static thaumicindustries2core.config.Config.alternativeCompoundLeather;
import static thaumicindustries2core.config.Config.alternativeVisFilter;

public class ConfigModpackFeatures {

    public static void init() {
        if (alternativeVisFilter) loadFeatureVisFilter_ARCANE();
        if (alternativeCompoundLeather) loadAlternativeLeather_COMPOUND();
    }

    protected static void loadFeatureVisFilter_ARCANE() {
        new AlternativeVisFilterArcaneRecipe();
    }

    protected static void loadAlternativeLeather_COMPOUND() {
        new AlternativeLeatherHandler();
        // Loading the research
        new AlternativeLeatherCompoundRecipe();
    }
}
