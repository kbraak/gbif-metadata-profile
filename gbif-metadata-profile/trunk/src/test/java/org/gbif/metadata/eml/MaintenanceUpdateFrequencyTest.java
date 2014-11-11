package org.gbif.metadata.eml;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaintenanceUpdateFrequencyTest {

  /**
   * Test ensures lookup works regardless of whether string is in lowerCamelCase or lowercase.
   */
  @Test
  public void testFindByIdentifier() {
    String id = "asneeded";
    assertEquals(MaintenanceUpdateFrequency.AS_NEEDED, MaintenanceUpdateFrequency.findByIdentifier(id));

    id = "asNeeded";
    assertEquals(MaintenanceUpdateFrequency.AS_NEEDED, MaintenanceUpdateFrequency.findByIdentifier(id));
  }
}
