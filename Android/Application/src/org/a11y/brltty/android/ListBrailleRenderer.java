/*
 * BRLTTY - A background process providing access to the console screen (when in
 *          text mode) for a blind person using a refreshable braille display.
 *
 * Copyright (C) 1995-2013 by The BRLTTY Developers.
 *
 * BRLTTY comes with ABSOLUTELY NO WARRANTY.
 *
 * This is free software, placed under the terms of the
 * GNU General Public License, as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any
 * later version. Please see the file LICENSE-GPL for details.
 *
 * Web Page: http://mielke.cc/brltty/
 *
 * This software is maintained by Dave Mielke <dave@mielke.cc>.
 */

package org.a11y.brltty.android;

import android.graphics.Rect;

public class ListBrailleRenderer extends BrailleRenderer {
  @Override
  protected final void setBrailleLocations (ScreenElementList elements) {
    elements.sortByVisualLocation();
    elements.groupByContainer();
    addVirtualElements(elements);

    int left = 0;
    int top = 0;
    int right = -1;
    int bottom = -1;
    boolean wasVirtual = false;

    for (ScreenElement element : elements) {
      String[] text = element.getBrailleText();

      boolean isVirtual = element.getVisualLocation() == null;
      boolean append = wasVirtual && isVirtual;
      wasVirtual = isVirtual;

      if (text != null) {
        int width = getTextWidth(text);

        if (append) {
          left = right + ApplicationParameters.COLUMN_SPACING + 1;
        } else {
          left = 0;
          top = bottom + 1;
        }

        right = left + width - 1;
        bottom = top + text.length - 1;
        element.setBrailleLocation(new Rect(left, top, right, bottom));
      }
    }
  }

  public ListBrailleRenderer () {
    super();
  }
}