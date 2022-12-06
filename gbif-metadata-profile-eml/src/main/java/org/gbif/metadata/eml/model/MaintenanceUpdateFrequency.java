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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

/**
 * This enumeration mirrors the MaintUpFreqType enumeration coming from EML. The maintenance update frequency is
 * the frequency with which changes and additions are made to the dataset after the initial dataset is completed.
 *
 * @see <a href="https://knb.ecoinformatics.org/#external//emlparser/docs/eml-2.1.1/./eml-dataset.html#MaintUpFreqType">MaintUpFreqType
 * EML ENUM</a>
 */
public enum MaintenanceUpdateFrequency {
  /**
   * Updated 1 time each year.
   */
  ANNUALLY("annually", 365),
  /**
   * Updated as needed. Not specific enough to use for auto-publishing.
   */
  AS_NEEDED("asNeeded", 0),
  /**
   * Updated 2 times each year.
   */
  BIANNUALLY("biannually", 182),
  /**
   * Updated continuously. Not specific enough to use for auto-publishing.
   */
  CONTINUALLY("continually", 0),
  /**
   * Updated 1 time each day.
   */
  DAILY("daily", 1),
  /**
   * Updated at irregular intervals. Not specific enough to use for auto-publishing.
   */
  IRREGULAR("irregular", 0),
  /**
   * Updated 1 time each month.
   */
  MONTHLY("monthly", 30),
  /**
   * Further updates are not planned.
   */
  NOT_PLANNED("notPlanned", 0),
  /**
   * Updated 1 time each week.
   */
  WEEKLY("weekly", 7),
  /**
   * Further updates may still happen, but it is not known for sure. Not specific enough to use for auto-publishing.
   * Ignore the typo in "unknown", this is exactly how this term is spelt in the EML 2.1.1 specification.
   */
  UNKOWN("unkown", 0),
  /**
   * Updated according to some other interval. Not specific enough to use for auto-publishing.
   */
  OTHER_MAINTENANCE_PERIOD("otherMaintenancePeriod", 0);

  // EML requires the ENUM value be in lowerCamelCase
  private final String identifier;
  private final int periodInDays;

  /**
   * List of frequencies that have update periods of one or more days.
   */
  public static final List<String> NON_ZERO_DAYS_UPDATE_PERIODS;

  static {
    // populate list
    List<String> ls = new ArrayList<>();
    ls.add(MaintenanceUpdateFrequency.ANNUALLY.getIdentifier());
    ls.add(MaintenanceUpdateFrequency.BIANNUALLY.getIdentifier());
    ls.add(MaintenanceUpdateFrequency.MONTHLY.getIdentifier());
    ls.add(MaintenanceUpdateFrequency.WEEKLY.getIdentifier());
    ls.add(MaintenanceUpdateFrequency.DAILY.getIdentifier());
    NON_ZERO_DAYS_UPDATE_PERIODS = Collections.unmodifiableList(ls);
  }

  /**
   * Constructor.
   *
   * @param identifier   identifier
   * @param periodInDays update frequency period in days
   */
  MaintenanceUpdateFrequency(String identifier, int periodInDays) {
    this.identifier = identifier;
    this.periodInDays = periodInDays;
  }

  /**
   * Return the Enumeration's identifier string.
   *
   * @return the Enumeration's identifier string
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Iterate through the enumerations, and try to find a match to the incoming string by comparing it against
   * the identifier of each enumeration.
   *
   * @param id incoming maintenanceUpdateFrequency identifier
   *
   * @return enumeration or null if no match was made
   */
  public static MaintenanceUpdateFrequency findByIdentifier(@Nullable String id) {
    if (id != null) {
      for (MaintenanceUpdateFrequency entry : MaintenanceUpdateFrequency.values()) {
        if (entry.getIdentifier().equalsIgnoreCase(id.trim())) {
          return entry;
        }
      }
    }
    return null;
  }

  /**
   * Return the Enumeration's frequency update period in days. This is set to 0 if an update period is not applicable
   * to the Enumeration.
   *
   * @return frequency update period in days
   */
  public int getPeriodInDays() {
    return periodInDays;
  }
}
