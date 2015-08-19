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


public class OrisTranslator implements IInstructionTranslator {

  @Override
  public void translate(final ITranslationEnvironment environment, final IInstruction instruction,
      final List<ReilInstruction> instructions) throws InternalTranslationException {
    TranslationHelpers.checkTranslationArguments(environment, instruction, instructions, "oris");

    final IOperandTreeNode registerOperand1 =
        instruction.getOperands().get(1).getRootNode().getChildren().get(0);
    final IOperandTreeNode literalOperand1 =
        instruction.getOperands().get(2).getRootNode().getChildren().get(0);

    long baseOffset = instruction.getAddress().toLong() * 0x100;
    final String tmpLiteralValue = environment.getNextVariableString();

    instructions.add(ReilHelpers.createStr(baseOffset++, OperandSize.DWORD,
        String.valueOf(Long.valueOf(literalOperand1.getValue()) << 16), OperandSize.DWORD,
        tmpLiteralValue));
    OrGenerator.generate(baseOffset, environment, instruction, instructions, "oris",
        registerOperand1.getValue(), tmpLiteralValue, false, false, false);
  }
}
