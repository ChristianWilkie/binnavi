/*
Copyright 2014 Google Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.google.security.zynamics.reil.translators.x86;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.Lists;
import com.google.security.zynamics.reil.OperandSize;
import com.google.security.zynamics.reil.ReilInstruction;
import com.google.security.zynamics.reil.TestHelpers;
import com.google.security.zynamics.reil.interpreter.CpuPolicyX86;
import com.google.security.zynamics.reil.interpreter.EmptyInterpreterPolicy;
import com.google.security.zynamics.reil.interpreter.Endianness;
import com.google.security.zynamics.reil.interpreter.InterpreterException;
import com.google.security.zynamics.reil.interpreter.ReilInterpreter;
import com.google.security.zynamics.reil.interpreter.ReilRegisterStatus;
import com.google.security.zynamics.reil.translators.InternalTranslationException;
import com.google.security.zynamics.reil.translators.StandardEnvironment;
import com.google.security.zynamics.reil.translators.x86.CmovccTranslator;
import com.google.security.zynamics.reil.translators.x86.ZeroGenerator;
import com.google.security.zynamics.zylib.disassembly.ExpressionType;
import com.google.security.zynamics.zylib.disassembly.IInstruction;
import com.google.security.zynamics.zylib.disassembly.MockInstruction;
import com.google.security.zynamics.zylib.disassembly.MockOperandTree;
import com.google.security.zynamics.zylib.disassembly.MockOperandTreeNode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class CMovccTranslatorTest {
  private final ReilInterpreter interpreter = new ReilInterpreter(Endianness.LITTLE_ENDIAN,
      new CpuPolicyX86(), new EmptyInterpreterPolicy());

  private final StandardEnvironment environment = new StandardEnvironment();

  private final CmovccTranslator translator = new CmovccTranslator(new ZeroGenerator());

  private final ArrayList<ReilInstruction> instructions = new ArrayList<ReilInstruction>();

  @Test
  public void testConditionMet() throws InternalTranslationException, InterpreterException {
    interpreter.setRegister("ZF", BigInteger.ONE, OperandSize.BYTE, ReilRegisterStatus.DEFINED);
    interpreter.setRegister("eax", BigInteger.valueOf(0x2000), OperandSize.DWORD,
        ReilRegisterStatus.DEFINED);
    interpreter.setRegister("ebx", BigInteger.valueOf(0x3000), OperandSize.DWORD,
        ReilRegisterStatus.DEFINED);

    final MockOperandTree operandTree1 = new MockOperandTree();
    operandTree1.root = new MockOperandTreeNode(ExpressionType.SIZE_PREFIX, "dword");
    operandTree1.root.m_children.add(new MockOperandTreeNode(ExpressionType.IMMEDIATE_INTEGER,
        "eax"));

    final MockOperandTree operandTree2 = new MockOperandTree();
    operandTree2.root = new MockOperandTreeNode(ExpressionType.SIZE_PREFIX, "dword");
    operandTree2.root.m_children.add(new MockOperandTreeNode(ExpressionType.IMMEDIATE_INTEGER,
        "ebx"));

    final List<MockOperandTree> operands = Lists.newArrayList(operandTree1, operandTree2);

    final IInstruction instruction = new MockInstruction("cmovz", operands);

    translator.translate(environment, instruction, instructions);

    interpreter.interpret(TestHelpers.createMapping(instructions), BigInteger.valueOf(0x100));

    assertEquals(4, TestHelpers.filterNativeRegisters(interpreter.getDefinedRegisters()).size());

    assertEquals(BigInteger.valueOf(0x3000), interpreter.getVariableValue("eax"));
    assertEquals(BigInteger.valueOf(0x3000), interpreter.getVariableValue("ebx"));
    assertEquals(BigInteger.ONE, interpreter.getVariableValue("ZF"));

    assertEquals(BigInteger.ZERO, BigInteger.valueOf(interpreter.getMemorySize()));
  }

  @Test
  public void testConditionUnmet() throws InternalTranslationException, InterpreterException {
    interpreter.setRegister("ZF", BigInteger.ZERO, OperandSize.BYTE, ReilRegisterStatus.DEFINED);
    interpreter.setRegister("eax", BigInteger.valueOf(0x2000), OperandSize.DWORD,
        ReilRegisterStatus.DEFINED);
    interpreter.setRegister("ebx", BigInteger.valueOf(0x3000), OperandSize.DWORD,
        ReilRegisterStatus.DEFINED);

    final MockOperandTree operandTree1 = new MockOperandTree();
    operandTree1.root = new MockOperandTreeNode(ExpressionType.SIZE_PREFIX, "dword");
    operandTree1.root.m_children.add(new MockOperandTreeNode(ExpressionType.IMMEDIATE_INTEGER,
        "eax"));

    final MockOperandTree operandTree2 = new MockOperandTree();
    operandTree2.root = new MockOperandTreeNode(ExpressionType.SIZE_PREFIX, "dword");
    operandTree2.root.m_children.add(new MockOperandTreeNode(ExpressionType.IMMEDIATE_INTEGER,
        "ebx"));

    final List<MockOperandTree> operands = Lists.newArrayList(operandTree1, operandTree2);

    final IInstruction instruction = new MockInstruction("cmovz", operands);

    translator.translate(environment, instruction, instructions);

    interpreter.interpret(TestHelpers.createMapping(instructions), BigInteger.valueOf(0x100));

    assertEquals(4, TestHelpers.filterNativeRegisters(interpreter.getDefinedRegisters()).size());

    assertEquals(BigInteger.valueOf(0x2000), interpreter.getVariableValue("eax"));
    assertEquals(BigInteger.valueOf(0x3000), interpreter.getVariableValue("ebx"));
    assertEquals(BigInteger.ZERO, interpreter.getVariableValue("ZF"));

    assertEquals(BigInteger.ZERO, BigInteger.valueOf(interpreter.getMemorySize()));
  }
}
