package com.runemate.assembly.parsers.pe32.types;

import com.runemate.assembly.parsers.pe32.headers.SectionHeader;

/**
 * @author Christopher Carpenter
 */
public class SectionTable {
    private final SectionHeader[] entries;

    /**
     * Entries in the section table are numbered starting from one (1)
     */
    public SectionTable(int entries) {
        this.entries = new SectionHeader[entries];
        for (byte number = 1; number <= entries; ++number) {
            SectionHeader header = new SectionHeader();
            header.number = number;
            this.entries[number - 1] = header;
        }
    }

    public SectionHeader[] getEntries() {
        return entries;
    }

    /**
     * Entries in the section table are numbered starting from one (1)
     */
    public SectionHeader getEntry(int number) {
        return entries[number - 1];
    }

    public SectionHeader getEntry(String name) {
        for (SectionHeader header : entries) {
            System.out.println("Looking for \"" + name + "\", current is \"" + header.name + "\"");
            if (header.name.equalsIgnoreCase(name)) {
                return header;
            }
        }
        return null;
    }
}
