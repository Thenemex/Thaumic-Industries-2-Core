package thaumicindustries2core.config;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import nemexlib.config.AConfig;

import static thaumicindustries2core.ThaumicIndustries2Core.modName;

public class Config extends AConfig {

    public static boolean scribingTools, thaumometer, vanillaFurnace, boneBow, crystalWell, seraphShoulders;
    public static boolean woolToStringMerge;
    public static boolean fmEnabled, mcEnabled, wgEnabled;
    protected static final String version = "1.5";

    public Config(FMLPreInitializationEvent event) {
        super(event, modName, modName, version);
    }

    protected void loadConfig() {
        String expert = "Expert-Tweaks";
        config.addCustomCategoryComment(expert, "You can tweak off/on expert changes here");
        scribingTools = newEntry(expert, "ScribingTools", "Sets a new arcane recipe, that will only accept TC4 Phials and not empty bottles");
        thaumometer = newEntry(expert, "Thaumometer", "Sets a new arcane recipe using some vis");
        vanillaFurnace = newEntry(expert, "VanillaFurnace", "Adds a mystical construct recipe for the vanilla furnace, and adds another one with arcane stone");
        boneBow = newEntry(expert, "BoneBow", "Sets a new arcane recipe with harder components");
        crystalWell = newEntry(expert, "CrystalScribingTools", "Sets a new infusion recipe with harder components");
        seraphShoulders = newEntry(expert, "SeraphShoulders", "Sets a new infusion recipe with arcane bellows and more essentia");

        String recipeMerges = "Recipe-Merges";
        config.addCustomCategoryComment(recipeMerges, "You can tweak off/on recipe merges here");
        woolToStringMerge = newEntry(recipeMerges, "Wool -> String", "Delete all 4 additionnal recipes, and add one under Alchemy / Entropic Process");

        String integrations = "Mod-Integrations";
        config.addCustomCategoryComment(integrations, "You can turn off/on integrations here");
        fmEnabled = newEntry(integrations, "Forbidden Magic");
        mcEnabled = newEntry(integrations, "Magic Cookies");
        wgEnabled = newEntry(integrations, "Witching Gadgets");
    }
}
