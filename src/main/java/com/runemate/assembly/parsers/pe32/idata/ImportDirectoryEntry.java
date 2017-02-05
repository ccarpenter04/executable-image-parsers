package com.runemate.assembly.parsers.pe32.idata;

import com.github.ccarpenter04.unsigned_types.UnsignedInteger;

/**
 * Each entry corresponds to a specific dll
 *
 * @author Christopher Carpenter
 */
public class ImportDirectoryEntry {
    public UnsignedInteger lookupTableRVA;
    public UnsignedInteger timeDateStamp;
    public UnsignedInteger forwarderChain;
    public UnsignedInteger nameRVA;
    public UnsignedInteger addressTableRVA;

    public boolean isEmpty() {
        return lookupTableRVA.longValue() == 0
                && timeDateStamp.longValue() == 0
                && forwarderChain.longValue() == 0
                && nameRVA.longValue() == 0
                && addressTableRVA.longValue() == 0;
    }
}
