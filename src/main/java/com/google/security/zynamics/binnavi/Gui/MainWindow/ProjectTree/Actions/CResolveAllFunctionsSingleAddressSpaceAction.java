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
package com.google.security.zynamics.binnavi.Gui.MainWindow.ProjectTree.Actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;


import com.google.common.base.Preconditions;
import com.google.security.zynamics.binnavi.Database.Interfaces.IDatabase;
import com.google.security.zynamics.binnavi.Gui.MainWindow.Implementations.CFunctionHelpers;
import com.google.security.zynamics.binnavi.disassembly.INaviAddressSpace;

public class CResolveAllFunctionsSingleAddressSpaceAction extends AbstractAction {
  /**
   * Used for serialization.
   */
  private static final long serialVersionUID = -1046563533728054166L;

  /**
   * Parent window used for dialogs.
   */
  private final JComponent m_parent;

  /**
   * Database from where the potential target functions are read.
   */
  private final IDatabase m_database;

  /**
   * The address space where the functions get resolved in.
   */
  private final INaviAddressSpace m_addressSpace;

  /**
   * Creates a new action object.
   * 
   * @param parent Parent window used for dialogs.
   * @param database Database from where the potential target functions are read.
   */
  public CResolveAllFunctionsSingleAddressSpaceAction(final JComponent parent,
      final IDatabase database, final INaviAddressSpace addressSpace) {
    super("Resolve all imported functions");

    m_parent = Preconditions.checkNotNull(parent, "IE01915: Parent argument can not be null");
    m_database = Preconditions.checkNotNull(database, "IE01916: Database argument can not be null");
    m_addressSpace =
        Preconditions.checkNotNull(addressSpace, "IE02340: module argument can not be null");
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    CFunctionHelpers.resolveAllFunctions(SwingUtilities.getWindowAncestor(m_parent), m_database,
        m_addressSpace);
  }
}
