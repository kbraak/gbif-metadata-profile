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
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * Encapsulates the descriptive elements of the geospatial coverage
 */
public class GeospatialCoverage implements Serializable {

  /**
   * Generated
   */
  private static final long serialVersionUID = -7639582552916192696L;

  /**
   * a short text description of a dataset's geographic areal domain.
   */
  private String description;

  /**
   * These are derived from associatedMetadata and represent codes referencing a thesaurus (e.g. DE,DK from the 3166
   * country codes)
   */
  private List<String> keywords = Lists.newArrayList();

  /**
   * Define the coordinates
   */
  private BBox boundingCoordinates = BBox.newWorldInstance();

  /**
   * Required by Struts2
   */
  public GeospatialCoverage() {
  }

  /**
   * @return the boundingCoordinates
   */
  public BBox getBoundingCoordinates() {
    return boundingCoordinates;
  }

  /**
   * @param boundingCoordinates the boundingCoordinates to set
   */
  public void setBoundingCoordinates(BBox boundingCoordinates) {
    this.boundingCoordinates = boundingCoordinates;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the keywords
   */
  public List<String> getKeywords() {
    return keywords;
  }

  /**
   * @param keywords the keywords to set
   */
  public void setKeywords(List<String> keywords) {
    this.keywords = keywords;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final GeospatialCoverage other = (GeospatialCoverage) obj;
    return Objects.equal(this.description, other.description) && Objects.equal(this.keywords, other.keywords) && Objects
      .equal(this.boundingCoordinates, other.boundingCoordinates);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(description, keywords, boundingCoordinates);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).
      add("description", description).
      add("keywords", keywords).
      add("boundingCoordinates", boundingCoordinates).
      toString();
  }

}
