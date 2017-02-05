package com.runemate.assembly.parsers.macho;

import capstone.Capstone;
import com.runemate.assembly.parsers.ExecutableFile;
import com.runemate.assembly.parsers.common.Function;
import com.runemate.assembly.parsers.exceptions.UnsupportedExecutableFormat;

import java.io.IOException;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * @author Christopher Carpenter
 */
public class MachO extends ExecutableFile {
    public MachO(byte[] data) throws IOException {
        super(data);
    }

    public void parse(byte[] data) {
        throw new UnsupportedExecutableFormat("Mach-O files are not currently supported.");
    }

    @Override
    public Capstone.CsInsn[] getInstructions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConcurrentNavigableMap<Long, Function> getFunctions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Function getStartFunction() {
        throw new UnsupportedOperationException();
    }
}
