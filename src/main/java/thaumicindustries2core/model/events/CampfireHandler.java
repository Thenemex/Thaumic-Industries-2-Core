package thaumicindustries2core.model.events;

import nemexlib.api.events.WandEventHandler;
import nemexlib.api.items.types.BlockType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumicindustries2core.model.research.CampfireCompoundRecipe;

import static thaumicindustries2core.ThaumicIndustries2Core.logger;

public class CampfireHandler extends WandEventHandler {

    private BlockType[][][] blueprint, blueprint2;

    public CampfireHandler() {
        super(new BlockType(Blocks.carpet, 15));
        this.setTag(CampfireCompoundRecipe.tag);
        this.setVis(CampfireCompoundRecipe.compound);
        this.initBlueprint();
        // ToDo Add compatibility with Charcoal block
    }

    public void initBlueprint() {
        BlockType woodF = new BlockType(Blocks.log, 8),
                  woodL = new BlockType(Blocks.log, 4),
                  carpB = new BlockType(Blocks.carpet, 15),
                  coalB = new BlockType(Blocks.coal_block),
                  slabW = new BlockType(Blocks.wooden_slab);

        this.blueprint = new BlockType[][][] {
                {{woodL, slabW, woodL}, {woodL, carpB, woodL}, {woodL, slabW, woodL}},
                {{woodF, woodF, woodF}, {slabW, coalB, slabW}, {woodF, woodF, woodF}}};
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
        if ((isMatchingBlueprint(world, x, y, z, blueprint) || isMatchingBlueprint(world, x, y, z, blueprint2)) && wand.consumeAllVisCrafting(heldItem, player, getVis(), true))
            return replaceStructure(world, x, y, z); // Delete all blocks and place the block at y-1
        return false;
    }

    protected boolean isMatchingBlueprint(World world, int x, int y, int z, BlockType[][][] blueprint) {
        boolean fit;
        int meta;
        for (int yy = 0; yy < 2; yy++) {
            if (yy != 0) logger.info("- - - - -");
            for (int xx = 0; xx < 3; xx++)
                for (int zz = 0; zz < 3; zz++) {
                    Block block = world.getBlock(x + xx - 1, y - yy, z + zz - 1);
                    BlockType type = blueprint[yy][xx][zz];
                    logger.info(block.getLocalizedName(), world.getBlockMetadata(x + xx - 1, y - yy, z + zz - 1), "|", x + xx - 1, y - yy + 1, z + zz - 1, "|", type.block().getLocalizedName(), type.meta());
                    fit = block.equals(type.block()) || (block.equals(Blocks.log2) && type.block().equals(Blocks.log));
                    if (fit && (block.equals(Blocks.log) || block.equals(Blocks.log2))) {
                        meta = world.getBlockMetadata(x + xx - 1, y - yy, z + zz - 1) - type.meta();
                        fit = meta >= 0 && meta < 4;
                    } else if (fit && block.equals(Blocks.carpet))
                        fit = world.getBlockMetadata(x + xx - 1, y - yy, z + zz - 1) == type.meta();
                    if (!fit) return false;
                }
        }
        logger.info("- - - - - - - - - -");
        return true;
    }

    protected boolean replaceStructure(World world, int x, int y, int z) {
        /**
        for (int yy = 0; yy < 3; yy++)
            for (int xx = 0; xx < 3; xx++)
                for (int zz = 0; zz < 3; zz++)
                    world.setBlockToAir(x + xx - 1, y - yy + 1, z + zz - 1);
        world.setBlock(x, y - 1, z, CampfireBackportBlocks.campfire_base);
         **/
        world.playSoundEffect((double)x + 0.5, (double)y + 0.5, (double)z + 0.5,
                "thaumcraft:wand", 1.0F, 1.0F);
        return true;
    }
}
