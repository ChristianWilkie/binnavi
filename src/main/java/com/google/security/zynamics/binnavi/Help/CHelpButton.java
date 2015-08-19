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
package com.google.security.zynamics.binnavi.Help;

import javax.swing.Action;
import javax.swing.JButton;

/**
 * Button class that can display context-sensitive help.
 */
public final class CHelpButton extends JButton implements IHelpProvider
{
	/**
	 * Used for serialization.
	 */
	private static final long serialVersionUID = -92836871085267939L;

	/**
	 * Provides context-sensitive help.
	 */
	private final IHelpInformation m_information;

	/**
	 * Creates a new help button object.
	 *
	 * @param action Action that is executed on button clicks.
	 * @param information Provides context-sensitive help.
	 */
	public CHelpButton(final Action action, final IHelpInformation information)
	{
		super(action);

		m_information = information;
	}

	@Override
	public IHelpInformation getHelpInformation()
	{
		return m_information;
	}
}
