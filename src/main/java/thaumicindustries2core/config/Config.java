package thaumicindustries2core.config;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import nemexlib.config.AConfig;

import static thaumicindustries2core.ThaumicIndustries2Core.modName;

public class Config extends AConfig {

    public static boolean scribingTools, thaumometer, vanillaFurnace, boneBow, crystalWell, seraphShoulders;
    public static boolean redstoneDupeMerge, woolToStringMerge;
    public static boolean fmEnabled, mcEnabled, tbEnabled, tkEnabled, wgEnabled;
    public static int woolToString_Aspect_Fabrico, woolToString_Aspect_Perditio, woolToString_stringAmount;
    public static int redstoneDupe_Aspect_Machina, redstoneDupe_Aspect_Potentia, redstoneDupe_outputAmount;
    protected static final String version = "1.6";

    public Config(FMLPreInitializationEvent event) {
        super(event, modName, modName, version);
    }

    protected void loadConfig() {
        String expert = "Expert-Tweaks";
        config.addCustomCategoryComment(expert, "You can tweak off/on expert changes here");
        {
            scribingTools = newEntry(expert, "ScribingTools", "Sets a new arcane recipe, that will only accept TC4 Phials and not empty bottles");
            thaumometer = newEntry(expert, "Thaumometer", "Sets a new arcane recipe using some vis");
            vanillaFurnace = newEntry(expert, "VanillaFurnace", "Adds a mystical construct recipe for the vanilla furnace, and adds another one with arcane stone");
            boneBow = newEntry(expert, "BoneBow", "Sets a new arcane recipe with harder components");
            crystalWell = newEntry(expert, "CrystalScribingTools", "Sets a new infusion recipe with harder components");
            seraphShoulders = newEntry(expert, "SeraphShoulders", "Sets a new infusion recipe with arcane bellows and more essentia"); }

        String recipeMerges = "Recipe-Merges";
        config.addCustomCategoryComment(recipeMerges, "You can tweak off/on recipe merges here");
        // Redstone dupe
        {
            redstoneDupeMerge = newEntry(recipeMerges, "Redstone -> Redstone", "Delete the 2 additionnal recipes, and add one under Alchemy / Alchemical Duplication");
            redstoneDupe_Aspect_Machina = newEntry(recipeMerges, "Redstone -> Redstone : Aspect - Machina", 2, 1, 64, null);
            redstoneDupe_Aspect_Potentia = newEntry(recipeMerges, "Redstone -> Redstone : Aspect - Potentia", 2, 1, 64, null);
            redstoneDupe_outputAmount = newEntry(recipeMerges, "Redstone -> Redstone : Output - Redstone", 2, 2, 64, null); }
        // Wool -> String
        {
            woolToStringMerge = newEntry(recipeMerges, "Wool -> String", "Delete the 4 additionnal recipes, and add one under Alchemy / Entropic Process");
            woolToString_Aspect_Fabrico = newEntry(recipeMerges, "Wool -> String : Aspect - Fabrico", 1, 1, 64, null);
            woolToString_Aspect_Perditio = newEntry(recipeMerges, "Wool -> String : Aspect - Perditio", 1, 1, 64, null);
            woolToString_stringAmount = newEntry(recipeMerges, "Wool -> String : Output - String", 4, 1, 64, null); }

        String integrations = "Mod-Integrations";
        config.addCustomCategoryComment(integrations, "You can turn off/on integrations here");
        {
            fmEnabled = newEntry(integrations, "Forbidden Magic");
            mcEnabled = newEntry(integrations, "Magic Cookies");
            tkEnabled = newEntry(integrations, "Thaumaturgical Knowledge");
            tbEnabled = newEntry(integrations, "Thaumic Bases");
            wgEnabled = newEntry(integrations, "Witching Gadgets"); }
    }
}
