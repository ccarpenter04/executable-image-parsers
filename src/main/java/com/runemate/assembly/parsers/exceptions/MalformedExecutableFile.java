package com.runemate.assembly.parsers.exceptions;

/**
 * @author Christopher Carpenter
 */
public class MalformedExecutableFile extends RuntimeException {
    public MalformedExecutableFile(String format) {
        super(format);
    }
}
