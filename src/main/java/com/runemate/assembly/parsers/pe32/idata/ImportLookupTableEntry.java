package com.runemate.assembly.parsers.pe32.idata;

import com.github.ccarpenter04.unsigned_types.UnsignedInteger;
import com.github.ccarpenter04.unsigned_types.UnsignedLong;
import com.github.ccarpenter04.unsigned_types.UnsignedNumber;

/**
 * One of these exists per function imported
 *
 * @author Christopher Carpenter
 */
public class ImportLookupTableEntry {
    public UnsignedNumber bitfields;

    public boolean importByOrdinal() {
        if (bitfields instanceof UnsignedInteger) {
            return (bitfields.intValue() & 0x80000000) == 0x80000000;
        } else if (bitfields instanceof UnsignedLong) {
            return (bitfields.longValue() & 0x8000000000000000L) == 0x8000000000000000L;
        }
        throw new IllegalStateException("The bit fields of this entry has not been initialized.");
    }

    public int getOrdinalNumber() {
        return bitfields.intValue() & 0xffff;
    }

    public int getHintNameTableEntryRVA() {
        return bitfields.intValue() & 0x7fffffff;
    }
}
