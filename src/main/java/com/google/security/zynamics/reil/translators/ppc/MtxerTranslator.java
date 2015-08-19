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
package com.google.security.zynamics.reil.translators.ppc;

import com.google.security.zynamics.reil.OperandSize;
import com.google.security.zynamics.reil.ReilHelpers;
import com.google.security.zynamics.reil.ReilInstruction;
import com.google.security.zynamics.reil.translators.IInstructionTranslator;
import com.google.security.zynamics.reil.translators.ITranslationEnvironment;
import com.google.security.zynamics.reil.translators.InternalTranslationException;
import com.google.security.zynamics.reil.translators.TranslationHelpers;
import com.google.security.zynamics.zylib.disassembly.IInstruction;
import com.google.security.zynamics.zylib.disassembly.IOperandTreeNode;

import java.util.List;


public class MtxerTranslator implements IInstructionTranslator {
  @Override
  public void translate(final ITranslationEnvironment environment, final IInstruction instruction,
      final List<ReilInstruction> instructions) throws InternalTranslationException {
    TranslationHelpers.checkTranslationArguments(environment, instruction, instructions, "mtxer");

    final IOperandTreeNode sourceRegister =
        instruction.getOperands().get(0).getRootNode().getChildren().get(0);

    Long baseOffset = instruction.getAddress().toLong() * 0x100;
    final OperandSize bt = OperandSize.BYTE;
    final OperandSize dw = OperandSize.DWORD;
    final String tmpVar1 = environment.getNextVariableString();
    final String strVal1 = environment.getNextVariableString();

    instructions.add(ReilHelpers.createBsh(baseOffset++, dw, sourceRegister.getValue(), bt,
        String.valueOf(-31L), dw, tmpVar1));
    instructions.add(ReilHelpers.createAnd(baseOffset++, dw, tmpVar1, dw, String.valueOf(1L), bt,
        strVal1));
    instructions.add(ReilHelpers.createStr(baseOffset++, bt, strVal1, bt,
        Helpers.XER_SUMMARY_OVERFLOW));

    instructions.add(ReilHelpers.createBsh(baseOffset++, dw, sourceRegister.getValue(), bt,
        String.valueOf(-30L), dw, tmpVar1));
    instructions.add(ReilHelpers.createAnd(baseOffset++, dw, tmpVar1, dw, String.valueOf(1L), bt,
        strVal1));
    instructions.add(ReilHelpers.createStr(baseOffset++, bt, strVal1, bt, Helpers.XER_OVERFLOW));

    instructions.add(ReilHelpers.createBsh(baseOffset++, dw, sourceRegister.getValue(), bt,
        String.valueOf(-29L), dw, tmpVar1));
    instructions.add(ReilHelpers.createAnd(baseOffset++, dw, tmpVar1, dw, String.valueOf(1L), bt,
        strVal1));
    instructions.add(ReilHelpers.createStr(baseOffset++, bt, strVal1, bt, Helpers.XER_CARRY_BIT));

    instructions.add(ReilHelpers.createAnd(baseOffset++, dw, sourceRegister.getValue(), dw,
        String.valueOf(0x0000007F), dw, Helpers.XER_COUNT_REGISTER));
  }
}
