package com.runemate.assembly.parsers.pe32.headers;

import com.github.ccarpenter04.unsigned_types.UnsignedByte;
import com.github.ccarpenter04.unsigned_types.UnsignedInteger;
import com.github.ccarpenter04.unsigned_types.UnsignedNumber;
import com.github.ccarpenter04.unsigned_types.UnsignedShort;
import com.runemate.assembly.parsers.pe32.enums.DLLCharacteristic;
import com.runemate.assembly.parsers.pe32.enums.Format;
import com.runemate.assembly.parsers.pe32.enums.Subsystem;
import com.runemate.assembly.parsers.pe32.types.ImageDataDirectory;

import java.util.EnumSet;

/**
 * @author Christopher Carpenter
 */
public class OptionalHeader {
    public Format format;
    public UnsignedByte majorLinkerVersion;
    public UnsignedByte minorLinkerVersion;
    public UnsignedInteger sizeOfCode;
    public UnsignedInteger sizeOfInitializedData;
    public UnsignedInteger sizeOfUninitializedData;
    public UnsignedInteger addressOfEntryPoint;
    public UnsignedInteger baseOfCode;
    public UnsignedInteger baseOfData;
    public UnsignedNumber imageBase;
    public UnsignedInteger sectionAlignment;
    public UnsignedInteger fileAlignment;
    public UnsignedShort majorOperatingSystemVersion;
    public UnsignedShort minorOperatingSystemVersion;
    public UnsignedShort majorImageVersion;
    public UnsignedShort minorImageVersion;
    public UnsignedShort majorSubsystemVersion;
    public UnsignedShort minorSubsystemVersion;
    public UnsignedInteger win32VersionValue;
    public UnsignedInteger sizeOfImage;
    public UnsignedInteger sizeOfHeaders;
    public UnsignedInteger checkSum;
    public UnsignedShort subsystem;
    public EnumSet<DLLCharacteristic> dllCharacteristics;
    public UnsignedNumber sizeOfStackReserve;
    public UnsignedNumber sizeOfStackCommit;
    public UnsignedNumber sizeOfHeapReserve;
    public UnsignedNumber sizeOfHeapCommit;
    public UnsignedInteger loaderFlags;
    public UnsignedInteger numberOfRvaAndSizes;
    public ImageDataDirectory[] imageDataDirectoryEntries;

    public Subsystem getRequiredSubsystem() {
        return Subsystem.get(subsystem);
    }

    @Override
    public String toString() {
        return "OptionalHeader{" +
                "requiredSubsystem=" + getRequiredSubsystem() +
                ", format=" + format +
                ", numberOfRvaAndSizes=" + numberOfRvaAndSizes +
                ", dllCharacteristics=" + dllCharacteristics + '}';
    }
}
