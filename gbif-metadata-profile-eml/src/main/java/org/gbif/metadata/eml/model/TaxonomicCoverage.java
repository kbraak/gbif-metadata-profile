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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

/**
 * The description of the Taxonomic scope that the resource covers.
 */
public class TaxonomicCoverage implements Serializable {

  /**
   * Generated
   */
  private static final long serialVersionUID = -1550877218411220807L;

  /**
   * A description of the range of taxa addressed in the data set or collection
   *
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-coverage
   *      .html#generalTaxonomicCoverage">EML
   *      Coverage generalTaxonomicCoverage keyword</a>
   */
  private String description;

  /**
   * Structured keywords listing taxa names
   */
  private List<TaxonKeyword> taxonKeywords = new ArrayList<TaxonKeyword>();

  /**
   * Required for struts2 params-interceptor, Digester and deserializing from XML
   */
  public TaxonomicCoverage() {}

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
  public List<TaxonKeyword> getTaxonKeywords() {
    return taxonKeywords;
  }

  /**
   * @param keywords the keywords to set
   */
  public void setTaxonKeywords(List<TaxonKeyword> keywords) {
    taxonKeywords = keywords;
  }

  public void addTaxonKeyword(TaxonKeyword keyword) {
    taxonKeywords.add(keyword);
  }

  /**
   * Adds new taxon keywords each having only a scientific name.
   *
   * @param scientificNames concatenated list of scientific names using a semicolon ; pipe | or new line \n delimiter
   *
   * @return the number of newly added taxon keywords
   */
  public int addTaxonKeywords(String scientificNames) {
    String delimiter = ";";
    if (scientificNames.contains("\n")) {
      delimiter = "\n";
    } else if (scientificNames.contains("|")) {
      delimiter = "|";
    }
    int count = 0;
    for (String sciname : StringUtils.split(scientificNames, delimiter)) {
      sciname = StringUtils.trimToNull(sciname);
      if (sciname != null) {
        taxonKeywords.add(new TaxonKeyword(sciname, null, null));
        count++;
      }
    }
    return count;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TaxonomicCoverage that = (TaxonomicCoverage) o;
    return Objects.equals(description, that.description)
        && Objects.equals(taxonKeywords, that.taxonKeywords);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, taxonKeywords);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", TaxonomicCoverage.class.getSimpleName() + "[", "]")
        .add("description='" + description + "'")
        .add("taxonKeywords=" + taxonKeywords)
        .toString();
  }
}
