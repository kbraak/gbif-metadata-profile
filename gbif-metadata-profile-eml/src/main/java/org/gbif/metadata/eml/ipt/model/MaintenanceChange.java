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

import org.gbif.api.vocabulary.MaintenanceUpdateFrequency;

import java.io.Serializable;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A description of change made to the data.
 */
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class MaintenanceChange implements Serializable {

  /**
   * The expression should unambiguously identify the entity(s) and attribute(s) that were changed.
   */
  private String changeScope;

  /**
   * The previous value or an expression that describes the previous value of the data.
   */
  private MaintenanceUpdateFrequency oldValue;

  /**
   * The date the changes were applied.
   */
  private Date changeDate;

  /**
   * Explanation or justification for the change made to the data.
   */
  private String comment;
}
