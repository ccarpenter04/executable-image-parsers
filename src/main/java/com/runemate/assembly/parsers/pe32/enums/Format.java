package com.runemate.assembly.parsers.pe32.enums;

import com.github.ccarpenter04.unsigned_types.UnsignedShort;

/**
 * @author Christopher Carpenter
 */
public enum Format {
    PE32(0x10b),
    PE32_PLUS(0x20b),;
    private final int magic_number;

    Format(int magic_number) {
        this.magic_number = magic_number;
    }

    public static Format get(UnsignedShort magic) {
        for (Format type : values()) {
            if (magic.intValue() == type.magic_number) {
                return type;
            }
        }
        return null;
    }
}
