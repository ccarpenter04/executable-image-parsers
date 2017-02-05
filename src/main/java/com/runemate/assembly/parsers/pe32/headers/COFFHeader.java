package com.runemate.assembly.parsers.pe32.headers;

import com.github.ccarpenter04.unsigned_types.UnsignedInteger;
import com.github.ccarpenter04.unsigned_types.UnsignedShort;

/**
 * @author Christopher Carpenter
 */
public class COFFHeader {
    public UnsignedShort machine;
    public UnsignedShort numberOfSections;
    public UnsignedInteger timeDateStamp;
    public UnsignedInteger pointerToSymbolTable;
    public UnsignedInteger numberOfSymbols;
    public UnsignedShort sizeOfOptionalHeader;
    public UnsignedShort characteristics;

    @Override
    public String toString() {
        return "COFFHeader{machine=" + machine +
                ", numberOfSections=" + numberOfSections +
                ", timeDateStamp=" + timeDateStamp +
                ", pointerToSymbolTable=" + pointerToSymbolTable +
                ", numberOfSymbols=" + numberOfSymbols +
                ", sizeOfOptionalHeader=" + sizeOfOptionalHeader +
                ", characteristics=" + characteristics + '}';
    }
}
