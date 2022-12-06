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
 * This class can be used to encapsulate information about collection data.
 */
public class Collection implements Serializable {

  /**
   * Generated
   */
  private static final long serialVersionUID = 7028536657811651816L;

  /**
   * Official name of the Collection in the local language.
   * Note: this could potentially be sourced from the resource title, but this is declared explicitly in the GBIF IPT
   * metadata profile, so must assume that this is required for a title in a different language, presumably to aid free
   * text discovery in original language
   *
   * @see <a href="http://purl.org/dc/elements/1.1/title">DublinCore</a>
   */
  private String collectionName;

  /**
   * The URI (LSID or URL) of the collection. In RDF, used as URI of the collection resource.
   *
   * @see <a href="http://rs.tdwg.org/ontology/voc/Collection#collectionId">TDWG Natural Collection Description</a>
   */
  private String collectionId;

  /**
   * Identifier for the parent collection for this sub-collection. Enables a hierarchy of collections and sub
   * collections to be built.
   *
   * @see <a href="http://rs.tdwg.org/ontology/voc/Collection#isPartOfCollection">TDWG Natural Collection
   * Description</a>
   */
  private String parentCollectionId;

  /**
   * Required by Struts2
   */
  public Collection() {}

  public String getCollectionName() {
    if (collectionName == null || collectionName.isEmpty()) {
      return null;
    }
    return collectionName;
  }

  public void setCollectionName(String collectionName) {
    this.collectionName = collectionName;
  }

  public String getCollectionId() {
    if (collectionId == null || collectionId.isEmpty()) {
      return null;
    }
    return collectionId;
  }

  public void setCollectionId(String collectionId) {
    this.collectionId = collectionId;
  }

  public String getParentCollectionId() {
    if (parentCollectionId == null || parentCollectionId.isEmpty()) {
      return null;
    }
    return parentCollectionId;
  }

  public void setParentCollectionId(String parentCollectionId) {
    this.parentCollectionId = parentCollectionId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Collection that = (Collection) o;
    return Objects.equals(collectionName, that.collectionName)
        && Objects.equals(collectionId, that.collectionId)
        && Objects.equals(parentCollectionId, that.parentCollectionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(collectionName, collectionId, parentCollectionId);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Collection.class.getSimpleName() + "[", "]")
        .add("collectionName='" + collectionName + "'")
        .add("collectionId='" + collectionId + "'")
        .add("parentCollectionId='" + parentCollectionId + "'")
        .toString();
  }
}
