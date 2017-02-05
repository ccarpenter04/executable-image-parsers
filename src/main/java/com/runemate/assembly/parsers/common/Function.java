package com.runemate.assembly.parsers.common;

import capstone.Capstone;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

/**
 * @author Christopher Carpenter
 */
public final class Function {

    private final long address;
    private final List<Capstone.CsInsn> callers = new CopyOnWriteArrayList<>();
    private final List<Capstone.CsInsn> instructions = new CopyOnWriteArrayList<>();

    public Function(long address) {
        this.address = address;
    }

    public long getAddress() {
        return address;
    }

    public List<Capstone.CsInsn> getCallers() {
        return callers;
    }

    public List<Capstone.CsInsn> getInstructions() {
        return instructions;
    }

    /**
     * Gets the instruction at the given index.
     *
     * @param idx The index to fetch at.
     * @return The instruction at the given index.
     */
    public Capstone.CsInsn insnAt(int idx) {
        return instructions.get(idx);
    }

    /**
     * Finds an instruction in this container matching the given predicate.
     *
     * @param predicate The predicate to match.
     * @return An instruction in this container matching the given predicate.
     */
    public Capstone.CsInsn find(Predicate<Capstone.CsInsn> predicate) {
        return instructions.stream().filter(predicate).findFirst().orElse(null);
    }

    /**
     * Finds an instruction in this container with the given memory address.
     *
     * @param address The address to look for.
     * @return An instruction in this container with the given memory address.
     */
    public Capstone.CsInsn findByAddr(long address) {
        return find(insn -> insn.address == address);
    }

    /**
     * Checks whether or not this function is empty.
     *
     * @return <tt>true</tt> if this function is empty, otherwise <tt>false</tt>.
     */
    public boolean isEmpty() {
        return instructions.isEmpty();
    }

    /**
     * Retrieves the instruction count for this function.
     *
     * @return The instruction count for this function.
     */
    public int size() {
        return instructions.size();
    }
}
