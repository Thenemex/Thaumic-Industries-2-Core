package thaumicindustries2core.config;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import nemexlib.config.AConfig;

import static thaumicindustries2core.ThaumicIndustries2Core.modName;

public class Config extends AConfig {

    public static boolean scribingTools, thaumometer, vanillaFurnace, boneBow, crystalWell;
    public static boolean fmEnabled, wgEnabled;
    protected static String version = "1.4";

    public Config(FMLPreInitializationEvent event) {
        super(event, modName, modName, version);
    }

    protected void loadConfig() {
        String expert = "Expert-Tweaks", integrations = "Mod-Integrations";
        config.addCustomCategoryComment(expert, "You can tweak off/on expert changes here");
        scribingTools = newEntry(expert, "ScribingTools", true, "Sets a new arcane recipe, that will only accept TC4 Phials and not empty bottles");
        thaumometer = newEntry(expert, "Thaumometer", true, "Sets a new arcane recipe using some vis");
        vanillaFurnace = newEntry(expert, "VanillaFurnace", true, "Adds a mystical construct recipe for the vanilla furnace, and adds another one with arcane stone");
        boneBow = newEntry(expert, "BoneBow", true, "Sets a new arcane recipe with harder components");
        crystalWell = newEntry(expert, "CrystalScribingTools", true, "Sets a new infusion recipe with harder components");

        config.addCustomCategoryComment(integrations, "You can turn off/on integrations here");
        fmEnabled = newEntry(integrations, "Forbidden Magic");
        wgEnabled = newEntry(integrations, "Witching Gadgets");
    }
}
