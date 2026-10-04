package com.dieselxstellaris.integration.stellaris.mixin;

import com.st0x0ef.stellaris.common.data.planets.Planet;
import com.st0x0ef.stellaris.common.vehicle_upgrade.FuelType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * In Stellaris 1.4.25 the DIESEL type has zero range and an infinite fuel requirement.
 * Both calculations are redirected to the FUEL type so the Alternative Engine behaves like standard fuel.
 */
@Mixin(FuelType.class)
public abstract class FuelTypeMixin {
    @Inject(method = "getMegametersTraveled", at = @At("HEAD"), cancellable = true, remap = false)
    private static void dxs$rangeLikeFuel(int fuel, FuelType.Type type, CallbackInfoReturnable<Float> cir) {
        if (type == FuelType.Type.DIESEL) {
            cir.setReturnValue(FuelType.getMegametersTraveled(fuel, FuelType.Type.FUEL));
        }
    }

    @Inject(method = "getFuelNeededToGoOnPlanet", at = @At("HEAD"), cancellable = true, remap = false)
    private static void dxs$consumptionLikeFuel(Planet from, Planet to, FuelType.Type type, CallbackInfoReturnable<Float> cir) {
        if (type == FuelType.Type.DIESEL) {
            cir.setReturnValue(FuelType.getFuelNeededToGoOnPlanet(from, to, FuelType.Type.FUEL));
        }
    }
}
