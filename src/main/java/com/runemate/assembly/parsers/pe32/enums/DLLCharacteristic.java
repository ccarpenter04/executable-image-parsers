package com.runemate.assembly.parsers.pe32.enums;

import com.github.ccarpenter04.unsigned_types.UnsignedShort;

import java.util.EnumSet;

/**
 * @author Christopher Carpenter
 */
public enum DLLCharacteristic {
    HIGH_ENTROPY_VA(0x0020, "Image can handle a high entropy 64-bit virtual address space."),
    DYNAMIC_BASE(0x0040, "DLL can be relocated at load time."),
    FORCE_INTEGRITY(0x0080, "Code Integrity checks are enforced."),
    NX_COMPAT(0x0100, "Image is NX compatible."),
    NO_ISOLATION(0x0200, "Isolation aware, but do not isolate the image."),
    NO_SEH(0x0400, "Does not use structured exception (SE) handling. No SE handler may be called in this image."),
    NO_BIND(0x0800, "Do not bind the image."),
    APPCONTAINER(0x1000, "Image must execute in an AppContainer."),
    WDM_DRIVER(0x2000, "A WDM driver."),
    GUARD_CF(0x4000, "Image supports Control Flow Guard."),
    TERMINAL_SERVER_AWARE(0x8000, "Terminal Server aware.");
    private final int mask;
    private final String description;

    DLLCharacteristic(int mask, String description) {
        this.mask = mask;
        this.description = description;
    }

    public static EnumSet<DLLCharacteristic> parse(UnsignedShort characteristics) {
        EnumSet<DLLCharacteristic> set = EnumSet.noneOf(DLLCharacteristic.class);
        int primitive = characteristics.intValue();
        for (DLLCharacteristic sc : values()) {
            if ((primitive & sc.mask) == sc.mask) {
                set.add(sc);
            }
        }
        return set;
    }
}
