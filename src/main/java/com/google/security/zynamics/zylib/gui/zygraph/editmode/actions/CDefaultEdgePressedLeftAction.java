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
package com.google.security.zynamics.zylib.gui.zygraph.editmode.actions;

import com.google.security.zynamics.zylib.gui.zygraph.editmode.IStateAction;
import com.google.security.zynamics.zylib.yfileswrap.gui.zygraph.editmode.helpers.CEditNodeHelper;
import com.google.security.zynamics.zylib.yfileswrap.gui.zygraph.editmode.states.CEdgePressedLeftState;

import java.awt.event.MouseEvent;


public class CDefaultEdgePressedLeftAction implements IStateAction<CEdgePressedLeftState> {
  @Override
  public void execute(final CEdgePressedLeftState state, final MouseEvent event) {
    if (state.getGraph().getEditMode().getLabelEventHandler().isActive()) {
      CEditNodeHelper.removeCaret(state.getGraph());
    }
  }
}
