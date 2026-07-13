package thaumicindustries2core.model.events;

import nemexlib.api.events.SingleBlockWithDropsHandler;
import nemexlib.api.items.types.BlockType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.common.config.ConfigBlocks;
import thaumicindustries2core.config.Config;

public class AlternativeLeatherHandler extends SingleBlockWithDropsHandler {

    public AlternativeLeatherHandler() {
        super(new BlockType(ConfigBlocks.blockTaint, 2));
        // ToDo Set research tag
        // ToDo Set Vis
    }

    @Override
    protected ItemStack getDrops(int i, boolean b) {
        return new ItemStack(Items.leather, Config.alternativeCompoundLeather_outputAmount);
    }
}
