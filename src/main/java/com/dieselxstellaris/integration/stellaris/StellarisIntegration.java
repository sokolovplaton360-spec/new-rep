package com.dieselxstellaris.integration.stellaris;

import com.dieselxstellaris.DieselXStellaris;
import com.dieselxstellaris.integration.create_diesel.CreateDieselCompat;
import com.dieselxstellaris.item.GasolineEngineUpgradeItem;
import com.st0x0ef.stellaris.common.vehicle_upgrade.FuelType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Stellaris side. Load this class only after Integrations.isAllLoaded() returned true. */
public final class StellarisIntegration {
    public static final String ITEM_ID = "gasoline_engine_upgrade";

    private StellarisIntegration() {
    }

    public static DeferredItem<? extends Item> registerItems(DeferredRegister.Items items) {
        return items.registerItem(ITEM_ID, GasolineEngineUpgradeItem::new, new Item.Properties().stacksTo(1));
    }

    /** Logs an error if the mixins did not apply, for example after a Stellaris update. */
    public static void selfTest() {
        check("gasoline", CreateDieselCompat.GASOLINE_BUCKET);
        check("diesel", CreateDieselCompat.DIESEL_BUCKET);

        float perFuel = FuelType.getMegametersTraveled(1000, FuelType.Type.DIESEL);
        float reference = FuelType.getMegametersTraveled(1000, FuelType.Type.FUEL);
        if (perFuel != reference) {
            DieselXStellaris.LOGGER.error("[{}] Range mixin was not applied: DIESEL gives {} Mm, FUEL gives {} Mm.",
                    DieselXStellaris.MOD_ID, perFuel, reference);
        }
    }

    private static void check(String name, net.minecraft.resources.ResourceLocation id) {
        Item bucket = BuiltInRegistries.ITEM.getOptional(id).orElse(null);
        if (bucket == null) {
            DieselXStellaris.LOGGER.error("[{}] Item {} ({}) was not found.", DieselXStellaris.MOD_ID, id, name);
            return;
        }
        if (FuelType.Type.getTypeBasedOnItem(bucket) != FuelType.Type.DIESEL) {
            DieselXStellaris.LOGGER.error("[{}] Fuel mixin was not applied for {}.", DieselXStellaris.MOD_ID, id);
        } else {
            DieselXStellaris.LOGGER.info("[{}] {} is accepted by the Alternative Engine.", DieselXStellaris.MOD_ID, id);
        }
    }
}
