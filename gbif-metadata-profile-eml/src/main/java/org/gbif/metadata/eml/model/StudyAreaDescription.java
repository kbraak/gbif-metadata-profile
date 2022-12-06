/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gbif.metadata.eml.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StudyAreaDescription that = (StudyAreaDescription) o;
    return name == that.name
        && Objects.equals(citableClassificationSystem, that.citableClassificationSystem)
        && Objects.equals(descriptorValue, that.descriptorValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, citableClassificationSystem, descriptorValue);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", StudyAreaDescription.class.getSimpleName() + "[", "]")
        .add("name=" + name)
        .add("citableClassificationSystem='" + citableClassificationSystem + "'")
        .add("descriptorValue='" + descriptorValue + "'")
        .toString();
  }
}
