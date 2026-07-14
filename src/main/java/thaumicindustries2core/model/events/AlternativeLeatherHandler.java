package thaumicindustries2core.model.events;

import nemexlib.api.events.SingleBlockWithDropsHandler;
import nemexlib.api.items.types.BlockType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.common.config.ConfigBlocks;
import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.research.AlternativeLeatherCompoundRecipe;

public class AlternativeLeatherHandler extends SingleBlockWithDropsHandler {

    // ToDo Check if possible to do something that give high amount for first time users, then 1
    public AlternativeLeatherHandler() {
        super(new BlockType(ConfigBlocks.blockTaint, 2));
        this.researchTag = "ALTERNATIVELEATHER";
        this.setVis(AlternativeLeatherCompoundRecipe.compound);
    }

    @Override
    protected ItemStack getDrops(int i, boolean b) {
        return new ItemStack(Items.leather, Config.alternativeCompoundLeather_outputAmount);
    }
}
