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
package com.google.security.zynamics.binnavi.API.disassembly;

import com.google.security.zynamics.binnavi.API.disassembly.ITraceListener;
import com.google.security.zynamics.binnavi.API.disassembly.Trace;
import com.google.security.zynamics.binnavi.API.disassembly.TraceEvent;

public final class MockTraceListener implements ITraceListener {
  public String events = "";

  @Override
  public void addedEvent(final Trace trace, final TraceEvent event) {
    events += "addedEvent;";
  }

  @Override
  public void changedDescription(final Trace trace, final String description) {
    events += "changedDescription;";
  }

  @Override
  public void changedName(final Trace trace, final String name) {
    events += "changedName;";
  }
}
