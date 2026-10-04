package com.dieselxstellaris.integration.create_diesel;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;

/**
 * Create: Diesel Generators side. Items are matched by registry id only, so there is no compile-time
 * or class-loading dependency on that mod. Stellaris fuels its rockets with buckets, not with fluids.
 */
public final class CreateDieselCompat {
    public static final String MOD_ID = "createdieselgenerators";

    public static final ResourceLocation GASOLINE_BUCKET =
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "gasoline_bucket");
    public static final ResourceLocation DIESEL_BUCKET =
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "diesel_bucket");

    private CreateDieselCompat() {
    }

    public static boolean isAlternativeFuel(@Nullable Item item) {
        if (item == null) {
            return false;
        }
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
        return GASOLINE_BUCKET.equals(key) || DIESEL_BUCKET.equals(key);
    }
}
