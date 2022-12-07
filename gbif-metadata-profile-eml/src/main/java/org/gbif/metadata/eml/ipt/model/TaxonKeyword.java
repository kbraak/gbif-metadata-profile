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
package org.gbif.metadata.eml.ipt.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * This class can be used to encapsulate taxonomic keyword information.
 */
public class TaxonKeyword implements Serializable {

  /**
   * Generated
   */
  private static final long serialVersionUID = -7870655444855755937L;

  /**
   * The name representing the taxonomic rank of the taxon being described , e.g., Orca
   *
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-coverage.html#taxonRankValue">EML Coverage
   *      taxonRankValue keyword</a>
   */
  private String scientificName;

  /**
   * the name of the taxonomic rank for which the Taxon rank value is provided, e.g., Genus
   *
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-coverage.html#taxonRankName">EML Coverage
   *      taxonRankName keyword</a>
   */
  private String rank;

  /**
   * The common/vernacular name(s) for the organisms in the dataset/collection @ http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-coverage.html#
   * commonName
   */
  private String commonName;

  /**
   * Required by Struts2
   */
  public TaxonKeyword() {}

  public TaxonKeyword(String scientificName, String rank, String commonName) {
    this.scientificName = scientificName;
    this.rank = rank;
    this.commonName = commonName;
  }

  /**
   * @return the commonName
   */
  public String getCommonName() {
    return commonName;
  }

  /**
   * @param commonName the commonName to set
   */
  public void setCommonName(String commonName) {
    this.commonName = commonName;
  }

  /**
   * @return the rank
   */
  public String getRank() {
    return rank;
  }

  /**
   * @param rank the rank to set
   */
  public void setRank(String rank) {
    this.rank = rank;
  }

  /**
   * @return the scientificName
   */
  public String getScientificName() {
    return scientificName;
  }

  /**
   * @param scientificName the scientificName to set
   */
  public void setScientificName(String scientificName) {
    this.scientificName = scientificName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TaxonKeyword that = (TaxonKeyword) o;
    return Objects.equals(scientificName, that.scientificName)
        && Objects.equals(rank, that.rank)
        && Objects.equals(commonName, that.commonName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scientificName, rank, commonName);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", TaxonKeyword.class.getSimpleName() + "[", "]")
        .add("scientificName='" + scientificName + "'")
        .add("rank='" + rank + "'")
        .add("commonName='" + commonName + "'")
        .toString();
  }
}
