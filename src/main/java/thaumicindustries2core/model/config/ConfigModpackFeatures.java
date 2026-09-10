package thaumicindustries2core.model.config;

import thaumicindustries2core.model.events.AlternativeLeatherHandler;
import thaumicindustries2core.model.research.alternative.AlternativeLeatherCompoundRecipe;
import thaumicindustries2core.model.research.alternative.AlternativeSilverwoodSaplingCompoundRecipe;
import thaumicindustries2core.model.research.alternative.AlternativeVisFilterArcaneRecipe;

import static thaumicindustries2core.config.Config.*;

public class ConfigModpackFeatures {

    public static void init() {
        if (alternativeVisFilter)
            new AlternativeVisFilterArcaneRecipe();
        if (alternativeCompoundLeather)
            new AlternativeLeatherCompoundRecipe().setHandler(new AlternativeLeatherHandler());
        if (alternativeSilverwoodSaplingCompound)
            new AlternativeSilverwoodSaplingCompoundRecipe();
    }
}
