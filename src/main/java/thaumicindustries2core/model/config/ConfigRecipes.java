package thaumicindustries2core.model.config;

import nemexlib.api.thaumcraft.API;
import net.minecraft.item.crafting.CraftingManager;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.common.config.ConfigResearch;
import thaumicindustries2core.config.Config;

public class ConfigRecipes {

    public static void init() {
        if (Config.scribingTools) loadExpertScribingTools();
    }

    protected static void loadExpertScribingTools() {
        ResearchItem research = API.getResearch("BASICS", "RESEARCH");
        // Removing recipe pages for research
        API.removePage(research, 6);
        API.removePage(research, 6);
        // Removing recipes for Scribing Tools, excepted the one used to fill it
        CraftingManager.getInstance().getRecipeList().remove(ConfigResearch.recipes.get("Scribe1"));
        ConfigResearch.recipes.remove("Scribe1");
        CraftingManager.getInstance().getRecipeList().remove(ConfigResearch.recipes.get("Scribe2"));
        ConfigResearch.recipes.remove("Scribe2");
    }
}
