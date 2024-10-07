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

import org.gbif.metadata.eml.ipt.util.DateUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * This class can be used to encapsulate temporal coverage information.
 */
public class TemporalCoverage implements Serializable {

  private static final long serialVersionUID = 898101764914677290L;

  /**
   * A single time stamp signifying the beginning of some time period.
   *
   * @see <a href="https://eml.ecoinformatics.org/schema/eml-coverage_xsd.html#TemporalCoverage_TemporalCoverage_rangeOfDates_beginDate">EML Coverage
   *      beginDate keyword</a>
   */
  private Date startDate;

  /**
   * A single time stamp signifying the end of some time period.
   *
   * @see <a href="https://eml.ecoinformatics.org/schema/eml-coverage_xsd.html#TemporalCoverage_TemporalCoverage_rangeOfDates_endDate">EML Coverage endDate
   *      keyword</a>
   */
  private Date endDate;

  /**
   * Text description of the time period during which the collection was assembled e.g. "Victorian", or "1922 - 1932",
   * or "c. 1750".
   *
   * @see <a href="http://rs.tdwg.org/ontology/voc/Collection#formationPeriod">TDWG Natural Collection Description</a>
   */
  private String formationPeriod;

  /**
   * Time period during which biological material was alive. (for palaeontological collections).
   *
   * @see <a href="http://rs.tdwg.org/ontology/voc/Collection#livingTimePeriodCoverage">TDWG Natural Collection
   *      Description</a>
   */
  private String livingTimePeriod;

  public TemporalCoverage() {}

  public String getFormationPeriod() {
    if (formationPeriod == null || formationPeriod.isEmpty()) {
      return null;
    }
    return formationPeriod;
  }

  public void setFormationPeriod(String formationPeriod) {
    this.formationPeriod =
        formationPeriod != null && formationPeriod.isEmpty() ? null : formationPeriod;
  }

  public String getLivingTimePeriod() {
    if (livingTimePeriod == null || livingTimePeriod.isEmpty()) {
      return null;
    }
    return livingTimePeriod;
  }

  public void setLivingTimePeriod(String livingTimePeriod) {
    this.livingTimePeriod =
        livingTimePeriod != null && livingTimePeriod.isEmpty() ? null : livingTimePeriod;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void correctDateOrder() {
    if (startDate == null && endDate != null) {
      startDate = endDate;
      endDate = null;
    }
    if (startDate != null && endDate != null && startDate.compareTo(endDate) > 0) {
      Date d = startDate;
      startDate = endDate;
      endDate = d;
    }
  }

  public Date getEndDate() {
    if (endDate == null) {
      return endDate;
    }
    return new Date(endDate.getTime());
  }

  public Date getStartDate() {
    if (startDate == null) {
      return startDate;
    }
    return new Date(startDate.getTime());
  }

  public TemporalCoverageType getType() {
    if (formationPeriod != null && !formationPeriod.isEmpty()) {
      return TemporalCoverageType.FORMATION_PERIOD;
    }
    if (livingTimePeriod != null && !livingTimePeriod.isEmpty()) {
      return TemporalCoverageType.LIVING_TIME_PERIOD;
    }
    if (startDate != null && endDate != null && startDate.compareTo(endDate) != 0) {
      return TemporalCoverageType.DATE_RANGE;
    }
    return TemporalCoverageType.SINGLE_DATE;
  }

  /**
   * Utility to set the date with a textual format
   *
   * @param start to set
   *
   * @throws ParseException Should it be an erroneous format
   */
  public void setEnd(String start) throws ParseException {
    endDate = DateUtils.calendarDate(start);
  }

  /**
   * Utility to set the date with a textual format
   *
   * @param start To set
   *
   * @throws ParseException Should it be an erroneous format
   */
  public void setStart(String start) throws ParseException {
    startDate = DateUtils.calendarDate(start);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TemporalCoverage that = (TemporalCoverage) o;
    return Objects.equals(startDate, that.startDate)
        && Objects.equals(endDate, that.endDate)
        && Objects.equals(formationPeriod, that.formationPeriod)
        && Objects.equals(livingTimePeriod, that.livingTimePeriod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startDate, endDate, formationPeriod, livingTimePeriod);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", TemporalCoverage.class.getSimpleName() + "[", "]")
        .add("startDate=" + startDate)
        .add("endDate=" + endDate)
        .add("formationPeriod='" + formationPeriod + "'")
        .add("livingTimePeriod='" + livingTimePeriod + "'")
        .toString();
  }
}
