package org.gbif.metadata.eml;

import java.math.BigDecimal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmlTest {

  @Test
  public void testEmlVersionsStartAtZero() {
    Eml eml = new Eml();

    assertEquals("0.0", eml.getEmlVersion().toPlainString());
    assertEquals("0.0", eml.getPreviousEmlVersion().toPlainString());

    BigDecimal nextMajorVersion = eml.getNextEmlVersionAfterMajorVersionChange();
    eml.setEmlVersion(nextMajorVersion);
    assertEquals("1.0", eml.getEmlVersion().toPlainString());
    assertEquals("0.0", eml.getPreviousEmlVersion().toPlainString());

    BigDecimal nextMinorVersion = eml.getNextEmlVersionAfterMinorVersionChange();
    eml.setEmlVersion(nextMinorVersion);
    assertEquals("1.1", eml.getEmlVersion().toPlainString());
    assertEquals("1.0", eml.getPreviousEmlVersion().toPlainString());

    nextMinorVersion = eml.getNextEmlVersionAfterMinorVersionChange();
    eml.setEmlVersion(nextMinorVersion);
    assertEquals("1.2", eml.getEmlVersion().toPlainString());
    assertEquals("1.1", eml.getPreviousEmlVersion().toPlainString());

    nextMajorVersion = eml.getNextEmlVersionAfterMajorVersionChange();
    eml.setEmlVersion(nextMajorVersion);
    assertEquals("2.0", eml.getEmlVersion().toPlainString());
    assertEquals("1.2", eml.getPreviousEmlVersion().toPlainString());
  }

  @Test
  public void testEmlVersionsStartNotAtZero() {
    Eml eml = new Eml();
    eml.setPackageId("619a4b95-1a82-4006-be6a-7dbe3c9b33c5/v2.2");

    BigDecimal nextMajorVersion = eml.getNextEmlVersionAfterMajorVersionChange();
    eml.setEmlVersion(nextMajorVersion);
    assertEquals("3.0", eml.getEmlVersion().toPlainString());
    assertEquals("2.2", eml.getPreviousEmlVersion().toPlainString());
  }

  @Test
  public void testEmlVersionsStartNotWithDecimal() {
    Eml eml = new Eml();
    eml.setPackageId("619a4b95-1a82-4006-be6a-7dbe3c9b33c5/v2");
    assertEquals("0.0", eml.getEmlVersion().toPlainString());

    BigDecimal nextMajorVersion = eml.getNextEmlVersionAfterMajorVersionChange();
    eml.setEmlVersion(nextMajorVersion);
    assertEquals("1.0", eml.getEmlVersion().toPlainString());
    assertEquals("0.0", eml.getPreviousEmlVersion().toPlainString());
  }

  @Test
  public void testGetNextEmlVersionAfterMajorVersionChange() {
    Eml eml = new Eml();
    eml.setEmlVersion(new BigDecimal("5.4"));
    assertEquals("6.0", eml.getNextEmlVersionAfterMajorVersionChange().toPlainString());
  }

  @Test
  public void testGetNextEmlVersionAfterMinorVersionChange() {
    Eml eml = new Eml();
    eml.setEmlVersion(new BigDecimal("5.4"));
    assertEquals("5.5", eml.getNextEmlVersionAfterMinorVersionChange().toPlainString());
  }
}
