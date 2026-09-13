package thaumicindustries2core.model.config;

import nemexlib.api.thaumcraft.API;
import thaumicindustries2core.model.events.alternative.AlternativeLeatherHandler;
import thaumicindustries2core.model.events.alternative.AlternativeSilverwoodSaplingHandler;
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
            loadAltSilverwoodSapling_COMPOUND(); // ToDo WandEventHandler
    }

    private static void loadAltSilverwoodSapling_COMPOUND() {
        new AlternativeSilverwoodSaplingCompoundRecipe().setHandler(new AlternativeSilverwoodSaplingHandler());
        // Swapping two researches
        API.moveResearch("PLANTS", -4, -4);
        API.moveResearch("PECH", -2, -4);
    }
}
