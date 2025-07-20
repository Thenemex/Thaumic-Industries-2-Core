package thaumicindustries2core.config;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import nemexlib.config.AConfig;

import static thaumicindustries2core.ThaumicIndustries2Core.modName;

public class Config extends AConfig {

    public Config(FMLPreInitializationEvent event, String version) {
        super(event, modName, modName, version);
    }

    protected void loadConfig() {}
}
