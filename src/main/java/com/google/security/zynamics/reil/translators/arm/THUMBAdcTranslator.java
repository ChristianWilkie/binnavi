/*
Copyright 2015 Google Inc. All Rights Reserved.

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
package com.google.security.zynamics.reil.translators.arm;

import com.google.security.zynamics.reil.OperandSize;
import com.google.security.zynamics.reil.ReilHelpers;
import com.google.security.zynamics.reil.ReilInstruction;
import com.google.security.zynamics.reil.translators.ITranslationEnvironment;
import com.google.security.zynamics.reil.translators.InternalTranslationException;
import com.google.security.zynamics.reil.translators.TranslationHelpers;
import com.google.security.zynamics.zylib.disassembly.IInstruction;
import com.google.security.zynamics.zylib.disassembly.IOperandTreeNode;

import java.util.List;


public class THUMBAdcTranslator extends ARMBaseTranslator {
  /**
   * ADC <Rd>, <Rm>
   * 
   * Operation:
   * 
   * Rd = Rd + Rm + C Flag N Flag = Rd[31] Z Flag = if Rd == 0 then 1 else 0 C Flag = CarryFrom(Rd +
   * Rm + C Flag) V Flag = OverflowFrom(Rd + Rm + C Flag)
   */

  @Override
  protected void translateCore(final ITranslationEnvironment environment,
      final IInstruction instruction, final List<ReilInstruction> instructions) {

    final IOperandTreeNode registerOperand1 =
        instruction.getOperands().get(0).getRootNode().getChildren().get(0);
    final IOperandTreeNode registerOperand2 =
        instruction.getOperands().get(1).getRootNode().getChildren().get(0);

    final String sourceRegister1 = (registerOperand1.getValue());
    final String sourceRegister2 = (registerOperand2.getValue());

    final OperandSize bt = OperandSize.BYTE;
    final OperandSize wd = OperandSize.WORD;
    final OperandSize dw = OperandSize.DWORD;

    long baseOffset = (instruction.getAddress().toLong() * 0x100) + instructions.size();

    final String tmpVar1 = environment.getNextVariableString();
    final String tmpVar2 = environment.getNextVariableString();
    final String tmpVar3 = environment.getNextVariableString();

    instructions
        .add(ReilHelpers.createAdd(baseOffset++, dw, sourceRegister1, dw, "C", dw, tmpVar1));
    instructions.add(ReilHelpers.createAdd(baseOffset++, dw, sourceRegister2, dw, tmpVar1, dw,
        tmpVar2));

    instructions.add(ReilHelpers.createAnd(baseOffset++, dw, tmpVar2, dw,
        String.valueOf(0xFFFFFFFFL), dw, sourceRegister1));

    // N flag
    instructions.add(ReilHelpers.createBsh(baseOffset++, dw, sourceRegister1, wd,
        String.valueOf(-31), bt, "N"));

    // Z flag
    instructions.add(ReilHelpers.createBisz(baseOffset++, dw, sourceRegister1, bt, "Z"));

    // C flag
    instructions.add(ReilHelpers.createBsh(baseOffset++, dw, tmpVar2, wd, String.valueOf(-32L), bt,
        tmpVar3));
    instructions.add(ReilHelpers.createAnd(baseOffset++, bt, tmpVar3, bt, String.valueOf(1L), bt,
        "C"));

    Helpers.addOverflow(baseOffset, environment, instructions, dw, tmpVar1, dw, sourceRegister2,
        bt, tmpVar3, "V", 32);
  }

  @Override
  public void translate(final ITranslationEnvironment environment, final IInstruction instruction,
      final List<ReilInstruction> instructions) throws InternalTranslationException {
    TranslationHelpers.checkTranslationArguments(environment, instruction, instructions, "ADC");
    translateAll(environment, instruction, "ADC", instructions);
  }
}
