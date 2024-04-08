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

import org.gbif.api.model.registry.Contact;
import org.gbif.api.model.registry.Dataset;
import org.gbif.api.model.registry.eml.temporal.DateRange;
import org.gbif.api.model.registry.eml.temporal.SingleDate;
import org.gbif.api.model.registry.eml.temporal.TemporalCoverage;
import org.gbif.api.model.registry.eml.temporal.VerbatimTimePeriod;
import org.gbif.api.model.registry.eml.temporal.VerbatimTimePeriodType;
import org.gbif.api.util.ContactAdapter;
import org.gbif.api.vocabulary.ContactType;
import org.gbif.metadata.common.parse.ParagraphContainer;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.concurrent.ThreadSafe;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * A simple tool to serialize a dataset object into an XML document compliant with the latest
 * version of the GBIF Metadata Profile, currently version 1.3.
 */
@ThreadSafe
public class EMLWriter {

  // Define pairs of DocBook tags. MUST MATCH HTML tags!
  private static final String[] DOCBOOK_TAGS = {
    "<section>", "</section>",
    "<title>", "</title>",
    "<para>", "</para>",
    "<itemizedlist>", "</itemizedlist>",
    "<listitem>", "</listitem>",
    "<orderedlist>", "</orderedlist>",
    "<emphasis>", "</emphasis>",
    "<subscript>", "</subscript>",
    "<superscript>", "</superscript>",
    "<literalLayout>", "</literalLayout>"
  };

  // Define pairs of HTML tags. MUST MATCH DocBook tags!
  private static final String[] HTML_TAGS = {
    "<div>", "</div>",
    "<h1>", "</h1>",
    "<p>", "</p>",
    "<ul>", "</ul>",
    "<li>", "</li>",
    "<ol>", "</ol>",
    "<em>", "</em>",
    "<sub>", "</sub>",
    "<sup>", "</sup>",
    "<code>", "</code>"
  };

  private static final String TEMPLATE_PATH = "/gbif-eml-profile-template";
  private final Configuration freemarkerConfig;
  private final boolean useDoiAsIdentifier;
  private final boolean omitXmlDeclaration;

  /**
   * Private constructor, use {@link #newInstance()}
   *
   * @param cfg
   */
  private EMLWriter(Configuration cfg, boolean useDoiAsIdentifier, boolean omitXmlDeclaration) {
    this.freemarkerConfig = cfg;
    this.useDoiAsIdentifier = useDoiAsIdentifier;
    this.omitXmlDeclaration = omitXmlDeclaration;
  }

  /**
   * Get a new instance of EMLWriter with default Freemarker configuration. Same as calling {@link
   * #newInstance(boolean)} method with useDoiAsIdentifier = false
   *
   * @return new instance
   */
  public static EMLWriter newInstance() {
    return new EMLWriter(
        DatasetXMLWriterConfigurationProvider.provideFreemarker(TEMPLATE_PATH), false, false);
  }

  /**
   * Get a new instance of EMLWriter with default Freemarker configuration.
   *
   * @param useDoiAsIdentifier should the packageId be the dataset.doi? If true, the dataset.doi
   *     won't be included in the list of alternate identifiers
   * @return
   */
  public static EMLWriter newInstance(boolean useDoiAsIdentifier) {
    return new EMLWriter(
        DatasetXMLWriterConfigurationProvider.provideFreemarker(TEMPLATE_PATH),
        useDoiAsIdentifier,
        false);
  }

  /**
   * Get a new instance of EMLWriter with default Freemarker configuration.
   *
   * @param useDoiAsIdentifier should the packageId be the dataset.doi? If true, the dataset.doi
   *     won't be included in the list of alternate identifiers
   * @param omitXmlDeclaration should the XML declaration be omitted in the generated document
   * @return
   */
  public static EMLWriter newInstance(boolean useDoiAsIdentifier, boolean omitXmlDeclaration) {
    return new EMLWriter(
        DatasetXMLWriterConfigurationProvider.provideFreemarker(TEMPLATE_PATH),
        useDoiAsIdentifier,
        omitXmlDeclaration);
  }

  /**
   * Write a document from a Dataset object for the latest version.
   *
   * @param dataset non null dataset object
   * @param writer where the output document will go. The writer is not closed by this method.
   * @throws IOException if an error occurs while processing the template
   */
  public void writeTo(Dataset dataset, Writer writer) throws IOException {
    innerWrite(dataset, writer, EMLProfileVersion.GBIF_1_3);
  }

  /**
   * Write a document from a Dataset object for a specific EML version.
   *
   * @param dataset non null dataset object
   * @param writer where the output document will go. The writer is not closed by this method.
   * @param emlProfileVersion EML profile version
   * @throws IOException if an error occurs while processing the template
   */
  public void writeTo(Dataset dataset, Writer writer, EMLProfileVersion emlProfileVersion)
      throws IOException {
    innerWrite(dataset, writer, emlProfileVersion);
  }

