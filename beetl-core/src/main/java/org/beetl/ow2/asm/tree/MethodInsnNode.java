/**
 * ASM: a very small and fast Java bytecode manipulation framework
 * Copyright (c) 2000-2011 INRIA, France Telecom
 * All rights reserved.
 * <p/>
 * Redistribution and use in srccode and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of srccode code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the className of the copyright holders nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.beetl.ow2.asm.tree;

import org.beetl.ow2.asm.MethodVisitor;
import org.beetl.ow2.asm.Opcodes;
import org.beetl.ow2.asm.Type;

import java.util.Map;

/**
 * A node that represents a method instruction. A method instruction is an
 * instruction that invokes a method.
 *
 * @author Eric Bruneton
 */
public class MethodInsnNode extends AbstractInsnNode {

    /**
     * The internal className of the method's owner class (see
     * {@link Type#getInternalName() getInternalName}).
     */
    public String owner;

    /**
     * The method's className.
     */
    public String name;

    /**
     * The method's descriptor (see {@link Type}).
     */
    public String desc;

    /**
     * If the method's owner class if an interface.
     */
    public boolean itf;

    /**
     * Constructs a new {@link MethodInsnNode}.
     *
     * @param opcode
     *            the opcode of the type instruction to be constructed. This
     *            opcode must be INVOKEVIRTUAL, INVOKESPECIAL, INVOKESTATIC or
     *            INVOKEINTERFACE.
     * @param owner
     *            the internal className of the method's owner class (see
     *            {@link Type#getInternalName()
     *            getInternalName}).
     * @param name
     *            the method's className.
     * @param desc
     *            the method's descriptor (see {@link Type}).
     */
    @Deprecated
    public MethodInsnNode(final int opcode, final String owner,
                          final String name, final String desc) {
        this(opcode, owner, name, desc, opcode == Opcodes.INVOKEINTERFACE);
    }

    /**
     * Constructs a new {@link MethodInsnNode}.
     *
     * @param opcode
     *            the opcode of the type instruction to be constructed. This
     *            opcode must be INVOKEVIRTUAL, INVOKESPECIAL, INVOKESTATIC or
     *            INVOKEINTERFACE.
     * @param owner
     *            the internal className of the method's owner class (see
     *            {@link Type#getInternalName()
     *            getInternalName}).
     * @param name
     *            the method's className.
     * @param desc
     *            the method's descriptor (see {@link Type}).
     * @param itf
     *            if the method's owner class is an interface.
     */
    public MethodInsnNode(final int opcode, final String owner,
                          final String name, final String desc, final boolean itf) {
        super(opcode);
        this.owner = owner;
        this.name = name;
        this.desc = desc;
        this.itf = itf;
    }

    /**
     * Sets the opcode of this instruction.
     *
     * @param opcode
     *            the new instruction opcode. This opcode must be INVOKEVIRTUAL,
     *            INVOKESPECIAL, INVOKESTATIC or INVOKEINTERFACE.
     */
    public void setOpcode(final int opcode) {
        this.opcode = opcode;
    }

    @Override
    public int getType() {
        return METHOD_INSN;
    }

    @Override
    public void accept(final MethodVisitor mv) {
        mv.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> labels) {
        return new MethodInsnNode(opcode, owner, name, desc, itf);
    }
}
