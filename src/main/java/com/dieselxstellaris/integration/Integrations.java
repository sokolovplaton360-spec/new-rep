package com.dieselxstellaris.integration;

import net.neoforged.fml.ModList;

/** Single place for mod presence checks. Never touches classes of the other mods. */
public final class Integrations {
    public static final String STELLARIS = "stellaris";
    public static final String CREATE_DIESEL_GENERATORS = "createdieselgenerators";

    private Integrations() {
    }

    public static boolean isStellarisLoaded() {
        return ModList.get().isLoaded(STELLARIS);
    }

    public static boolean isCreateDieselLoaded() {
        return ModList.get().isLoaded(CREATE_DIESEL_GENERATORS);
    }

    public static boolean isAllLoaded() {
        return isStellarisLoaded() && isCreateDieselLoaded();
    }
}
