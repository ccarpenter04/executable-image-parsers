package com.runemate.assembly.parsers;

import capstone.Capstone;
import com.runemate.assembly.parsers.common.Function;

import java.io.IOException;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * @author Christopher Carpenter
 */
public abstract class ExecutableFile {
    public ExecutableFile(byte[] data) throws IOException {
        parse(data);
    }

    /**
     * Parses the files contents and extracts the data that we need for analysis.
     */
    protected abstract void parse(byte[] data) throws IOException;

    /**
     * Gets an array of all the files instructions.
     */
    public abstract Capstone.CsInsn[] getInstructions();

    /**
     * Gets a ConcurrentMap of all the functions and their starting memory address.
     */
    public abstract ConcurrentNavigableMap<Long, Function> getFunctions();

    public abstract Function getStartFunction();
}
