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
package com.google.security.zynamics.binnavi.Gui.GraphWindows.Actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import com.google.common.base.Preconditions;
import com.google.security.zynamics.binnavi.CMain;
import com.google.security.zynamics.binnavi.Gui.GraphWindows.Implementations.CGraphSelecter;
import com.google.security.zynamics.binnavi.yfileswrap.zygraph.ZyGraph;


/**
 * Action for selecting the children of the node selection in a graph.
 */
public final class CActionSelectChildren extends AbstractAction {
  /**
   * Used for serialization.
   */
  private static final long serialVersionUID = 1832710653397253768L;

  /**
   * Graph where the selection operation is executed.
   */
  private final ZyGraph m_graph;

  /**
   * Creates a new action object.
   * 
   * @param graph Graph where the selection operation is executed.
   * @param showIcon Flag to toggle whether the action has an icon.
   */
  public CActionSelectChildren(final ZyGraph graph, final boolean showIcon) {
    super("Select successors of selection");

    m_graph = Preconditions.checkNotNull(graph, "IE02830: graph argument can not be null");

    if (showIcon) {
      putValue(SMALL_ICON, new ImageIcon(CMain.class.getResource("data/selallchild_up.jpg")));
    }

    putValue(Action.SHORT_DESCRIPTION, "Select successors of selection");
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    CGraphSelecter.selectChildrenOfSelection(m_graph);
  }
}
