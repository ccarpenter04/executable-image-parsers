package com.runemate.assembly.parsers.pe32.types;

import com.github.ccarpenter04.unsigned_types.UnsignedInteger;

/**
 * @author Christopher Carpenter
 */
public class ImageDataDirectory {
    private UnsignedInteger relativeVirtualAddress;
    private UnsignedInteger size;

    public ImageDataDirectory(UnsignedInteger relativeVirtualAddress, UnsignedInteger size) {
        this.relativeVirtualAddress = relativeVirtualAddress;
        this.size = size;
    }

    public UnsignedInteger getRelativeVirtualAddress() {
        return relativeVirtualAddress;
    }

    public UnsignedInteger getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "ImageDataDirectory{RVA=" + relativeVirtualAddress + ", size=" + size + '}';
    }
}
