package com.dieselxstellaris.integration.stellaris.mixin;

import com.dieselxstellaris.integration.create_diesel.CreateDieselCompat;
import com.st0x0ef.stellaris.common.vehicle_upgrade.FuelType;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Stellaris decides which engine can burn a bucket in FuelType.Type.getTypeBasedOnItem.
 * Buckets of CDG gasoline and diesel are reported as DIESEL, so only a DIESEL engine (our upgrade) accepts them.
 */
@Mixin(FuelType.Type.class)
public abstract class FuelTypeTypeMixin {
    @Inject(method = "getTypeBasedOnItem", at = @At("HEAD"), cancellable = true, remap = false)
    private static void dxs$alternativeFuels(Item item, CallbackInfoReturnable<FuelType.Type> cir) {
        if (CreateDieselCompat.isAlternativeFuel(item)) {
            cir.setReturnValue(FuelType.Type.DIESEL);
        }
    }
}
