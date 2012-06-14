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

import com.google.common.base.Objects;

/**
 * Encapsulates all the information for a StudyAreaDescription.
 */
public class StudyAreaDescription implements Serializable {

  /**
   * Generated
   */
  private static final long serialVersionUID = -625087801176596735L;
  private StudyAreaDescriptor name = StudyAreaDescriptor.GENERIC;
  private String citableClassificationSystem = "false";
  private String descriptorValue;

  public String getCitableClassificationSystem() {
    return citableClassificationSystem;
  }

  public void setCitableClassificationSystem(String citableClassificationSystem) {
    this.citableClassificationSystem = citableClassificationSystem;
  }

  public String getDescriptorValue() {
    return descriptorValue;
  }

  public void setDescriptorValue(String descriptorValue) {
    this.descriptorValue = descriptorValue;
  }

  public StudyAreaDescriptor getName() {
    if (name == null) return StudyAreaDescriptor.GENERIC;
    return name;
  }

  public void setName(StudyAreaDescriptor name) {
    this.name = name;
  }

  public void setName(String nameStr) {
    name = StudyAreaDescriptor.fromString(nameStr);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final StudyAreaDescription other = (StudyAreaDescription) obj;
    return Objects.equal(this.name, other.name) && Objects
      .equal(this.citableClassificationSystem, other.citableClassificationSystem) && Objects
      .equal(this.descriptorValue, other.descriptorValue);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name, citableClassificationSystem, descriptorValue);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).
      add("name", name).
      add("citableClassificationSystem", citableClassificationSystem).
      add("descriptorValue", descriptorValue).
      toString();
  }

}
