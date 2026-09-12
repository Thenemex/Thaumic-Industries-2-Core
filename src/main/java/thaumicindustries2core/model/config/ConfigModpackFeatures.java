package thaumicindustries2core.model.config;

import nemexlib.api.thaumcraft.API;
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
            loadAltSilverwoodSapling_COMPOUND();
    }

    private static void loadAltSilverwoodSapling_COMPOUND() {
        new AlternativeSilverwoodSaplingCompoundRecipe();
        // Swapping two researches
        API.moveResearch("PLANTS", -4, -4);
        API.moveResearch("PECH", -2, -4);
    }
}
