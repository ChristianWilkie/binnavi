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
package com.google.security.zynamics.binnavi.Debug.Models.Trace;

import com.google.security.zynamics.binnavi.debug.models.breakpoints.BreakpointAddress;
import com.google.security.zynamics.binnavi.debug.models.trace.TraceRegister;
import com.google.security.zynamics.binnavi.debug.models.trace.TraceEventType;
import com.google.security.zynamics.binnavi.debug.models.trace.interfaces.ITraceEvent;

import java.util.List;

public final class MockEvent implements ITraceEvent {
  private final BreakpointAddress m_address;

  public MockEvent(final BreakpointAddress address) {
    m_address = address;
  }

  @Override
  public BreakpointAddress getOffset() {
    return m_address;
  }

  @Override
  public long getThreadId() {
    throw new RuntimeException("Not yet implemented");
  }

  @Override
  public TraceEventType getType() {
    throw new RuntimeException("Not yet implemented");
  }

  @Override
  public List<TraceRegister> getRegisterValues() {
    throw new IllegalStateException("Not yet implemented");
  }

}
