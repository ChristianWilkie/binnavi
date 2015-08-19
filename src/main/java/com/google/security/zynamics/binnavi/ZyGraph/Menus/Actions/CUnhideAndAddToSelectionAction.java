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
package com.google.security.zynamics.binnavi.ZyGraph.Menus.Actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


import com.google.common.base.Preconditions;
import com.google.security.zynamics.binnavi.ZyGraph.Implementations.CProximityFunctions;
import com.google.security.zynamics.binnavi.disassembly.INaviViewNode;
import com.google.security.zynamics.binnavi.yfileswrap.zygraph.ZyGraph;
import com.google.security.zynamics.zylib.yfileswrap.gui.zygraph.proximity.ZyProximityNode;

/**
 * Action class used to unhide nodes hidden by a proxmity node and to add those nodes to the active
 * selection.
 */
public final class CUnhideAndAddToSelectionAction extends AbstractAction {
  /**
   * Used for serialization.
   */
  private static final long serialVersionUID = 3124530795015985439L;

  /**
   * Graph where the unhiding operation takes place.
   */
  private final ZyGraph m_graph;

  /**
   * Proximity node to remove.
   */
  private final ZyProximityNode<INaviViewNode> m_node;

  /**
   * Creates a new action object.
   *
   * @param graph Graph where the unhiding operation takes place.
   * @param node Proximity node to remove.
   */
  public CUnhideAndAddToSelectionAction(
      final ZyGraph graph, final ZyProximityNode<INaviViewNode> node) {
    super("Unhide and add to selection");

    Preconditions.checkNotNull(graph, "IE00946: Graph argument can't be null");

    Preconditions.checkNotNull(node, "IE00947: Node argument can't be null");

    m_graph = graph;
    m_node = node;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    new CProximityFunctions().unhideAndSelect(m_graph, m_node);
  }
}
