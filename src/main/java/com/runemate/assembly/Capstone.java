package com.runemate.assembly;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;

public final class Capstone {
    static {
        Native.register(Capstone.class, NativeLibrary.getInstance("capstone"));
    }

    public static native int cs_close(NativeLongByReference handle);

    public static native NativeLong cs_disasm(NativeLong handle, byte[] code, NativeLong code_len,
                                              long addr, NativeLong count, PointerByReference insn);

    //cs_disasm_ex

    //cs_disasm_iter

    public static native int cs_errno(NativeLong csh);

    public static native void cs_free(Pointer p, NativeLong count);

    public static native String cs_group_name(NativeLong csh, int id);

    public static native byte cs_insn_group(NativeLong csh, Pointer insn, int id);

    public static native String cs_insn_name(NativeLong csh, int id);

    //cs_malloc

    public static native int cs_op_count(NativeLong csh, Pointer insn, int type);

    public static native int cs_op_index(NativeLong csh, Pointer insn, int type, int index);

    public static native int cs_open(int arch, int mode, NativeLongByReference handle);

    public static native int cs_option(NativeLong handle, int option, NativeLong optionValue);

    public static native String cs_reg_name(NativeLong csh, int id);

    public static native byte cs_reg_read(NativeLong csh, Pointer insn, int id);

    public static native byte cs_reg_write(NativeLong csh, Pointer insn, int id);

    //cs_strerror

    public static native boolean cs_support(int query);

    public static native int cs_version(IntByReference major, IntByReference minor);
}
