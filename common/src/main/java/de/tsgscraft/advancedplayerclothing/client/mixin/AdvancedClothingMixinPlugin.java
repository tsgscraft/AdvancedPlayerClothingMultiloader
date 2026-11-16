package de.tsgscraft.advancedplayerclothing.client.mixin;

import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.platform.Services;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class AdvancedClothingMixinPlugin implements IMixinConfigPlugin {

    private boolean fmgModLoaded;
    private boolean rdModLoaded;

    @Override
    public void onLoad(String mixinPackage) {
        fmgModLoaded = Services.PLATFORM.isModLoading(REFERENCE.fmg_MODID);
        REFERENCE.LOG.info("FMG mod loaded: {}", fmgModLoaded);
        rdModLoaded = Services.PLATFORM.isModLoading(REFERENCE.rd_MODID);
        REFERENCE.LOG.info("RD mod loaded: {}", rdModLoaded);
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        REFERENCE.LOG.info("Checking if mixin {} should be applied for target class {}", mixinClassName, targetClassName);
        if (mixinClassName.equals("de.tsgscraft.advancedplayerclothing.client.mixin.fmg.GenderLayerMixin")) {
            return fmgModLoaded;
        }
        if (mixinClassName.equals("de.tsgscraft.advancedplayerclothing.client.mixin.rd.RagdollPartBlockEntityRendererMixin")) {
            return rdModLoaded;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
