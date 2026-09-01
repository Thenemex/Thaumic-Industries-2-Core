package thaumicindustries2core.model.events.campfire;

import connor135246.campfirebackport.common.blocks.BlockCampfire;
import connor135246.campfirebackport.common.blocks.CampfireBackportBlocks;
import connor135246.campfirebackport.common.tileentity.TileEntityCampfire;
import nemexlib.api.events.WandEventHandler;
import nemexlib.api.items.types.BlockType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumicindustries2core.model.research.CampfireCompoundRecipe;

public class CampfireIgniterHandler extends WandEventHandler {

    public CampfireIgniterHandler() {
        super();
        for (int i = 2; i < 6; i++)
            super.registerTriggerEvent(new BlockType(CampfireBackportBlocks.campfire_base, i));
        this.setTag(CampfireCompoundRecipe.tag);
        this.setVis(CampfireCompoundRecipe.ignite);
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
        return world.getBlock(x, y, z).equals(CampfireBackportBlocks.campfire_base);
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
