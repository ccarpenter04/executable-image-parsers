package com.runemate.assembly.parsers.pe32;

import capstone.Capstone;
import capstone.X86;
import com.runemate.assembly.parsers.ExecutableFile;
import com.runemate.assembly.parsers.common.Function;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentMap;

import static capstone.X86_const.*;

/**
 * @author Christopher Carpenter
 */
public class PE32Test {

    public static void main(String... args) throws IOException {
        Path testFile = Paths.get(args[0]);
        System.out.println("Parsing the pe32 file at " + testFile);
        System.out.println("Printing results to dissassembly.txt");
        System.setOut(new PrintStream(new FileOutputStream("dissassembly.txt")));
        byte[] data = Files.readAllBytes(testFile);
        ExecutableFile pe32 = new PE32(data);
        ConcurrentMap<Long, Function> functions = pe32.getFunctions();
        for (ConcurrentMap.Entry<Long, Function> function : functions.entrySet()) {
//            System.out.println("0x" + Long.toHexString(function.getKey()) + " is called " + function.getValue().getCallers().size() + " times.");
            for (Capstone.CsInsn instruction : function.getValue().getInstructions()) {
//                print_ins_detail(instruction);
                print_insn_minimal(instruction);
                X86.OpInfo operands = (X86.OpInfo) instruction.operands;
                if (operands.op.length != 0) {
                    for (int idx = 0; idx < operands.op.length; idx++) {
                        X86.Operand op = operands.op[idx];
                        if (op.type == X86_OP_REG) {
                            System.out.printf("  operands[%d].type = REG\n", idx);
                            System.out.printf("  operands[%d].value.reg = %s\n", idx,op.value.reg);
                        } else if (op.type == X86_OP_MEM) {
                            System.out.printf("  operands[%d].type = MEM\n", idx);
                            System.out.printf("  operands[%d].value.mem.base = %s\n", idx,op.value.mem.base);
                            System.out.printf("  operands[%d].value.mem.disp = %s\n", idx, op.value.mem.disp);
                            System.out.printf("  operands[%d].value.mem.index = %s\n", idx, op.value.mem.index);
                            System.out.printf("  operands[%d].value.mem.scale = %s\n", idx, op.value.mem.scale);
                            System.out.printf("  operands[%d].value.mem.segment = %s\n", idx, op.value.mem.segment);
                        }
                    }
                }
            }
//            break;
        }
    }

    private static void print_insn_minimal(Capstone.CsInsn ins) {
        System.out.printf("0x%x:\t%s\t%s\n", ins.address, ins.mnemonic, ins.opStr);
    }

    private static void print_insn(Capstone.CsInsn ins) {
        System.out.printf("0x%x:\t%s\t%s\n", ins.address, ins.mnemonic, ins.opStr);
        X86.OpInfo operands = (X86.OpInfo) ins.operands;
        System.out.printf("\tPrefix: %s\n", array2hex(operands.prefix));
        System.out.printf("\tOpcode: %s\n", array2hex(operands.opcode));
        // print address size
        //System.out.printf("\taddr_size: %d\n", operands.addrSize);
        // print displacement value
        //System.out.printf("\tdisp: 0x%x\n", operands.disp);
    }

