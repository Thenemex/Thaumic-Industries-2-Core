package thaumicindustries2core.model.config;

import nemexlib.api.recipes.crucible.CrucibleAdder;
import nemexlib.api.thaumcraft.API;
import nemexlib.api.thaumcraft.aspects.Aspects;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumicindustries2core.config.Config;

public class ConfigRecipeMerges {

    public static void init() {
        if (Config.redstoneDupeMerge) mergeRedstoneDupe();
        if (Config.woolToStringMerge) mergeCrucibleWoolToString();
    }

    protected static void mergeRedstoneDupe() {
        ResearchItem research = API.getResearch("ALCHEMY", "ALCHEMICALDUPLICATION");
        CrucibleRecipe recipe = CrucibleAdder.addRecipe(research.key,
                new Aspects(new int[]{Config.redstoneDupe_Aspect_Machina, Config.redstoneDupe_Aspect_Potentia}, Aspect.MECHANISM, Aspect.ENERGY),
                new ItemStack(Items.redstone, Config.redstoneDupe_outputAmount),
                new ItemStack(Items.redstone));
        API.addPage(research, new ResearchPage(recipe), 7);
    }
    protected static void mergeCrucibleWoolToString() {
        ResearchItem research = API.getResearch("ALCHEMY", "ENTROPICPROCESSING");
        CrucibleRecipe recipe = CrucibleAdder.addRecipe(research.key,
                new Aspects(new int[]{Config.woolToString_Aspect_Perditio, Config.woolToString_Aspect_Fabrico}, Aspect.ENTROPY, Aspect.CRAFT),
                new ItemStack(Items.string, Config.woolToString_stringAmount),
                new ItemStack(Blocks.wool, 1, 32767));
        API.addPage(research, new ResearchPage(recipe), 4);
    }
}
