package thaumicindustries2core.model.integrations.nested;

import com.jaquadro.minecraft.gardentrees.core.recipe.WoodPostRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.forbidden.items.ForbiddenItems;
import nemexlib.api.integrations.ACompat;
import net.minecraft.item.Item;
import thaumcraft.common.config.ConfigItems;
import witchinggadgets.common.WGContent;

import static thaumicindustries2core.model.config.ConfigIntegrations.*;

public class GardenTreesNestedCompat extends ACompat {

    public GardenTreesNestedCompat(String mod) {
        super(mod);
    }

    @Override
    public void loadIntegration() {
        // Thaumcraft (Thaumium, Enhanced Thaumium, Voidmetal)
        add(ConfigItems.itemAxeThaumium, ConfigItems.itemAxeElemental, ConfigItems.itemAxeVoid);
        // Forbidden Magic (Chameleon)
        if (Loader.isModLoaded(fm)) add(ForbiddenItems.morphAxe);
        // Tainted Magic (Shadowmetal)
        add(tm, "ItemShadowmetalAxe");
        // Witching Gadgets (Primordial)
        if (Loader.isModLoaded(wg)) add(WGContent.ItemPrimordialAxe);
        // Thaumic Bases (Thauminite)
        add(tb, "thauminiteAxe");
        // Thaumic Tinkerer (Ichorium, Awakened Ichorium)
        // add(tt, "ichorAxe", "ichorAxeGem");
        // Twilight Forest (Steeleaf, Knightly, Ironwood, Minotaur)
        add(tw, "item.steeleafAxe", "item.knightlyAxe", "item.ironwoodAxe", "item.minotaurAxe");
        // Blood Magic (Bound)
        add(bm, "boundAxe");
        // Blood Arsenal (Blood Infused Wood/Iron/Diamond)
        // add(ba, "blood_infused_axe_wood", "blood_infused_axe_iron", "blood_infused_axe_diamond");
        // Sanguimancy (Corrupted)
        // add(sg, "corruptedAxe");
        // Botania (Manasteel, Elementium, Terra)
        add(bt, "manasteelAxe", "elementiumAxe", "terraAxe");
        // ToDo Check how to simulate durability + add on after: load
    }

    public static void add(Item axe) {
        WoodPostRecipe.axeList.add(axe);
    }
    public static void add(Item ... axes) {
        for (Item axe : axes) add(axe);
    }
    public static void add(String modid, String itemName) {
        if (Loader.isModLoaded(modid)) add(GameRegistry.findItem(modid, itemName));
    }
    public static void add(String modid, String ... itemNames) {
        if (!Loader.isModLoaded(modid)) return;
        for (String itemName : itemNames)
            add(GameRegistry.findItem(modid, itemName));
    }

}
