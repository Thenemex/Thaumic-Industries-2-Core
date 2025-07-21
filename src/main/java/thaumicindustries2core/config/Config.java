package thaumicindustries2core.config;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import nemexlib.config.AConfig;

import static thaumicindustries2core.ThaumicIndustries2Core.modName;

public class Config extends AConfig {

    public static boolean scribingTools, vanillaFurnace;

    public Config(FMLPreInitializationEvent event, String version) {
        super(event, modName, modName, version);
    }

    protected void loadConfig() {
        String expert = "Expert-Tweaks";
        config.addCustomCategoryComment(expert, "You can tweak off/on expert changes here");
        scribingTools = newEntry(expert, "ScribingTools", true);
        vanillaFurnace = newEntry(expert, "VanillaFurnace", true);
    }
}
