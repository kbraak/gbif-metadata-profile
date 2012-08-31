/***************************************************************************
 * Copyright 2010 Global Biodiversity Information Facility Secretariat
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ***************************************************************************/

package org.gbif.metadata.eml;

import org.gbif.utils.file.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import freemarker.template.TemplateException;
import org.junit.Test;
import org.xml.sax.SAXException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class EmlWriterTest {

  @Test
  public void testRoundtrip() {
    try {
      // read EML
      Eml eml = EmlFactory.build(FileUtils.classpathStream("eml/sample.xml"));
      assertNotNull(eml);

      // write EML
      File temp = File.createTempFile("eml", ".xml");
      temp.deleteOnExit();
      System.out.println("Writing temporary test eml file to " + temp.getAbsolutePath());
      EmlWriter.writeEmlFile(temp, eml);

      // read EML
      Eml eml2 = EmlFactory.build(new FileInputStream(temp));
      assertNotNull(eml2);

      // compare
      assertEquals("Tanzanian Entomological Collection", eml.getTitle());
      assertEquals(eml2.getGuid(), eml.getGuid());
      assertEquals(eml2.getPackageId(), eml.getPackageId());
      assertEquals(eml2.getMetadataLanguage(), eml.getMetadataLanguage());
      assertEquals(eml2.getTitle(), eml.getTitle());
      assertEquals(eml2.getDescription(), eml.getDescription());
      assertEquals(eml2.getPubDate(), eml.getPubDate());
      // TODO: fix timezone parsing/writing
      // Sth unknown does go wrong here...
      // assertEquals(eml2.getDateStamp(), eml.getDateStamp());
      assertEquals(eml2.getCitation(), eml.getCitation());
      assertEquals(eml2.getLogoUrl(), eml.getLogoUrl());
      assertEquals(eml2.getHomepageUrl(), eml.getHomepageUrl());
      assertEquals(eml2.getContact(), eml.getContact());
      assertEquals(eml2.getMetadataProvider(), eml.getMetadataProvider());
      assertEquals(eml2.getResourceCreator(), eml.getResourceCreator());
      assertEquals(eml2.getDistributionUrl(), eml.getDistributionUrl());
      assertEquals(eml2.getMetadataLanguage(), eml.getMetadataLanguage());

      // write EML again with more data
      KeywordSet ks = new KeywordSet();
      ks.add("Carla");
      ks.add("Maria");
      ks.add("Luise");
      eml.addKeywordSet(ks);

      TaxonomicCoverage tc = new TaxonomicCoverage();
      tc.addTaxonKeywords("Abies alba; Puma concolor; Luzula luzuloides var. luzuloides");
      eml.addTaxonomicCoverage(tc);
      EmlWriter.writeEmlFile(temp, eml);

    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void testSetNonNullPubDate() {
    try {
      // read EML
      Eml eml = EmlFactory.build(FileUtils.classpathStream("eml/sample3.xml"));
      assertNotNull(eml);
      assertNull(eml.getPubDate());

      String pubDate = "2011-02-07";
      eml.setPubDateAsString(pubDate);

      // write EML
      File temp = File.createTempFile("eml", ".xml");
      System.out.println("Writing temporary test eml file to " + temp.getAbsolutePath());
      EmlWriter.writeEmlFile(temp, eml);

      // now read the EML in again and ensure pubDate is not null
      Eml eml2 = EmlFactory.build(new FileInputStream(temp));
      assertNotNull(eml2);

      System.out.println("New pub date: " + eml2.getPubDate());
      assertNotNull(eml2.getPubDate());

    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void testSetNullPubDate() {
    try {
      // read EML
      Eml eml = EmlFactory.build(FileUtils.classpathStream("eml/sample.xml"));
      assertNotNull(eml);

      Date pubDate = null;
      eml.setPubDate(pubDate);

      // write EML
      File temp = File.createTempFile("eml", ".xml");
      System.out.println("Writing temporary test eml file to " + temp.getAbsolutePath());
      EmlWriter.writeEmlFile(temp, eml);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void testXmlEscaping() {
    try {
      // read EML to have some defaults
      Eml eml = EmlFactory.build(FileUtils.classpathStream("eml/sample.xml"));
      assertNotNull(eml);

      // use ampersand values
      Agent gbif = new Agent();
      gbif.setOrganisation("GBIF & EOL");
      eml.setContact(gbif);
      eml.setTitle("The <very> important \"resources\" & other things");

      // write EML
      File temp = File.createTempFile("eml", ".xml");
      System.out.println("Writing temporary xml escaping test eml file to " + temp.getAbsolutePath());
      EmlWriter.writeEmlFile(temp, eml);

      // now read the EML in again and ensure pubDate is not null
      Eml eml2 = EmlFactory.build(new FileInputStream(temp));
      assertNotNull(eml2);
      assertEquals("GBIF & EOL", eml2.getContact().getOrganisation());
      assertEquals("The <very> important \"resources\" & other things", eml2.getTitle());

    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void testEmptyFormatVersion() throws IOException, TemplateException, SAXException {
    // read EML
    Eml eml = EmlFactory.build(FileUtils.classpathStream("eml/sample.xml"));
    assertNotNull(eml);
    assertEquals(2, eml.getPhysicalData().size());
    // ensure the format version is optional - non existing = null
    assertNull(eml.getPhysicalData().get(0).getFormatVersion());
    // ensure the format version can be a String! In 1.0.1 it used to be a decimal
    assertEquals("2.0.97", eml.getPhysicalData().get(1).getFormatVersion());

    // create new PhysicalData, that has empty-string format version
    PhysicalData data = new PhysicalData();

    // empty string - should not be written to EML.xml
    data.setFormatVersion("");
    data.setName("Original dataset");
    data.setCharset("UTF-8");
    data.setFormat("MS Excel Spreadsheet");
    // becomes the 2nd Physical data object
    eml.addPhysicalData(data);

    // write EML
    File temp = File.createTempFile("eml", ".xml");
    System.out.println("Writing temporary test eml file to " + temp.getAbsolutePath());
    EmlWriter.writeEmlFile(temp, eml);

    // now read the EML in again and ensure format version is null - remember it's the 3rd Physical Data
    Eml eml2 = EmlFactory.build(new FileInputStream(temp));
    assertNotNull(eml2);
    assertEquals(null, eml2.getPhysicalData().get(2).getFormatVersion());
    assertEquals("UTF-8", eml2.getPhysicalData().get(2).getCharset());
  }
}
