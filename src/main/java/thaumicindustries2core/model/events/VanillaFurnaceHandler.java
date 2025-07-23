package thaumicindustries2core.model.events;

import nemexlib.api.events.WandEventHandler;
import nemexlib.api.items.types.BlockType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.common.items.wands.ItemWandCasting;

import static thaumicindustries2core.ThaumicIndustries2Core.logger;

public class VanillaFurnaceHandler extends WandEventHandler {

    private static Block[][][] blueprint;
    protected final String researchTag;

    private static final Block slab = Blocks.stone_slab;

    public VanillaFurnaceHandler() {
        super(new BlockType[]{new BlockType(Blocks.coal_block)});
        // ToDo Add compatibility with Charcoal Block
        // ToDo Redo the shitty constructors in WandEventHandler
        this.researchTag = "VANILLAFURNACE";
    }

    public static void initBlueprint() {
        Block cobble = Blocks.cobblestone,
                coal = Blocks.coal_block;

        blueprint = new Block[][][]
                {{{cobble, cobble, cobble}, {cobble, cobble, cobble}, {cobble, cobble, cobble}},
                 {{cobble, slab, cobble}, {slab, coal, slab}, {cobble, slab, cobble}},
                 {{cobble, slab, cobble}, {slab, cobble, slab}, {cobble, slab, cobble}}};
    }

    @Override
    public boolean performTrigger(World world, ItemStack heldItem, EntityPlayer player, int x, int y, int z, int side, int event) {
        if (world.isRemote) return false;
        if (isResearchNotComplete(player, researchTag)) return false; // Needs research to perform recipe
        ItemWandCasting wand = (ItemWandCasting) heldItem.getItem();
        if (wand.getFocus(heldItem) != null) return false; // Needs no focus equipped on the wand
        if (!player.isSneaking()) return false; // Player needs to be sneaking
        // Code for editing world
        // if (canFitVanillaFurnace(world, x, y, z) && wand.consumeAllVisCrafting(heldItem, player, VanillaFurnaceCompoundRecipes.compound, true))
        logger.info("FINAL = ", canFitVanillaFurnace(world, x, y, z));
        return false;
    }

    protected boolean canFitVanillaFurnace(World world, int x, int y, int z) {
        logger.info("Clicked at : (" + x + "," + y + "," + z + ")");
        Block block;
        boolean fit;
        for (int yy = 0; yy < 3; yy++)
            for (int xx = 0; xx < 3; xx++)
                for (int zz = 0; zz < 3; zz++) {
                    block = world.getBlock(x + xx - 1, y - yy + 1, z + zz - 1);
                    fit = block.equals(blueprint[yy][xx][zz]);
                    if (fit && block.equals(slab))
                        // If block matches blueprint AND is a slab -> check for metadata 3 (Cobblestone Slab)
                        fit = world.getBlockMetadata(x + xx - 1, y - yy + 1, z + zz - 1) == 3;
                    logger.info(block, " -> ", fit);
                    if (!fit) return false;
                }
        return true;
    }


}
