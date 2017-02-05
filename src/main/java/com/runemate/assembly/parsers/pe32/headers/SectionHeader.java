package com.runemate.assembly.parsers.pe32.headers;

import com.github.ccarpenter04.unsigned_types.UnsignedInteger;
import com.github.ccarpenter04.unsigned_types.UnsignedShort;
import com.runemate.assembly.parsers.pe32.enums.SectionFlags;

import java.util.EnumSet;

/**
 * An entry in the section table
 *
 * @author Christopher Carpenter
 */
public class SectionHeader {
    public String name;
    public UnsignedInteger virtualSize;
    public UnsignedInteger virtualAddress;
    public UnsignedInteger sizeOfRawData;
    public UnsignedInteger pointerToRawData;
    public UnsignedInteger pointerToRelocations;
    public UnsignedInteger pointerToLineNumbers;
    public UnsignedShort numberOfRelocations;
    public UnsignedShort numberOfLineNumbers;
    public EnumSet<SectionFlags> characteristics;
    public byte number;
    public byte[] rawData;

    @Override
    public String toString() {
        return "SectionHeader{number " + number + ". " +
                "name='" + name + '\'' +
                ", virtualSize=" + virtualSize +
                ", virtualAddress=" + virtualAddress +
                ", sizeOfRawData=" + sizeOfRawData +
                ", pointerToRawData=" + pointerToRawData +
                ", pointerToRelocations=" + pointerToRelocations +
                ", pointerToLineNumbers=" + pointerToLineNumbers +
                ", numberOfRelocations=" + numberOfRelocations +
                ", numberOfLineNumbers=" + numberOfLineNumbers +
                ", characteristics=" + characteristics +
                '}';
    }
}
