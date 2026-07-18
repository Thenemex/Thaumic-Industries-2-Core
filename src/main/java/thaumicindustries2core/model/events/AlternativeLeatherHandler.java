package thaumicindustries2core.model.events;

import nemexlib.api.events.SingleBlockWithDropsHandlerWithoutUpgrade;
import nemexlib.api.items.types.BlockType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumicindustries2core.config.Config;
import thaumicindustries2core.model.research.AlternativeLeatherCompoundRecipe;

import static thaumicindustries2core.model.properties.PlayerCoreProgress.*;

public class AlternativeLeatherHandler extends SingleBlockWithDropsHandlerWithoutUpgrade {

    public AlternativeLeatherHandler() {
        super("ALTERNATIVELEATHER", new BlockType(ConfigBlocks.blockTaint, 2));
        this.setVis(AlternativeLeatherCompoundRecipe.compound);
    }

    /**
     * Private method called by <code>performTrigger()</code>
     * <p>Mainly used for code splitting</p>
     */
    @Override protected boolean dropItem(World world, ItemStack heldItem, EntityPlayer player, int x, int y, int z) {
        if (world.isRemote) return false;
        if (hasRun(player, getTag())) {
            player.addChatMessage(new ChatComponentText("Already run !"));
            return false;
        }
        if (isResearchNotComplete(player, getTag())) return false; // Needs research to perform recipe
        ItemWandCasting wand = (ItemWandCasting) heldItem.getItem();
        if (wand.getFocus(heldItem) != null) return false; // Needs no focus equipped on the wand
        if (!player.isSneaking()) return false; // Player needs to be sneaking
        // Code for editing world
        if (!isVisNeeded() || (isVisNeeded() && !wand.consumeAllVisCrafting(heldItem, player, getVis(), true)))
            return false;
        world.setBlockToAir(x, y, z);
        spawnItem(world, x, y, z, getDrops());
        setRun(player, getTag());
        return true;
    }

    @Override protected ItemStack getDrops() {
        return new ItemStack(Items.leather, Config.alternativeCompoundLeather_outputAmount);
    }
}