  private void innerWrite(Dataset dataset, Writer writer, EMLProfileVersion emlProfileVersion)
      throws IOException {
    Objects.requireNonNull(dataset, "Dataset can't be null");

    Map<String, Object> map = new HashMap<>();
    map.put("dataset", dataset);
    map.put("eml", new EmlDatasetWrapper(dataset));
    map.put("useDoiAsIdentifier", useDoiAsIdentifier);
    map.put("omitXmlDeclaration", omitXmlDeclaration);

    String emlTemplate = String.format("eml-dataset-%s.ftl", emlProfileVersion.getVersion());

    try {
      freemarkerConfig.getTemplate(emlTemplate).process(Collections.unmodifiableMap(map), writer);
    } catch (TemplateException e) {
      throw new IOException(
          "Error while processing the EML Freemarker template for dataset " + dataset.getKey(), e);
    }
  }

  /**
   * Wrapper for a dataset instance that exposes some EML specific methods. Mostly used for
   * generating EML, see EMLWriter. This class requires to be public to be used in the Freemarker
   * template.
   */
  public static class EmlDatasetWrapper {

    private final Dataset dataset;
    private final ContactAdapter contactAdapter;

    public EmlDatasetWrapper(Dataset dataset) {
      this.dataset = dataset;
      this.contactAdapter = new ContactAdapter(dataset.getContacts());
    }

    public List<Contact> getAssociatedParties() {
      return contactAdapter.getAssociatedParties();
    }

    public Contact getResourceCreator() {
      return contactAdapter.getResourceCreator();
    }

    /** @return list of {@link Contact} of type ContactType.ORIGINATOR */
    public List<Contact> getCreators() {
      return contactAdapter.getCreators();
    }

    public Contact getAdministrativeContact() {
      return contactAdapter.getAdministrativeContact();
    }

    /** @return list of {@link Contact} of type ContactType.ADMINISTRATIVE_POINT_OF_CONTACT */
    public List<Contact> getContacts() {
      return contactAdapter.getContacts();
    }

    public List<String> getDescription() {
      return new ParagraphContainer(dataset.getDescription()).getParagraphs();
    }

    // Value with all HTML tags replaced by DocBook analogues
    public String getDocBookField(String fieldName) {
      String result = null;

      try {
        String value = BeanUtils.getProperty(dataset, fieldName);

        if (value != null) {
          result = replaceDocBookElements(value);
        }
      } catch (Exception e) {
        // TODO log exception
      }

      return result;
    }

    private String replaceDocBookElements(String value) {
      String htmlStringWithLinksReplaces =
          value.replaceAll(
              "<a\\s+href=\"(.*?)\">\\s*(.*?)\\s*</a>",
              "<ulink url=\"$1\"><citetitle>$2</citetitle></ulink>");

      // Perform replacements
      return StringUtils.replaceEach(htmlStringWithLinksReplaces, HTML_TAGS, DOCBOOK_TAGS);
    }

    public Contact getMetadataProvider() {
      return contactAdapter.getFirstPreferredType(ContactType.METADATA_AUTHOR);
    }

    /** @return list of {@link Contact} of type ContactType.METADATA_AUTHOR */
    public List<Contact> getMetadataProviders() {
      return contactAdapter.getMetadataProviders();
    }

    /**
     * @return list of all formation periods {@link VerbatimTimePeriodType} of type
     *     VerbatimTimePeriodType.FORMATION_PERIOD
     */
    public List<VerbatimTimePeriod> getFormationPeriods() {
      return getTimePeriods(VerbatimTimePeriodType.FORMATION_PERIOD);
    }

    /**
     * @return list of all formation periods {@link VerbatimTimePeriodType} of type
     *     VerbatimTimePeriodType.LIVING_TIME_PERIOD
     */
    public List<VerbatimTimePeriod> getLivingTimePeriods() {
      return getTimePeriods(VerbatimTimePeriodType.LIVING_TIME_PERIOD);
    }

    /** @return list of all {@link VerbatimTimePeriodType} of specified type */
    private List<VerbatimTimePeriod> getTimePeriods(VerbatimTimePeriodType type) {
      List<VerbatimTimePeriod> periods = new ArrayList<>();
      for (TemporalCoverage tc : dataset.getTemporalCoverages()) {
        if (tc instanceof VerbatimTimePeriod) {
          VerbatimTimePeriod tp = (VerbatimTimePeriod) tc;
          if (type.equals(tp.getType())) {
            periods.add(tp);
          }
        }
      }
      return periods;
    }

    /**
     * @return list of all {@link SingleDate} and {@link DateRange} {@link TemporalCoverage} or an
     *     empty list if none found
     */
    public List<TemporalCoverage> getSingleDateAndDateRangeCoverages() {
      List<TemporalCoverage> periods = new ArrayList<>();
      for (TemporalCoverage tc : dataset.getTemporalCoverages()) {
        if (tc instanceof DateRange || tc instanceof SingleDate) {
          periods.add(tc);
        }
      }
      return periods;
    }
  }
}
