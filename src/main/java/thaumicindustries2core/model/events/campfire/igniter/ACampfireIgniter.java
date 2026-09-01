package thaumicindustries2core.model.events.campfire.igniter;

import connor135246.campfirebackport.common.blocks.BlockCampfire;
import connor135246.campfirebackport.common.tileentity.TileEntityCampfire;
import nemexlib.api.events.WandEventHandler;
import nemexlib.api.items.types.BlockType;
import nemexlib.api.thaumcraft.aspects.Aspects;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.common.items.wands.ItemWandCasting;

public abstract class ACampfireIgniter extends WandEventHandler {

    protected final Block campfire;

    protected ACampfireIgniter(Block campfire, String tag, Aspects ignite) {
        super();
        for (int i = 2; i < 6; i++)
            super.registerTriggerEvent(new BlockType(campfire, i));
        this.setTag(tag).setVis(ignite);
        this.campfire = campfire;
    }

    @Override
    public boolean performTrigger(World world, ItemStack heldItem, EntityPlayer player, int x, int y, int z, int side, int event) {
        if (world.isRemote) return false;
        if (isResearchNotComplete(player, getTag())) return false; // Needs research to perform recipe
        ItemWandCasting wand = (ItemWandCasting) heldItem.getItem();
        if (wand.getFocus(heldItem) != null) return false; // Needs no focus equipped on the wand
        if (!player.isSneaking()) return false; // Player needs to be sneaking
        if (isMatchingBlueprint(world, x, y, z))
            return replaceStructure(world, heldItem, wand, player, x, y, z);
        return false;
    }

    protected boolean isMatchingBlueprint(World world, int x, int y, int z) {
        return world.getBlock(x, y, z).equals(campfire);
    }

    protected boolean replaceStructure(World world, ItemStack heldItem, ItemWandCasting wand, EntityPlayer player, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityCampfire) {
            TileEntityCampfire eTile = (TileEntityCampfire) tile;
            if (!eTile.isLit() && wand.consumeAllVisCrafting(heldItem, player, getVis(), false)) {
                BlockCampfire.updateCampfireBlockState(1, player, eTile);
                wand.consumeAllVisCrafting(heldItem, player, getVis(), true);
                return true;
            }
        }
        return false;
    }
}
