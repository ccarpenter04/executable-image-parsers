package com.runemate.assembly.parsers.pe32.enums;

import com.github.ccarpenter04.unsigned_types.UnsignedShort;

/**
 * @author Christopher Carpenter
 */
public enum Subsystem {
    IMAGE_SUBSYSTEM_UNKNOWN(0, "An unknown subsystem"),
    IMAGE_SUBSYSTEM_NATIVE(1, "Device drivers and native Windows processes"),
    IMAGE_SUBSYSTEM_WINDOWS_GUI(2, "The Windows graphical user interface (GUI) subsystem"),
    IMAGE_SUBSYSTEM_WINDOWS_CUI(3, "The Windows character subsystem"),
    IMAGE_SUBSYSTEM_POSIX_CUI(7, "The Posix character subsystem"),
    IMAGE_SUBSYSTEM_WINDOWS_CE_GUI(9, "Windows CE"),
    IMAGE_SUBSYSTEM_EFI_APPLICATION(10, "An Extensible Firmware Interface (EFI) application"),
    IMAGE_SUBSYSTEM_EFI_BOOT_SERVICE_DRIVER(11, "An EFI driver with boot services"),
    IMAGE_SUBSYSTEM_EFI_RUNTIME_DRIVER(12, "An EFI driver with run-time services"),
    IMAGE_SUBSYSTEM_EFI_ROM(13, "An EFI ROM image"),
    IMAGE_SUBSYSTEM_XBOX(14, "XBOX");
    private final int id;
    private final String description;

    Subsystem(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static Subsystem get(UnsignedShort id) {
        for (Subsystem subsystem : values()) {
            if (subsystem.id == id.intValue()) {
                return subsystem;
            }
        }
        throw null;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
