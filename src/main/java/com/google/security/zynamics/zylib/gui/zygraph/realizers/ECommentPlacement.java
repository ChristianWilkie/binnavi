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
package com.google.security.zynamics.zylib.gui.zygraph.realizers;

public enum ECommentPlacement {
  ABOVE_LINE, BEHIND_LINE;

  public static int getOrdinal(final ECommentPlacement placement) {
    return placement == ECommentPlacement.ABOVE_LINE ? 0 : 1;
  }

  public static ECommentPlacement valueOf(final int ordinal) {
    if (ordinal == 0) {
      return ECommentPlacement.ABOVE_LINE;
    } else if (ordinal == 1) {
      return ECommentPlacement.BEHIND_LINE;
    }

    throw new IllegalStateException("Error: Unknown ordinal value.");
  }
}
