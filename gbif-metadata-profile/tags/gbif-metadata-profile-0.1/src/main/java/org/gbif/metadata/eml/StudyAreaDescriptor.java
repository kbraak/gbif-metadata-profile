/*
 * Copyright 2009 GBIF.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gbif.metadata.eml;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import com.google.common.collect.Maps;

public enum StudyAreaDescriptor implements Serializable {
  THEMATIC("thematic"),
  GEOGRAPHIC("geographic"),
  GENERIC("generic");

  public static final Map<String, String> HTML_SELECT_MAP;
  private final String name;

  static {
    Map<String, String> map = Maps.newHashMap();
    for (StudyAreaDescriptor sad : StudyAreaDescriptor.values()) {
      map.put(sad.name(), "studyAreaDescriptorType." + sad.name());
    }
    HTML_SELECT_MAP = Collections.unmodifiableMap(map);
  }

  /**
   * Returns a StudyAreaDescriptor created from a string description of the StudyAreaDescriptor. If the description is
   * null or if it's not a valid value, null is returned.
   *
   * @param studyAreaDescriptor the description
   *
   * @return StudyAreaDescriptor
   */
  public static StudyAreaDescriptor fromString(String studyAreaDescriptor) {
    if (studyAreaDescriptor == null) {
      return null;
    }
    studyAreaDescriptor = studyAreaDescriptor.trim();
    for (StudyAreaDescriptor s : StudyAreaDescriptor.values()) {
      if (s.name.equalsIgnoreCase(studyAreaDescriptor)) {
        return s;
      }
    }
    return null;
  }

  StudyAreaDescriptor(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
