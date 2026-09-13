package thaumicindustries2core.model.events.alternative;

import nemexlib.api.events.WandEventHandler;
import nemexlib.api.items.types.BlockType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumicindustries2core.model.research.alternative.AlternativeSilverwoodSaplingCompoundRecipe;

import static thaumicindustries2core.model.properties.PlayerCoreProgress.*;

public class AlternativeSilverwoodSaplingHandler extends WandEventHandler {

    public AlternativeSilverwoodSaplingHandler() {
        super(new BlockType(ConfigBlocks.blockCustomPlant, 0));
        this.setTag(AlternativeSilverwoodSaplingCompoundRecipe.tag); // ToDo Put methods in AResearch to get tag safely, or change constructor
        this.setVis(AlternativeSilverwoodSaplingCompoundRecipe.compound);
    }

    @Override
    public boolean performTrigger(World world, ItemStack heldItem, EntityPlayer player, int x, int y, int z, int side, int event) {
        if (world.isRemote) return false;
        if (hasRun(player, getTag())) return false;
        if (isResearchNotComplete(player, getTag())) return false; // Needs research to perform recipe
        ItemWandCasting wand = (ItemWandCasting) heldItem.getItem();
        if (wand.getFocus(heldItem) != null) return false; // Needs no focus equipped on the wand
        if (!player.isSneaking()) return false; // Player needs to be sneaking
        if (!wand.consumeAllVisCrafting(heldItem, player, getVis(), true))
            return false;
        world.setBlockMetadataWithNotify(x, y, z, 1, 3); // Transforming Greatwood:0 into Silverwood:1
        world.playSoundEffect((double)x + 0.5, (double)y + 0.5, (double)z + 0.5,
                "thaumcraft:wand", 1.0F, 1.0F);
        fireBlockParticles(world, x, y, z);
        setRun(player, getTag());
        return false;
    }
}
