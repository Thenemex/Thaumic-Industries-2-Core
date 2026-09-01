package thaumicindustries2core.model.events.campfire;

import nemexlib.api.events.WandEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CampfireSoulHandler extends WandEventHandler {

    public CampfireSoulHandler() {}

    @Override
    public boolean performTrigger(World world, ItemStack itemStack, EntityPlayer entityPlayer, int i, int i1, int i2, int i3, int i4) {
        return false;
    }
}
