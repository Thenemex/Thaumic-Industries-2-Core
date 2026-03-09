package thaumicindustries2core.model.config;

import thaumicindustries2core.config.Config;

public class ConfigRecipeMerges {

    public static void init() {
        if (Config.woolToStringMerge) mergeCrucibleWoolToString();
    }

    protected static void mergeCrucibleWoolToString() {

    }
}
