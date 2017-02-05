package com.runemate.assembly.parsers.pe32;

import capstone.Capstone;
import capstone.X86;
import com.runemate.assembly.parsers.ExecutableFile;
import com.runemate.assembly.parsers.common.Function;
import com.runemate.assembly.parsers.exceptions.MalformedExecutableFile;
import com.runemate.assembly.parsers.exceptions.UnsupportedExecutableFormat;
import com.runemate.assembly.parsers.pe32.enums.Format;
import com.runemate.assembly.parsers.pe32.enums.SectionFlags;
import com.runemate.assembly.parsers.pe32.headers.COFFHeader;
import com.runemate.assembly.parsers.pe32.headers.OptionalHeader;
import com.runemate.assembly.parsers.pe32.headers.SectionHeader;
import com.runemate.assembly.parsers.pe32.types.SectionTable;

import java.io.IOException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Christopher Carpenter
 */
public class PE32 extends ExecutableFile {
    private COFFHeader coff_header;
    private OptionalHeader optional_header;
    private SectionTable section_table;

    private Capstone.CsInsn[] instructions;
    private ConcurrentNavigableMap<Long, Function> functions;
    private Function startFunction;

    public PE32(byte[] data) throws IOException {
        super(data);
    }

    @Override
    protected void parse(byte[] data) throws IOException {
        functions = new ConcurrentSkipListMap<>();

        PEByteStream stream = new PEByteStream(data);
        stream.getDOSHeader();
        coff_header = stream.getCOFFHeader();
        int preoptional_position = stream.position();
        optional_header = stream.getOptionalHeader();
        stream.position(preoptional_position + coff_header.sizeOfOptionalHeader.intValue());
        section_table = stream.getSectionTable(coff_header.numberOfSections.intValue());

        SectionHeader section_header = null;
        for (SectionHeader sh : section_table.getEntries()) {
            if (sh.characteristics.contains(SectionFlags.CNT_CODE)) {
                if (section_header != null) {
                    throw new UnsupportedExecutableFormat("PE32 files are only supported if they have a single code section.");
                }
                section_header = sh;
            }
        }
        if (section_header == null) {
            throw new MalformedExecutableFile("PE32 files must contain a section with code.");
        }
        Capstone capstone = new Capstone(Capstone.CS_ARCH_X86, optional_header.format.equals(Format.PE32) ? Capstone.CS_MODE_32 : Capstone.CS_MODE_64);
        capstone.setDetail(Capstone.CS_OPT_ON);
        long section_address = optional_header.imageBase.longValue() + section_header.virtualAddress.longValue();
        instructions = capstone.disasm(section_header.rawData, section_address);
        //The start function is the function at the beginning of the section and is called by the operating system directly.
        startFunction = new Function(section_address);
        startFunction.getCallers().add(null);
        functions.put(section_address, startFunction);
        for (Capstone.CsInsn instruction : instructions) {
            if (instruction.mnemonic.equals("call")) {
                X86.OpValue value = ((X86.OpInfo) instruction.operands).op[0].value;
                long subroutineAddress = value.imm;
                if (subroutineAddress == 0) {
                    //TODO finish parsing import table and provide a list of imported functions that are used by each of the pe files functions.
                        /*subroutineAddress = value.mem.disp;
                        ImportedFunction function = imported_functions.get(subroutineAddress);
                        if (function == null) {
                            imported_functions.put(subroutineAddress, function = new ImportedFunction(subroutineAddress));
                        }
                        function.addUsage(instruction);*/
                } else if (subroutineAddress < section_address || subroutineAddress > section_address + section_header.sizeOfRawData.longValue()) {
                    //TODO resolve addresses of functions that are derived from stack/register values.
                    //example: call	dword ptr [eax + 0x288] so the first opcode which we're reading is referencing the register.
                } else {
                    Function function = functions.get(subroutineAddress);
                    if (function == null) {
                        functions.put(subroutineAddress, function = new Function(subroutineAddress));
                    }
                    function.getCallers().add(instruction);
                }
            }
        }
        capstone.close();
        Function function = null;
        for (Capstone.CsInsn instruction : instructions) {
            if (functions.containsKey(instruction.address)) {
                function = functions.get(instruction.address);
            }
            if (function == null) {
                throw new MalformedExecutableFile("The first instruction isn't at the same address as the starting function.");
            }
            function.getInstructions().add(instruction);
        }
    }

    @Override
    public Capstone.CsInsn[] getInstructions() {
        return instructions;
    }

    @Override
    public ConcurrentNavigableMap<Long, Function> getFunctions() {
        return functions;
    }

    @Override
    public Function getStartFunction() {
        return startFunction;
    }

    public COFFHeader getCOFFHeader() {
        return coff_header;
    }

    public OptionalHeader getOptionalHeader() {
        return optional_header;
    }

    public SectionTable getSectionTable() {
        return section_table;
    }
}
