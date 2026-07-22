package thaumicindustries2core.model.events;

import nemexlib.api.events.WandEventHandler;
import nemexlib.api.items.types.BlockType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumicindustries2core.model.research.CampfireCompoundRecipe;

public class CampfireHandler extends WandEventHandler {

    private Block[][][] blueprint, blueprint2;

    public CampfireHandler() {
        super(new BlockType(Blocks.carpet, 15));
        this.setTag(CampfireCompoundRecipe.tag);
        this.initBlueprint();
    }

    public void initBlueprint() {
        Block air  = Blocks.air,
              wood = Blocks.log,
              carp = Blocks.carpet,
              coal = Blocks.coal_block,
              slab = Blocks.wooden_slab;

        this.blueprint = new Block[][][] {
                {{air, air, air},    {air, air, air},    {air, air, air}},
                {{wood, slab, wood}, {wood, carp, wood}, {wood, slab, wood}},
                {{wood, wood, wood}, {slab, coal, slab}, {wood, wood, wood}}};
        this.blueprint2 = new Block[][][] {
                {{air, air, air},    {air, air, air},    {air, air, air}},
                {{wood, wood, wood}, {slab, carp, slab}, {wood, wood, wood}},
                {{wood, slab, wood}, {wood, coal, wood}, {wood, slab, wood}}};
    }

    @Override
    public boolean performTrigger(World world, ItemStack itemStack, EntityPlayer entityPlayer, int x, int y, int z, int side, int event) {
        return false;
    }
}
