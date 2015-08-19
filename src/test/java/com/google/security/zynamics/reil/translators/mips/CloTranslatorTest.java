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
package com.google.security.zynamics.reil.translators.mips;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.Lists;
import com.google.security.zynamics.reil.OperandSize;
import com.google.security.zynamics.reil.ReilInstruction;
import com.google.security.zynamics.reil.TestHelpers;
import com.google.security.zynamics.reil.interpreter.CpuPolicyMips;
import com.google.security.zynamics.reil.interpreter.EmptyInterpreterPolicy;
import com.google.security.zynamics.reil.interpreter.Endianness;
import com.google.security.zynamics.reil.interpreter.InterpreterException;
import com.google.security.zynamics.reil.interpreter.ReilInterpreter;
import com.google.security.zynamics.reil.interpreter.ReilRegisterStatus;
import com.google.security.zynamics.reil.translators.InternalTranslationException;
import com.google.security.zynamics.reil.translators.StandardEnvironment;
import com.google.security.zynamics.reil.translators.mips.CloTranslator;
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
public class CloTranslatorTest {
  private final ReilInterpreter interpreter = new ReilInterpreter(Endianness.LITTLE_ENDIAN,
      new CpuPolicyMips(), new EmptyInterpreterPolicy());

  private final StandardEnvironment environment = new StandardEnvironment();

  private final CloTranslator translator = new CloTranslator();

  private final ArrayList<ReilInstruction> instructions = new ArrayList<ReilInstruction>();

  @Test
  public void testCloOne() throws InternalTranslationException, InterpreterException {
    interpreter.setRegister("$t7", BigInteger.valueOf(0x0L), OperandSize.DWORD,
        ReilRegisterStatus.DEFINED);
    interpreter.setRegister("$t6", BigInteger.valueOf(0x0L), OperandSize.DWORD,
        ReilRegisterStatus.DEFINED);

    final MockOperandTree operandTree1 = new MockOperandTree();
    operandTree1.root = new MockOperandTreeNode(ExpressionType.SIZE_PREFIX, "b4");
    operandTree1.root.m_children.add(new MockOperandTreeNode(ExpressionType.REGISTER, "$t7"));

    final MockOperandTree operandTree2 = new MockOperandTree();
    operandTree2.root = new MockOperandTreeNode(ExpressionType.SIZE_PREFIX, "b4");
    operandTree2.root.m_children.add(new MockOperandTreeNode(ExpressionType.REGISTER, "$t6"));

    final List<MockOperandTree> operands = Lists.newArrayList(operandTree1, operandTree2);// ,
                                                                                          // operandTree3);

    final IInstruction instruction = new MockInstruction("clo", operands);

    translator.translate(environment, instruction, instructions);

    interpreter.interpret(TestHelpers.createMapping(instructions), BigInteger.valueOf(0x100L));

    // check correct outcome

    assertEquals(3, TestHelpers.filterNativeRegisters(interpreter.getDefinedRegisters()).size());
    assertEquals(BigInteger.valueOf(0x0L), interpreter.getVariableValue("$t7"));
    assertEquals(BigInteger.valueOf(0x0L), interpreter.getVariableValue("$t6"));
    assertEquals(BigInteger.ZERO, BigInteger.valueOf(interpreter.getMemorySize()));
  }

  @Test
  public void testCloThree() throws InternalTranslationException, InterpreterException {
    interpreter.setRegister("$t7", BigInteger.valueOf(0x0L), OperandSize.DWORD,
        ReilRegisterStatus.DEFINED);
    interpreter.setRegister("$t6", BigInteger.valueOf(0xFFFE1DC0L), OperandSize.DWORD,
        ReilRegisterStatus.DEFINED);

    final MockOperandTree operandTree1 = new MockOperandTree();
    operandTree1.root = new MockOperandTreeNode(ExpressionType.SIZE_PREFIX, "b4");
    operandTree1.root.m_children.add(new MockOperandTreeNode(ExpressionType.REGISTER, "$t7"));

    final MockOperandTree operandTree2 = new MockOperandTree();
    operandTree2.root = new MockOperandTreeNode(ExpressionType.SIZE_PREFIX, "b4");
    operandTree2.root.m_children.add(new MockOperandTreeNode(ExpressionType.REGISTER, "$t6"));

    final List<MockOperandTree> operands = Lists.newArrayList(operandTree1, operandTree2);// ,
                                                                                          // operandTree3);

    final IInstruction instruction = new MockInstruction("clo", operands);

    translator.translate(environment, instruction, instructions);

    interpreter.interpret(TestHelpers.createMapping(instructions), BigInteger.valueOf(0x100L));

    // check correct outcome

    assertEquals(3, TestHelpers.filterNativeRegisters(interpreter.getDefinedRegisters()).size());
    assertEquals(BigInteger.valueOf(0xFL), interpreter.getVariableValue("$t7"));
    assertEquals(BigInteger.valueOf(0xFFFE1DC0L), interpreter.getVariableValue("$t6"));
    assertEquals(BigInteger.ZERO, BigInteger.valueOf(interpreter.getMemorySize()));
  }

  @Test
  public void testCloTwo() throws InternalTranslationException, InterpreterException {
    interpreter.setRegister("$t7", BigInteger.valueOf(0x0L), OperandSize.DWORD,
        ReilRegisterStatus.DEFINED);
    interpreter.setRegister("$t6", BigInteger.valueOf(0xFFFFFFFFL), OperandSize.DWORD,
        ReilRegisterStatus.DEFINED);

    final MockOperandTree operandTree1 = new MockOperandTree();
    operandTree1.root = new MockOperandTreeNode(ExpressionType.SIZE_PREFIX, "b4");
    operandTree1.root.m_children.add(new MockOperandTreeNode(ExpressionType.REGISTER, "$t7"));

    final MockOperandTree operandTree2 = new MockOperandTree();
    operandTree2.root = new MockOperandTreeNode(ExpressionType.SIZE_PREFIX, "b4");
    operandTree2.root.m_children.add(new MockOperandTreeNode(ExpressionType.REGISTER, "$t6"));

    final List<MockOperandTree> operands = Lists.newArrayList(operandTree1, operandTree2);// ,
                                                                                          // operandTree3);

    final IInstruction instruction = new MockInstruction("clo", operands);

    translator.translate(environment, instruction, instructions);

    interpreter.interpret(TestHelpers.createMapping(instructions), BigInteger.valueOf(0x100L));

    // check correct outcome

    assertEquals(3, TestHelpers.filterNativeRegisters(interpreter.getDefinedRegisters()).size());
    assertEquals(BigInteger.valueOf(0x20L), interpreter.getVariableValue("$t7"));
    assertEquals(BigInteger.valueOf(0xFFFFFFFFL), interpreter.getVariableValue("$t6"));
    assertEquals(BigInteger.ZERO, BigInteger.valueOf(interpreter.getMemorySize()));
  }
}
