package de.tsgscraft.advancedplayerclothing.platform;

import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.platform.services.ICompatHelper;
import de.tsgscraft.advancedplayerclothing.platform.services.INetworkHelper;
import de.tsgscraft.advancedplayerclothing.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final ICompatHelper COMPAT = load(ICompatHelper.class);
    public static final INetworkHelper NETWORK = load(INetworkHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        REFERENCE.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
