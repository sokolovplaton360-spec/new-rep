package com.dieselxstellaris.item;

import com.st0x0ef.stellaris.common.items.VehicleUpgradeItem;
import com.st0x0ef.stellaris.common.vehicle_upgrade.FuelType;
import com.st0x0ef.stellaris.common.vehicle_upgrade.MotorUpgrade;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * Motor upgrade for Stellaris rockets. Uses Stellaris' DIESEL motor type, which has no item of its own.
 * The mixins in integration.stellaris.mixin make that type accept Create: Diesel Generators buckets
 * and give it the same range and fuel consumption as regular Stellaris fuel.
 */
public class GasolineEngineUpgradeItem extends VehicleUpgradeItem {
    public GasolineEngineUpgradeItem(Item.Properties properties) {
        super(properties, new MotorUpgrade(FuelType.Type.DIESEL));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("item.diesel_x_stellaris.gasoline_engine_upgrade.tooltip")
                .withStyle(ChatFormatting.GRAY));
    }
}
