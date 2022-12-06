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
 * This class can be used to encapsulate information about a citation having an identifier (e.g. DOI) plus
 * citation string.
 */
public class Citation implements Serializable {

  /**
   * Generated
   */
  private static final long serialVersionUID = 8611377167438888243L;

  private String identifier;
  private String citation;

  /**
   * Default constructor required by Struts2
   */
  public Citation() {}

  public Citation(String citation, String identifier) {
    this.citation = citation;
    this.identifier = identifier;
  }

  /**
   * @return the name
   */
  public String getCitation() {
    return citation;
  }

  public void setCitation(String citation) {
    this.citation = citation;
  }

  /**
   * @return the identifier
   */
  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Citation citation1 = (Citation) o;
    return Objects.equals(identifier, citation1.identifier)
        && Objects.equals(citation, citation1.citation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identifier, citation);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Citation.class.getSimpleName() + "[", "]")
        .add("identifier='" + identifier + "'")
        .add("citation='" + citation + "'")
        .toString();
  }
}
