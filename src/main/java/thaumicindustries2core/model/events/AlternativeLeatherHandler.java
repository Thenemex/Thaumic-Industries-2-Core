package thaumicindustries2core.model.events;

import nemexlib.api.events.SingleBlockWithDropsHandlerWithoutUpgrade;
import nemexlib.api.items.types.BlockType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.common.config.ConfigBlocks;
import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.research.AlternativeLeatherCompoundRecipe;

public class AlternativeLeatherHandler extends SingleBlockWithDropsHandlerWithoutUpgrade {

    // ToDo Check if possible to do something that give high amount for first time users, then 1
    public AlternativeLeatherHandler() {
        super("ALTERNATIVELEATHER", new BlockType(ConfigBlocks.blockTaint, 2));
        this.setVis(AlternativeLeatherCompoundRecipe.compound);
    }

    @Override
    protected ItemStack getDrops() {
        return new ItemStack(Items.leather, Config.alternativeCompoundLeather_outputAmount);
    }
}
