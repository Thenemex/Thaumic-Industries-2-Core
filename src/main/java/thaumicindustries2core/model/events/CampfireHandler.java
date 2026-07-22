package thaumicindustries2core.model.events;

import connor135246.campfirebackport.common.blocks.CampfireBackportBlocks;
import nemexlib.api.events.WandEventHandler;
import nemexlib.api.items.types.BlockType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumicindustries2core.model.research.CampfireCompoundRecipe;

public class CampfireHandler extends WandEventHandler {

    private BlockType[][][] blueprint, blueprint2;

    public CampfireHandler() {
        super(new BlockType(Blocks.carpet, 15));
        this.setTag(CampfireCompoundRecipe.tag);
        this.initBlueprint();
    }

    public void initBlueprint() {
        BlockType woodF = new BlockType(Blocks.log, 8),
                  woodL = new BlockType(Blocks.log, 4),
                  carpB = new BlockType(Blocks.carpet, 15),
                  coalB = new BlockType(Blocks.coal_block),
                  slabW = new BlockType(Blocks.wooden_slab);

        this.blueprint = new BlockType[][][] {
                {{woodF, slabW, woodF}, {woodF, carpB, woodF}, {woodF, slabW, woodF}},
                {{woodL, woodL, woodL}, {slabW, coalB, slabW}, {woodL, woodL, woodL}}};
        this.blueprint2 = new BlockType[][][] {
                {{woodF, woodF, woodF}, {slabW, carpB, slabW}, {woodF, woodF, woodF}},
                {{woodL, slabW, woodL}, {woodL, coalB, woodL}, {woodL, slabW, woodL}}};
    }

    @Override
    public boolean performTrigger(World world, ItemStack heldItem, EntityPlayer player, int x, int y, int z, int side, int event) {
        if (world.isRemote) return false;
        if (isResearchNotComplete(player, getTag())) return false; // Needs research to perform recipe
        ItemWandCasting wand = (ItemWandCasting) heldItem.getItem();
        if (wand.getFocus(heldItem) != null) return false; // Needs no focus equipped on the wand
        if (!player.isSneaking()) return false; // Player needs to be sneaking
        if ((isMatchingBlueprint(world, x, y, z, blueprint) || isMatchingBlueprint(world, x, y, z, blueprint2))
                && wand.consumeAllVisCrafting(heldItem, player, getVis(), true))
            return replaceStructure(world, x, y, z, side); // Delete all blocks and place the furnace at y-1
        return false;
    }

    protected boolean isMatchingBlueprint(World world, int x, int y, int z, BlockType[][][] blueprint) {
        boolean fit;
        for (int yy = 0; yy < 2; yy++)
            for (int xx = 0; xx < 3; xx++)
                for (int zz = 0; zz < 3; zz++) {
                    Block block = world.getBlock(x + xx - 1, y - yy + 1, z + zz - 1);
                    BlockType type = blueprint[yy][xx][zz];
                    fit = block.equals(type.block());
                    if (fit && (block.equals(Blocks.log) || block.equals(Blocks.carpet)))
                        // If block matches blueprint AND is a specific block -> check for metadata
                        fit = world.getBlockMetadata(x + xx - 1, y - yy + 1, z + zz - 1) == type.meta();
                    if (!fit) return false;
                }
        return true;
    }

    protected boolean replaceStructure(World world, int x, int y, int z, int side) {
        for (int yy = 0; yy < 3; yy++)
            for (int xx = 0; xx < 3; xx++)
                for (int zz = 0; zz < 3; zz++)
                    world.setBlockToAir(x + xx - 1, y - yy + 1, z + zz - 1);
        world.setBlock(x, y - 1, z, CampfireBackportBlocks.campfire_base);
        world.setBlockMetadataWithNotify(x, y - 1, z, side,3);
        world.playSoundEffect((double)x + 0.5, (double)y + 0.5, (double)z + 0.5,
                "thaumcraft:wand", 1.0F, 1.0F);
        return true;
    }
}
