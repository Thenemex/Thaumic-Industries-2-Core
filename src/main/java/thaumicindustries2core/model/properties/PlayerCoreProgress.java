package thaumicindustries2core.model.properties;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.HashMap;
import java.util.Map;

// ToDo Pop in a NemexLib abstract class
public class PlayerCoreProgress implements IExtendedEntityProperties {

    public static final String propName = "CoreProgress";

    protected final Map<String, Boolean> hasRunCompound = new HashMap<>();

    @Override public void saveNBTData(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        for (Map.Entry<String, Boolean> entry : hasRunCompound.entrySet()) {
            NBTTagCompound entryTag = new NBTTagCompound();
            entryTag.setString("key", entry.getKey());
            entryTag.setBoolean("value", entry.getValue());
            list.appendTag(entryTag);
        }
        compound.setTag("hasRunCompound", list);
    }

    @Override public void loadNBTData(NBTTagCompound compound) {
        hasRunCompound.clear();
        if (compound.hasKey("hasRunCompound")) {
            NBTTagList list = compound.getTagList("hasRunCompound", 10); // 10 = NBTTagCompound
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound entryTag = list.getCompoundTagAt(i);
                String key = entryTag.getString("key");
                boolean value = entryTag.getBoolean("value");
                hasRunCompound.put(key, value);
            }
        }
    }

    @Override public void init(Entity entity, World world) {}

    public boolean hasRun(String tag) {
        return hasRunCompound.getOrDefault(tag, false);
    }
    public void setRun(String tag) {
        hasRunCompound.put(tag,true);
    }

    public static PlayerCoreProgress get(EntityPlayer player) {
        if (player == null) return null;
        return (PlayerCoreProgress) player.getExtendedProperties(propName); // Can be null
    }
    public static boolean hasRun(EntityPlayer player, String tag) {
        return get(player).hasRun(tag);
    }
    public static void setRun(EntityPlayer player, String tag) {
        get(player).setRun(tag);
    }
}
