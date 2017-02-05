package com.runemate.assembly.parsers.common;

import com.github.ccarpenter04.unsigned_types.UnsignedByte;
import com.github.ccarpenter04.unsigned_types.UnsignedInteger;
import com.github.ccarpenter04.unsigned_types.UnsignedLong;
import com.github.ccarpenter04.unsigned_types.UnsignedShort;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author Christopher Carpenter
 */
public class ByteStream {
    private final ByteBuffer stream;

    public ByteStream(byte[] bytes) {
        this.stream = ByteBuffer.wrap(bytes);
    }

    public byte[] array() {
        return stream.array();
    }

    public int capacity() {
        return stream.capacity();
    }

    public int remaining() {
        return stream.remaining();
    }

    public int position() {
        return stream.position();
    }

    public ByteStream position(int new_position) {
        stream.position(new_position);
        return this;
    }

    public ByteOrder order() {
        return stream.order();
    }

    public ByteStream order(ByteOrder new_byte_order) {
        stream.order(new_byte_order);
        return this;
    }

    public ByteStream getBytes(byte[] destination) {
        stream.get(destination);
        return this;
    }

    public ByteStream getBytes(byte[] destination, int offset, int length) {
        stream.get(destination, offset, length);
        return this;
    }

    public Byte getByte() {
        return stream.get();
    }

    public Byte getByte(int index) {
        return stream.get(index);
    }

    public Character getChar() {
        return stream.getChar();
    }

    public Character getChar(int index) {
        return stream.getChar(index);
    }

    public Short getShort() {
        return stream.getShort();
    }

    public Short getShort(int index) {
        return stream.getShort(index);
    }

    public Integer getInt() {
        return stream.getInt();
    }

    public Integer getInt(int index) {
        return stream.getInt(index);
    }

    public Long getLong() {
        return stream.getLong();
    }

    public Long getLong(int index) {
        return stream.getLong(index);
    }

    public Float getFloat() {
        return stream.getFloat();
    }

    public Float getFloat(int index) {
        return stream.getFloat(index);
    }

    public Double getDouble() {
        return stream.getDouble();
    }

    public Double getDouble(int index) {
        return stream.getDouble(index);
    }

    public UnsignedByte getUnsignedByte() {
        return new UnsignedByte(stream.get());
    }

    public UnsignedByte getUnsignedByte(int index) {
        return new UnsignedByte(stream.get(index));
    }

    public UnsignedShort getUnsignedShort() {
        return new UnsignedShort(stream.getShort());
    }

    public UnsignedShort getUnsignedShort(int index) {
        return new UnsignedShort(stream.getShort(index));
    }

    public UnsignedInteger getUnsignedInt() {
        return new UnsignedInteger(stream.getInt());
    }

    public UnsignedInteger getUnsignedInt(int index) {
        return new UnsignedInteger(stream.getInt(index));
    }

    public UnsignedLong getUnsignedLong() {
        return new UnsignedLong(stream.getLong());
    }

    public UnsignedLong getUnsignedLong(int index) {
        return new UnsignedLong(stream.getLong(index));
    }
}