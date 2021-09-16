/*
 * Copyright 2021 Global Biodiversity Information Facility (GBIF)
 *
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
package org.gbif.metadata;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {

  public static final ThreadSafeSimpleDateFormat ISO_DATE_FORMAT = new ThreadSafeSimpleDateFormat("yyyy-MM-dd");
  //2010-02-22

  public static final List<ThreadSafeSimpleDateFormat> ALL_DATE_FORMATS = new ArrayList<ThreadSafeSimpleDateFormat>(10);

  static {
    ALL_DATE_FORMATS.add(new ThreadSafeSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));  // 2001-07-04T12:08:56.235-0700
    ALL_DATE_FORMATS.add(new ThreadSafeSimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ"));  // 2001-07-04 12:08:56.235-0700
    ALL_DATE_FORMATS.add(new ThreadSafeSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"));  // 2001-07-04T12:08:56.235
    ALL_DATE_FORMATS.add(new ThreadSafeSimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));  // 2001-07-04 12:08:56.235
    ALL_DATE_FORMATS.add(new ThreadSafeSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));  // 2001-07-04T12:08:56
    ALL_DATE_FORMATS.add(new ThreadSafeSimpleDateFormat("yyyy-MM-dd HH:mm:ss"));  // 2001-07-04 12:08:56
    ALL_DATE_FORMATS
      .add(new ThreadSafeSimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z"));  // Wed, 4 Jul 2001 12:08:56 -0700
    ALL_DATE_FORMATS.add(new ThreadSafeSimpleDateFormat("EEE, d MMM yyyy HH:mm:ss"));  // Wed, 4 Jul 2001 12:08:56
    ALL_DATE_FORMATS.add(ISO_DATE_FORMAT);
    ALL_DATE_FORMATS.add(new ThreadSafeSimpleDateFormat("yyyy.MM.dd"));  // 2010.03.22
    ALL_DATE_FORMATS.add(new ThreadSafeSimpleDateFormat("dd.MM.yyyy"));  // 22.03.2010
    ALL_DATE_FORMATS.add(new ThreadSafeSimpleDateFormat("yyyy/MM/dd"));  // 2010/02/24
    //ALL_DATE_FORMATS.add(new ThreadSafeSimpleDateFormat("MM/dd/yyyy"));  // 02/24/2010
  }

  /**
   * Utility to parse an EML calendarDate in a textual format. Can be ISO date or just the year, ignoring whitespace
   *
   * @param dateString To set
   *
   * @return the parsed date
   *
   * @throws ParseException Should it be an erroneous format
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-coverage.html#calendarDate">EML Coverage
   *      calendarDate keyword</a>
   */
  public static Date calendarDate(String dateString) throws ParseException {
    if (dateString == null || StringUtils.isEmpty(dateString.trim())) {
      return null;
    }
    // kill whitespace
    dateString = dateString.replaceAll("\\s", "");
    dateString = dateString.replaceAll("[\\,._#//]", "-");
    Date date;
    try {
      ThreadSafeSimpleDateFormat sdf = new ThreadSafeSimpleDateFormat("yyyy-MM-dd");
      date = sdf.parse(dateString);
    } catch (ParseException e) {
      if (dateString.length() == 4) {
        ThreadSafeSimpleDateFormat sdf = new ThreadSafeSimpleDateFormat("yyyy");
        date = sdf.parse(dateString);
        date = new Date(date.getTime() + 1);
      } else {
        throw e;
      }
    }
    return date;
  }

  /**
   * Parses a string date trying various common formats, starting with the most complex ones
   *
   * @param x the date as a string or null if not parsable
   */
  public static Date parse(String x) {
    if (x == null || StringUtils.isEmpty(x.trim())) {
      return null;
    }
    Date date = null;
    for (ThreadSafeSimpleDateFormat df : ALL_DATE_FORMATS) {
      try {
        // alternatively try others
        date = df.parse(x);
        break;
      } catch (ParseException ignored) {
      }
    }
    // if date is still null, try schema date
    if (date == null) {
      try {
        date = schemaDateTime(x);
      } catch (ParseException ignored) {
      }
    }
    return date;
  }

  /**
   * Parses a string date trying the given preferred format first
   *
   * @param x date as a string
   */
  public static Date parse(String x, ThreadSafeSimpleDateFormat preferredFormat) {
    Date date = null;
    try {
      // first try with preferred format
      date = preferredFormat.parse(x);
    } catch (ParseException ignored) {
      date = parse(x);
    }
    return date;
  }

  /**
   * Parses a string date trying the ISO format first
   *
   * @param x the date as a string
   */
  public static Date parseIso(String x) {
    return parse(x, ISO_DATE_FORMAT);
  }

  /**
   * Utility to parse an XML schema datetime in a textual format
   *
   * @param dateString To set
   *
   * @throws ParseException Should it be an erroneous format
   */
  public static Date schemaDateTime(String dateString) throws ParseException {
    dateString = StringUtils.trimToEmpty(dateString);
    Date date;
    try {
      Pattern timezone = Pattern.compile("([+-]\\d\\d:\\d\\d)$");
      dateString = timezone.matcher(dateString).replaceAll("GMT$1");
      ThreadSafeSimpleDateFormat sdf = new ThreadSafeSimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
      date = sdf.parse(dateString);
    } catch (ParseException ignored) {
      try {
        ThreadSafeSimpleDateFormat sdf = new ThreadSafeSimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        date = sdf.parse(dateString);
      } catch (ParseException e1) {
        date = calendarDate(dateString);
      }
    }
    return date;
  }

  private DateUtils() {
    throw new UnsupportedOperationException("Can't initialize class");
  }
}
