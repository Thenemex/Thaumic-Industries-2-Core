package thaumicindustries2core.model.properties;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityEvent;
import thaumcraft.common.lib.FakeThaumcraftPlayer;

import static thaumicindustries2core.model.properties.PlayerCoreProgress.*;

public class PlayerCoreProgressHandler {

    public PlayerCoreProgressHandler() {}

    @SubscribeEvent
    public void apply(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && event.entity.worldObj != null && !(event.entity instanceof FakePlayer || event.entity instanceof FakeThaumcraftPlayer)) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (player.getExtendedProperties(propName) == null)
                player.registerExtendedProperties(propName, new PlayerCoreProgress());
        }
    }
}
