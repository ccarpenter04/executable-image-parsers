package com.runemate.assembly.parsers.pe32;

import com.github.ccarpenter04.unsigned_types.UnsignedShort;
import com.runemate.assembly.parsers.common.ByteStream;
import com.runemate.assembly.parsers.exceptions.MalformedExecutableFile;
import com.runemate.assembly.parsers.pe32.enums.DLLCharacteristic;
import com.runemate.assembly.parsers.pe32.enums.Format;
import com.runemate.assembly.parsers.pe32.enums.SectionFlags;
import com.runemate.assembly.parsers.pe32.headers.COFFHeader;
import com.runemate.assembly.parsers.pe32.headers.OptionalHeader;
import com.runemate.assembly.parsers.pe32.headers.SectionHeader;
import com.runemate.assembly.parsers.pe32.types.ImageDataDirectory;
import com.runemate.assembly.parsers.pe32.types.SectionTable;

import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * @author Christopher Carpenter
 */
public class PEByteStream extends ByteStream {
    private static final short DOS_MAGIC_NUMBER = 0x5A4D;//"MZ"
    private static final int PE_MAGIC_NUMBER = 0x00004550;//"PE\0\0"

    public PEByteStream(byte[] bytes) {
        super(bytes);
        order(ByteOrder.LITTLE_ENDIAN);
    }

    public void getDOSHeader() {
        short dos_magic_number = getShort();
        if (dos_magic_number != DOS_MAGIC_NUMBER) {
            throw new MalformedExecutableFile("DOS magic number was 0x" + Integer.toHexString(dos_magic_number) + " instead of " + DOS_MAGIC_NUMBER);
        }
        //Skip the rest of the DOS header except for getting the start address of the PE32 header
        position(0x3c);
        int e_lfanew = getInt();
        //Skip to the start of the PE32 header
        position(e_lfanew);
    }

    public COFFHeader getCOFFHeader() {
        int pe_magic_number = getInt();
        if (pe_magic_number != PE_MAGIC_NUMBER) {
            throw new MalformedExecutableFile("PE magic number was 0x" + Integer.toHexString(pe_magic_number) + " instead of " + PE_MAGIC_NUMBER);
        }
        COFFHeader ph = new COFFHeader();
        ph.machine = getUnsignedShort();
        ph.numberOfSections = getUnsignedShort();
        ph.timeDateStamp = getUnsignedInt();
        ph.pointerToSymbolTable = getUnsignedInt();
        ph.numberOfSymbols = getUnsignedInt();
        ph.sizeOfOptionalHeader = getUnsignedShort();
        ph.characteristics = getUnsignedShort();
        return ph;
    }

    public OptionalHeader getOptionalHeader() {
        OptionalHeader oh = new OptionalHeader();
        UnsignedShort magic_number = getUnsignedShort();
        oh.format = Format.get(magic_number);
        if (oh.format == null) {
            throw new MalformedExecutableFile("Optional header magic number was 0x" + Integer.toHexString(magic_number.intValue()) + " which is not a known magic number.");
        }
        oh.majorLinkerVersion = getUnsignedByte();
        oh.minorLinkerVersion = getUnsignedByte();
        oh.sizeOfCode = getUnsignedInt();
        oh.sizeOfInitializedData = getUnsignedInt();
        oh.sizeOfUninitializedData = getUnsignedInt();
        oh.addressOfEntryPoint = getUnsignedInt();
        oh.baseOfCode = getUnsignedInt();
        if (Format.PE32.equals(oh.format)) {
            oh.baseOfData = getUnsignedInt();
        }
        if (Format.PE32.equals(oh.format)) {
            oh.imageBase = getUnsignedInt();
        } else {
            oh.imageBase = getUnsignedLong();
        }
        oh.sectionAlignment = getUnsignedInt();
        oh.fileAlignment = getUnsignedInt();
        oh.majorOperatingSystemVersion = getUnsignedShort();
        oh.minorOperatingSystemVersion = getUnsignedShort();
        oh.majorImageVersion = getUnsignedShort();
        oh.minorImageVersion = getUnsignedShort();
        oh.majorSubsystemVersion = getUnsignedShort();
        oh.minorSubsystemVersion = getUnsignedShort();
        oh.win32VersionValue = getUnsignedInt();
        oh.sizeOfImage = getUnsignedInt();
        oh.sizeOfHeaders = getUnsignedInt();
        oh.checkSum = getUnsignedInt();
        oh.subsystem = getUnsignedShort();
        oh.dllCharacteristics = DLLCharacteristic.parse(getUnsignedShort());
        if (Format.PE32.equals(oh.format)) {
            oh.sizeOfStackReserve = getUnsignedInt();
            oh.sizeOfStackCommit = getUnsignedInt();
            oh.sizeOfHeapReserve = getUnsignedInt();
            oh.sizeOfHeapCommit = getUnsignedInt();
        } else {
            oh.sizeOfStackReserve = getUnsignedLong();
            oh.sizeOfStackCommit = getUnsignedLong();
            oh.sizeOfHeapReserve = getUnsignedLong();
            oh.sizeOfHeapCommit = getUnsignedLong();
        }
        oh.loaderFlags = getUnsignedInt();
        oh.numberOfRvaAndSizes = getUnsignedInt();
        oh.imageDataDirectoryEntries = new ImageDataDirectory[oh.numberOfRvaAndSizes.intValue()];
        for (int index = 0; index < oh.imageDataDirectoryEntries.length; ++index) {
            oh.imageDataDirectoryEntries[index] = new ImageDataDirectory(getUnsignedInt(), getUnsignedInt());
        }
        return oh;
    }

    public SectionTable getSectionTable(int size) {
        SectionTable sectionTable = new SectionTable(size);
        for (int number = 1; number <= size; ++number) {
            SectionHeader sh = sectionTable.getEntry(number);
            byte[] utf8_name_buffer = new byte[8];
            getBytes(utf8_name_buffer);
            sh.name = new String(utf8_name_buffer, StandardCharsets.UTF_8);
            sh.virtualSize = getUnsignedInt();
            sh.virtualAddress = getUnsignedInt();
            sh.sizeOfRawData = getUnsignedInt();
            sh.pointerToRawData = getUnsignedInt();
            sh.pointerToRelocations = getUnsignedInt();
            sh.pointerToLineNumbers = getUnsignedInt();
            sh.numberOfRelocations = getUnsignedShort();
            sh.numberOfLineNumbers = getUnsignedShort();
            sh.characteristics = SectionFlags.parse(getUnsignedInt());
        }
        for (SectionHeader sh : sectionTable.getEntries()) {
            position(sh.pointerToRawData.intValue());
            byte[] raw_data_buffer = new byte[sh.sizeOfRawData.intValue()];
            getBytes(raw_data_buffer);
            sh.rawData = raw_data_buffer;
        }
        return sectionTable;
    }
}
