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
package org.gbif.metadata.eml;

import org.gbif.metadata.eml.model.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTest {
  private static final String ADDRESS = "63 Strandvejen";
  private static final String CITY = "Aarhus";
  private static final String COUNTRY = "Denmark";
  private static final String PROVINCE = "Alberta";
  private static final String POSTAL_CODE = "2100";

  @Test
  public void testToFormattedString() {
    Address address1 = new Address();
    address1.setAddress(ADDRESS);
    address1.setCity(CITY);
    address1.setCountry(COUNTRY);
    address1.setPostalCode(POSTAL_CODE);
    address1.setProvince(PROVINCE);
    assertEquals("63 Strandvejen, Aarhus, Alberta Denmark, 2100", address1.toFormattedString());

    Address address2 = new Address();
    address2.setCity(CITY);
    address2.setCountry(COUNTRY);
    address2.setPostalCode(POSTAL_CODE);
    address2.setProvince(PROVINCE);
    assertEquals("Aarhus, Alberta Denmark, 2100", address2.toFormattedString());

    Address address3 = new Address();
    address3.setCity(CITY);
    address3.setCountry(COUNTRY);
    address3.setPostalCode(POSTAL_CODE);
    assertEquals("Aarhus Denmark, 2100", address3.toFormattedString());

    Address address4 = new Address();
    address4.setCountry(COUNTRY);
    address4.setPostalCode(POSTAL_CODE);
    assertEquals("Denmark, 2100", address4.toFormattedString());

    Address address5 = new Address();
    address5.setPostalCode(POSTAL_CODE);
    assertEquals("2100", address5.toFormattedString());

    Address address6 = new Address();
    address6.setAddress(ADDRESS);
    address6.setCity(CITY);
    assertEquals("63 Strandvejen, Aarhus", address6.toFormattedString());
  }
}
