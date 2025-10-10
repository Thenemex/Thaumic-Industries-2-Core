package thaumicindustries2core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import nemexlib.api.util.Logger;
import nemexlib.config.AConfig;
import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.config.ConfigExpertTweaks;
import thaumicindustries2core.model.config.ConfigIntegrations;

import static thaumicindustries2core.ThaumicIndustries2Core.dependencies;
import static thaumicindustries2core.ThaumicIndustries2Core.modID;

@SuppressWarnings({"unused", "EmptyMethod"})
@Mod(modid = modID, useMetadata = true, version = "1.3", dependencies = dependencies)
public class ThaumicIndustries2Core{

    public static final String modID = "TCI2Core", modName = "ThaumicIndustries2Core";
    public static AConfig config;
    public static final Logger logger = new Logger(modID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Config(event).init(); // Init config
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent ignoredEvent) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent ignoredEvent) {
        // Loading Integrations
        ConfigIntegrations.init();
        // Loading recipe changes
        ConfigExpertTweaks.init();
    }

    public static final String dependencies = "required-after:Thaumcraft@[4.2.3.5,);required-after:NemexLib@[1.0.0.8,);after:tc4tweak";
}
