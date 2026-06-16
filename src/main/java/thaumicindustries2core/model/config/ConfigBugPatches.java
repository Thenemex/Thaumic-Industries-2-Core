package thaumicindustries2core.model.config;

import net.minecraftforge.common.MinecraftForge;
import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.patches.BuggedRecipePatch;
import thaumicindustries2core.model.patches.IPatch;

public class ConfigBugPatches {

    public static IPatch buggedRecipePatch;

    public static void init() {
        if (Config.buggedRecipePatch) patchFireRecipe();
    }

    protected static void patchFireRecipe() {
        buggedRecipePatch = new BuggedRecipePatch();
        MinecraftForge.EVENT_BUS.register(buggedRecipePatch);
    }
}
