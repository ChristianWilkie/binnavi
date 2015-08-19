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


public class THUMBLdmiaTranslator extends ARMBaseTranslator {
  @Override
  protected void translateCore(final ITranslationEnvironment environment,
      final IInstruction instruction, final List<ReilInstruction> instructions) {
    final IOperandTreeNode registerOperand1 =
        instruction.getOperands().get(0).getRootNode().getChildren().get(0).getChildren().size() == 1
            ? instruction.getOperands().get(0).getRootNode().getChildren().get(0).getChildren()
                .get(0) : instruction.getOperands().get(0).getRootNode().getChildren().get(0);
    final Boolean wback =
        instruction.getOperands().get(0).getRootNode().getChildren().get(0).getChildren().size() == 1
            ? true : false;
    /*
     * get size to see how many registers we have to load
     */
    final int registerListLength =
        instruction.getOperands().get(1).getRootNode().getChildren().get(0).getChildren().size();

    final String sourceRegister1 = (registerOperand1.getValue());

    final OperandSize bt = OperandSize.BYTE;
    final OperandSize dw = OperandSize.DWORD;

    long baseOffset = (instruction.getAddress().toLong() * 0x100) + instructions.size();

    final String tmpAddress = environment.getNextVariableString();

    instructions.add(ReilHelpers.createStr(baseOffset++, dw, sourceRegister1, dw, tmpAddress));

    for (int i = 0; i < registerListLength; i++) {
      final String currentRegisterValue =
          instruction.getOperands().get(1).getRootNode().getChildren().get(0).getChildren().get(i)
              .getValue();
      instructions.add(ReilHelpers
          .createLdm(baseOffset++, dw, tmpAddress, dw, currentRegisterValue));
      instructions.add(ReilHelpers.createAdd(baseOffset++, dw, tmpAddress, bt, String.valueOf(4L),
          dw, tmpAddress));
    }
    if (wback) {
      instructions.add(ReilHelpers.createAdd(baseOffset++, dw, sourceRegister1, dw,
          String.valueOf(registerListLength * 4), dw, sourceRegister1));
    }
  }

  @Override
  public void translate(final ITranslationEnvironment environment, final IInstruction instruction,
      final List<ReilInstruction> instructions) throws InternalTranslationException {
    TranslationHelpers.checkTranslationArguments(environment, instruction, instructions, "LDMIA");
    translateAll(environment, instruction, "LDMIA", instructions);

  }
}
