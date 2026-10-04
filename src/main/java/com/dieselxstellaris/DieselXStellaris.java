package com.dieselxstellaris;

import com.dieselxstellaris.integration.Integrations;
import com.dieselxstellaris.integration.stellaris.StellarisIntegration;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(DieselXStellaris.MOD_ID)
public final class DieselXStellaris {
    public static final String MOD_ID = "diesel_x_stellaris";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    /** Null when Stellaris or Create: Diesel Generators is not loaded. */
    private static DeferredItem<? extends Item> gasolineEngineUpgrade;

    public DieselXStellaris(IEventBus modEventBus, ModContainer modContainer) {
        if (Integrations.isAllLoaded()) {
            gasolineEngineUpgrade = StellarisIntegration.registerItems(ITEMS);
            ITEMS.register(modEventBus);
            modEventBus.addListener(this::addCreative);
        } else {
            LOGGER.warn("[{}] Stellaris loaded: {}, Create: Diesel Generators loaded: {}. Integration is disabled.",
                    MOD_ID, Integrations.isStellarisLoaded(), Integrations.isCreateDieselLoaded());
        }
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        if (!Integrations.isAllLoaded()) {
            return;
        }
        event.enqueueWork(StellarisIntegration::selfTest);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (gasolineEngineUpgrade != null && event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(gasolineEngineUpgrade);
        }
    }
}
