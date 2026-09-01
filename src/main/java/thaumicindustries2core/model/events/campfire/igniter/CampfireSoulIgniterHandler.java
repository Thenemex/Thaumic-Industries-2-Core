package thaumicindustries2core.model.events.campfire.igniter;

import connor135246.campfirebackport.common.blocks.CampfireBackportBlocks;
import nemexlib.api.thaumcraft.aspects.Aspects;

public class CampfireSoulIgniterHandler extends ACampfireIgniter {

    public CampfireSoulIgniterHandler(String tag, Aspects ignite) {
        super(CampfireBackportBlocks.soul_campfire_base, tag, ignite);
    }
}
