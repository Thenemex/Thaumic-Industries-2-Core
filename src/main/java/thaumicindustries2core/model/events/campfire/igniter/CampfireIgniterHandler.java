package thaumicindustries2core.model.events.campfire.igniter;

import connor135246.campfirebackport.common.blocks.CampfireBackportBlocks;
import nemexlib.api.thaumcraft.aspects.Aspects;

public class CampfireIgniterHandler extends ACampfireIgniter {

    public CampfireIgniterHandler(String tag, Aspects ignite) {
        super(CampfireBackportBlocks.campfire_base, tag, ignite);
    }
}
