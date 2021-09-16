package org.gbif.metadata.eml;

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
