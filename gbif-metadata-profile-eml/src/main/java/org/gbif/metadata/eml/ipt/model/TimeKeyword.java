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

public class TimeKeyword implements Serializable {

  private Date start;
  private Date end;

  public Date getEnd() {
    return end;
  }

  public Date getStart() {
    return start;
  }

  public void setEnd(Date end) {
    this.end = end;
  }

  /**
   * Utility to set the date with a textual format
   *
   * @param end To set
   *
   * @throws ParseException Should it be an erroneous format
   */
  public void setEnd(String end) throws ParseException {
    this.end = DateUtils.calendarDate(end);
  }

  public void setStart(Date start) {
    this.start = start;
  }

  /**
   * Utility to set the date with a textual format
   *
   * @param start To set
   *
   * @throws ParseException Should it be an erroneous format
   */
  public void setStart(String start) throws ParseException {
    this.start = DateUtils.calendarDate(start);
  }
}
