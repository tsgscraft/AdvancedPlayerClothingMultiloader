package de.tsgscraft.advancedplayerclothing.platform;

public enum RagdollPart {
    HEAD,
    TORSO,
    LEFT_ARM,
    RIGHT_ARM,
    LEFT_LEG,
    RIGHT_LEG;

    public static RagdollPart fromString(String name) {
        for (RagdollPart part : RagdollPart.values()) {
            if (part.name().equalsIgnoreCase(name)) {
                return part;
            }
        }
        return null;
    }
}
