package thaumicindustries2core.config;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import nemexlib.config.AConfig;
import thaumicindustries2core.ThaumicIndustries2Core;

public class Config extends AConfig {

    public static boolean buggedRecipePatch;
    public static boolean boneBow, campfire, golemCoreFishing, infusion, scribingTools, thaumometer, vanillaFurnace, crystalWell, seraphShoulders;
    public static boolean redstoneDupeMerge, woolToStringMerge;
    public static boolean cbEnabled, fmEnabled, gtEnabled, mcEnabled, tbEnabled, tkEnabled, ttEnabled, wgEnabled;
    public static int woolToString_Aspect_Fabrico, woolToString_Aspect_Perditio, woolToString_stringAmount;
    public static int redstoneDupe_Aspect_Machina, redstoneDupe_Aspect_Potentia, redstoneDupe_outputAmount;
    public static boolean removeDislocationFocus;
    public static boolean alternativeVisFilter, alternativeCompoundLeather;
    public static int alternativeVisFilter_outputAmount, alternativeCompoundLeather_outputAmount;
    protected static final String version = "1.12";

    public Config(FMLPreInitializationEvent event) {
        super(ThaumicIndustries2Core.modName, event, version);
    }

    protected void loadConfig() {
        String bugPatches = "Bug-Patches", expert = "Expert-Tweaks", recipeMerges = "Recipe-Merges", removals = "Removals", integrations = "Mod-Integrations", tcieFeatures = "TCIe-Features";
        comment(bugPatches, "You can tweak off/on bug patches here");
        {
            buggedRecipePatch = newEntry(bugPatches, "BuggedRecipePatch", "Removes all recipes that got a Null item set as output"); }

        comment(expert, "You can tweak off/on expert changes here");
        {
            boneBow = newEntry(expert, "BoneBow", "Sets a new arcane recipe with harder components");
            campfire = newEntry(expert, "Campfire", "Adds a mystical construct recipe for the campfire");
            golemCoreFishing = newEntry(expert, "GolemCoreFishing", "Sets a new infusion recipe with harder and special components");
            infusion = newEntry(expert, "Infusion", "Sets new items to scan before being able to unlock infusion (Alchemical Furnace + Alembic)");
            scribingTools = newEntry(expert, "ScribingTools", "Sets a new arcane recipe, that will only accept TC4 Phials and not empty bottles");
            thaumometer = newEntry(expert, "Thaumometer", "Sets a new arcane recipe using some vis");
            vanillaFurnace = newEntry(expert, "VanillaFurnace", "Adds a mystical construct recipe for the vanilla furnace, and adds another one with arcane stone");
            crystalWell = newEntry(expert, "CrystalScribingTools", "Sets a new infusion recipe with harder components");
            seraphShoulders = newEntry(expert, "SeraphShoulders", "Sets a new infusion recipe with arcane bellows and more essentia"); }

        comment(recipeMerges, "You can tweak off/on recipe merges here");
        // Redstone dupe
        {
            redstoneDupeMerge = newEntry(recipeMerges, "Redstone -> Redstone", "Delete the 2 additionnal recipes, and add one under Alchemy / Alchemical Duplication");
            redstoneDupe_Aspect_Machina = newEntry(recipeMerges, "Redstone -> Redstone : Aspect - Machina", 2, 1, 64);
            redstoneDupe_Aspect_Potentia = newEntry(recipeMerges, "Redstone -> Redstone : Aspect - Potentia", 2, 1, 64);
            redstoneDupe_outputAmount = newEntry(recipeMerges, "Redstone -> Redstone : Output - Redstone", 2, 2, 64); }
        // Wool -> String
        {
            woolToStringMerge = newEntry(recipeMerges, "Wool -> String", "Delete the 4 additionnal recipes, and add one under Alchemy / Entropic Process");
            woolToString_Aspect_Fabrico = newEntry(recipeMerges, "Wool -> String : Aspect - Fabrico", 1, 1, 64);
            woolToString_Aspect_Perditio = newEntry(recipeMerges, "Wool -> String : Aspect - Perditio", 1, 1, 64);
            woolToString_stringAmount = newEntry(recipeMerges, "Wool -> String : Output - String", 4, 1, 64); }

        comment(removals, "You can turn off/on removals here");
        {
            removeDislocationFocus = newEntry(removals, "RemoveDislocationFocus", "Removes research/recipe for Wand Focus : Dislocation ; and cleans off any Ichor prereq leftover");
        }
        
        comment(integrations, "You can turn off/on integrations here");
        {
            cbEnabled = newEntry(integrations, "Campfire Backport");
            fmEnabled = newEntry(integrations, "Forbidden Magic");
            gtEnabled = newEntry(integrations, "Garden Trees");
            mcEnabled = newEntry(integrations, "Magic Cookies");
            tbEnabled = newEntry(integrations, "Thaumic Bases");
            tkEnabled = newEntry(integrations, "Thaumaturgical Knowledge");
            ttEnabled = newEntry(integrations, "Thaumic Tinkerer");
            wgEnabled = newEntry(integrations, "Witching Gadgets"); }

        comment(tcieFeatures, "You can turn off/on special features made for the modpack Thaumic Industries");
        // Alternative Vis Filter
        {
            alternativeVisFilter = newEntry(tcieFeatures, "Alternative Vis Filter", "Adds a new research and arcane recipe for Vis Filter");
            alternativeVisFilter_outputAmount = newEntry(tcieFeatures, "Alternative Vis Filter : Output", 1, 1, 64); }
        // Alternative Compound Leather
        {
            alternativeCompoundLeather = newEntry(tcieFeatures, "Alternative Leather", "Adds a new research and a mystical construct recipe for turning a block of flesh into some leather");
            alternativeCompoundLeather_outputAmount = newEntry(tcieFeatures, "Alternative Leather : Output", 1, 1, 64);
        }
    }
}
