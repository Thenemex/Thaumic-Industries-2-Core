package thaumicindustries2core.model.events.alternative;

import nemexlib.api.events.WandEventHandler;
import nemexlib.api.items.types.BlockType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumicindustries2core.model.research.alternative.AlternativeSilverwoodSaplingCompoundRecipe;

public class AlternativeSilverwoodSaplingHandler extends WandEventHandler {

    public AlternativeSilverwoodSaplingHandler() {
        super(new BlockType(ConfigBlocks.blockCustomPlant, 0));
        this.setTag(AlternativeSilverwoodSaplingCompoundRecipe.tag); // ToDo Put methods in AResearch to get tag safely, or change constructor
        this.setVis(AlternativeSilverwoodSaplingCompoundRecipe.compound);
    }

    @Override
    public boolean performTrigger(World world, ItemStack heldItem, EntityPlayer player, int x, int y, int z, int side, int event) {
        if (world.isRemote) return false;
        if (isResearchNotComplete(player, getTag())) return false; // Needs research to perform recipe
        ItemWandCasting wand = (ItemWandCasting) heldItem.getItem();
        if (wand.getFocus(heldItem) != null) return false; // Needs no focus equipped on the wand
        if (!player.isSneaking()) return false; // Player needs to be sneaking
        if (isMatchingBlueprint(world, x, y, z) && wand.consumeAllVisCrafting(heldItem, player, getVis(), true))
            return replaceStructure(world, x, y, z, side);
        return false;
    }

    // ToDo Add these methods in WandEventHandler
    protected boolean isMatchingBlueprint(World world, int x, int y, int z) {
        return world.getBlock(x, y, z).equals(ConfigBlocks.blockCustomPlant)
                && world.getBlockMetadata(x, y, z) == 0;
    }

    protected boolean replaceStructure(World world, int x, int y, int z, int ignoredSide) {
        world.setBlockMetadataWithNotify(x, y, z, 1, 3); // Transforming Greatwood:0 into Silverwood:1
        world.playSoundEffect((double)x + 0.5, (double)y + 0.5, (double)z + 0.5,
                "thaumcraft:wand", 1.0F, 1.0F);
        fireBlockParticles(world, x, y, z);
        return true;
    }
}