    private static void print_ins_detail(Capstone.CsInsn insn) {
        System.out.printf("0x%x:\t%s\t%s\n", insn.address, insn.mnemonic, insn.opStr);
        X86.OpInfo operands = (X86.OpInfo) insn.operands;
        System.out.printf("\tPrefix: %s\n", array2hex(operands.prefix));
        System.out.printf("\tOpcode: %s\n", array2hex(operands.opcode));
        // print REX prefix (non-zero value is relevant for x86_64)
        System.out.printf("\trex: 0x%x\n", operands.rex);
        // print address size
        System.out.printf("\taddr_size: %d\n", operands.addrSize);
        // print modRM byte
        System.out.printf("\tmodrm: 0x%x\n", operands.modrm);
        // print displacement value
        System.out.printf("\tdisp: 0x%x\n", operands.disp);
        // SIB is not available in 16-bit mode
        //if ((cs.mode & Capstone.CS_MODE_16) == 0) {
        // print SIB byte
        System.out.printf("\tsib: 0x%x\n", operands.sib);
        if (operands.sib != 0)
            System.out.printf("\t\tsib_base: %s\n\t\tsib_index: %s\n\t\tsib_scale: %d\n",
                    insn.regName(operands.sibBase), insn.regName(operands.sibIndex), operands.sibScale);
        //}
        if (operands.sseCC != 0)
            System.out.printf("\tsse_cc: %d\n", operands.sseCC);
        if (operands.avxCC != 0)
            System.out.printf("\tavx_cc: %d\n", operands.avxCC);
        if (operands.avxSae)
            System.out.printf("\tavx_sae: TRUE\n");
        if (operands.avxRm != 0)
            System.out.printf("\tavx_rm: %d\n", operands.avxRm);
        int count = insn.opCount(X86_OP_IMM);
        if (count > 0) {
            System.out.printf("\timm_count: %d\n", count);
            for (int i = 0; i < count; i++) {
                int index = insn.opIndex(X86_OP_IMM, i + 1);
                System.out.printf("\t\timms[%d]: 0x%x\n", i + 1, (operands.op[index].value.imm));
            }
        }
        if (operands.op.length != 0) {
            System.out.printf("\top_count: %d\n", operands.op.length);
            for (int c = 0; c < operands.op.length; c++) {
                X86.Operand i = operands.op[c];
                if (i.type == X86_OP_REG) {
                    System.out.printf("\t\toperands[%d].type: REG = %s\n", c, insn.regName(i.value.reg));
                }
                if (i.type == X86_OP_IMM) {
                    System.out.printf("\t\toperands[%d].type: IMM = 0x%x\n", c, i.value.imm);
                }
                if (i.type == X86_OP_FP) {
                    System.out.printf("\t\toperands[%d].type: FP = %f\n", c, i.value.fp);
                }
                if (i.type == X86_OP_MEM) {
                    System.out.printf("\t\toperands[%d].type: MEM\n", c);
                    String segment = insn.regName(i.value.mem.segment);
                    String base = insn.regName(i.value.mem.base);
                    String index = insn.regName(i.value.mem.index);
                    if (segment != null) {
                        System.out.printf("\t\t\toperands[%d].mem.segment: REG = %s\n", c, segment);
                    }
                    if (base != null) {
                        System.out.printf("\t\t\toperands[%d].mem.base: REG = %s\n", c, base);
                    }
                    if (index != null) {
                        System.out.printf("\t\t\toperands[%d].mem.index: REG = %s\n", c, index);
                    }
                    if (i.value.mem.scale != 1) {
                        System.out.printf("\t\t\toperands[%d].mem.scale: %d\n", c, i.value.mem.scale);
                    }
                    if (i.value.mem.disp != 0) {
                        System.out.printf("\t\t\toperands[%d].mem.disp: 0x%x\n", c, i.value.mem.disp);
                    }
                }
                // AVX broadcast type
                if (i.avx_bcast != X86_AVX_BCAST_INVALID) {
                    System.out.printf("\t\toperands[%d].avx_bcast: %d\n", c, i.avx_bcast);
                }
                // AVX zero opmask {z}
                if (i.avx_zero_opmask) {
                    System.out.printf("\t\toperands[%d].avx_zero_opmask: TRUE\n", c);
                }
                System.out.printf("\t\toperands[%d].size: %d\n", c, i.size);
            }
        }
    }

    private static String array2hex(byte[] arr) {
        String ret = "";
        for (byte anArr : arr) {
            ret += String.format("0x%02x ", anArr);
        }
        return ret;
    }
}
