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
package org.gbif.metadata.dc.parse;

import org.gbif.api.model.registry.Contact;
import org.gbif.api.model.registry.Dataset;
import org.gbif.api.model.registry.Identifier;
import org.gbif.api.model.registry.eml.KeywordCollection;
import org.gbif.api.vocabulary.ContactType;
import org.gbif.api.vocabulary.IdentifierType;
import org.gbif.api.vocabulary.License;
import org.gbif.utils.file.FileUtils;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class DatasetDcParserTest {

  private Contact contactByType(Dataset d, ContactType type) {
    for (Contact c : d.getContacts()) {
      if (type == c.getType()) {
        return c;
      }
    }
    return null;
  }

  private void assertIdentifierExists(Dataset d, String id, IdentifierType type) {
    for (Identifier i : d.getIdentifiers()) {
      if (i.getType() == type && id.equals(i.getIdentifier())) {
        return;
      }
    }
    fail("Identifier " + id + " of type " + type + " missing");
  }

  private void assertKeywordExists(Dataset d, String tag) {
    for (KeywordCollection kc : d.getKeywordCollections()) {
      for (String k : kc.getKeywords()) {
        if (k.equals(tag)) {
          return;
        }
      }
    }
    fail("Keyword" + tag + " missing");
  }

  @Test
  public void testDcParsing() throws Exception {
    Dataset dataset =
        DatasetDcParser.parse(FileUtils.classpathStream("dc/worms_dc.xml"));

    Calendar cal = Calendar.getInstance();
    cal.clear();

    assertNotNull(dataset);

    assertEquals("World Register of Marine Species", dataset.getTitle());
    assertTrue(
        dataset
            .getDescription()
            .startsWith(
                "The aim of a World Register of Marine Species (WoRMS) is to provide an authoritative and comprehensive list of names of marine organisms, including information on synonymy. While highest priority goes to valid names, other names in use are included so that this register can serve as a guide to interpret taxonomic literature."));
    assertEquals("http://www.marinespecies.org/", dataset.getHomepage().toString());
    assertEquals("Ward Appeltans", contactByType(dataset, ContactType.ORIGINATOR).getLastName());
    assertEquals("World Register of Marine Species", dataset.getTitle());
    assertEquals("World Register of Marine Species", dataset.getTitle());
    assertEquals("World Register of Marine Species", dataset.getTitle());

    assertIdentifierExists(dataset, "1234", IdentifierType.UNKNOWN);
    assertIdentifierExists(dataset, "doi:10.1093/ageing/29.1.57", IdentifierType.UNKNOWN);
    assertIdentifierExists(
        dataset, "http://ageing.oxfordjournals.org/content/29/1/57", IdentifierType.UNKNOWN);

    assertKeywordExists(dataset, "Specimens");
    assertKeywordExists(dataset, "Authoritative");
    assertKeywordExists(dataset, "Species Checklist");
    assertKeywordExists(dataset, "Taxonomy");
    assertKeywordExists(dataset, "Marine");

    // License parsed from license element populated with GBIF supported license URL
    assertEquals(License.CC0_1_0, dataset.getLicense());
    assertEquals(License.CC0_1_0.getLicenseTitle(), dataset.getLicense().getLicenseTitle());
  }

  /**
   * Test License parsed from rights element populated with GBIF supported license acronym, and when
   * license element has not populated.
   */
  @Test
  public void testDcParsingLicenseFromRights() throws Exception {
    Dataset dataset =
        DatasetDcParser.parse(FileUtils.classpathStream("dc/worms_dc2.xml"));
    assertEquals(License.CC_BY_NC_4_0, dataset.getLicense());
    assertEquals(License.CC_BY_NC_4_0.getLicenseTitle(), dataset.getLicense().getLicenseTitle());
  }
}
