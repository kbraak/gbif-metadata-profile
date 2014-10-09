/*
 * Copyright 2009 GBIF.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gbif.metadata.eml;

import org.gbif.metadata.BasicMetadata;
import org.gbif.metadata.DateUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * The EML model is a POJO representing the GBIF Extended Metadata Profile for the IPT 1.1 In addition to standard Bean
 * encapsulation, additional methods exist to simplify the implementation of an EML XML parser.
 *
 * @see EmlFactory
 */
public class Eml implements Serializable, BasicMetadata {

  private static final Pattern PACKAGED_ID_PATTERN = Pattern.compile("/v([0-9]+)$");

  private static final Joiner JOINER = Joiner.on("; ").useForNull("");
  private static final Splitter SEMICOLON_SPLITTER = Splitter.on(';');
  private static final Splitter COMMA_SPLITTER = Splitter.on(',');
  private static final Splitter PIPE_SPLITTER = Splitter.on('|');

  /**
   * Generated
   */
  private static final long serialVersionUID = 770733523572837495L;

  private String description;

  /**
   * This is not in the GBIF extended metadata document, but seems like a sensible placeholder that can be used to
   * capture anything missing, and maps nicely in EML, therefore is added
   */
  private String additionalInfo;

  private List<String> alternateIdentifiers = Lists.newArrayList();

  /**
   * The 'associatedParty' element provides the full name of other people, organizations, or positions who should be
   * associated with the resource. These parties might play various roles in the creation or maintenance of the
   * resource, and these roles should be indicated in the "role" element.
   */
  private List<Agent> associatedParties = Lists.newArrayList();

  private BibliographicCitationSet bibliographicCitationSet = new BibliographicCitationSet();

  /**
   * A resource that describes a literature citation for the resource, one that might be found in a bibliography. We
   * cannot use http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml.html#citation because the IPT deals with
   * /eml/dataset and not /eml/citation therefore these are found in the additionalMetadata section of the EML.
   */
  private Citation citation;

  /**
   * Date of metadata creation or the last metadata update Default to now(), but can be overridden
   */
  private Date dateStamp = new Date();
  /**
   * The distributionType URL is generally meant for informational purposes, and the "function" attribute should be set
   * to "information".
   */
  private String distributionUrl;
  /**
   * Serialised data
   */
  private int emlVersion = 0;
  private List<GeospatialCoverage> geospatialCoverages = Lists.newArrayList();

  /**
   * Dataset level to which the metadata applies. The default value for GBIF is "dataset"
   *
   * @see <a href="http://www.fgdc.gov/standards/projects/incits-l1-standards-projects/NAP-Metadata
   *      /napMetadataProfileV101.pdf>NAP Metadata</a>
   */
  private String hierarchyLevel = "dataset";

  /**
   * A rights management statement for the resource, or reference a service providing such information. Rights
   * information encompasses Intellectual Property Rights (IPR), Copyright, and various Property Rights. In the case of
   * a data set, rights might include requirements for use, requirements for attribution, or other requirements the
   * owner would like to impose.
   *
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-resource.html#intellectualRights>EML
   *      Resource intellectualRights keyword</a>
   */
  private String intellectualRights;

  /**
   * A quantitative descriptor (number of specimens, samples or batches). The actual quantification could be covered by
   * 1) an exact number of �JGI-units� in the collection plus a measure of uncertainty (+/- x); 2) a range of numbers
   * (x
   * to x), with the lower value representing an exact number, when the higher value is omitted.
   */
  private List<JGTICuratorialUnit> jgtiCuratorialUnits = Lists.newArrayList();

  // Note that while Sets would be fine, to ease testing, Lists are
  // used to preserve ordering. A Set implementation that respects ordering
  // would also suffice
  // please refer to typed classes for descriptions of the properties and how
  // they map to EML
  private List<KeywordSet> keywords = Lists.newArrayList();

  /**
   * The language in which the resource is written. This can be a well-known language name, or one of the ISO language
   * codes to be more precise.
   * The IPT will always use ISO language codes.
   *
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-resource.html#language">EML Resource
   *      language keyword</a>
   */
  private String language = "en";

  /**
   * URL of the logo associated with a resource.
   */
  private String logoUrl;

  /**
   * Language of the metadata composed of an ISO639-2/T three letter language code and an ISO3166-1 three letter
   * country
   * code.
   */
  private String metadataLanguage = "en";

  /**
   * The GBIF metadata profile states "Describes other languages used in metadata free text description. Consists of
   * language, country and characterEncoding" In Java world, a LocaleBundle handles this concisely
   */
  private LocaleBundle metadataLocale;

  private List<Collection> collections = Lists.newArrayList();

  private List<PhysicalData> physicalData = Lists.newArrayList();

  /**
   * The project this resource is associated with
   */
  private Project project = new Project();

  /**
   * The date that the resource was published. The format should be represented as: CCYY, which represents a 4 digit
   * year, or as CCYY-MM-DD, which denotes the full year, month, and day. Note that month and day are optional
   * components. Formats must conform to ISO 8601. http://knb.ecoinformatics.org/
   * software/eml/eml-2.1.0/eml-resource.html#pubDate
   */
  private Date pubDate;

  /**
   * This is not in the GBIF extended metadata document, but seems like a sensible field to maintain, and maps nicely
   * in EML, therefore is added.
   */
  private String purpose;

  /**
   * A text description of the maintenance of this data resource.
   *
   * @see <a href="https://knb.ecoinformatics.org/#external//emlparser/docs/eml-2.1.1/./eml-dataset.html#description">MaintenanceUpdateFrequency description</a>
   */
  private String updateFrequencyDescription;

  /**
   * The maintenance update frequency is the frequency with which changes and additions are made to the dataset after
   * the initial dataset is completed.
   *
   * @see <a href="https://knb.ecoinformatics.org/#external//emlparser/docs/eml-2.1.1/./eml-dataset.html#MaintUpFreqType">MaintUpFreqType EML ENUM</a>
   */
  private MaintenanceUpdateFrequency updateFrequency = MaintenanceUpdateFrequency.UNKOWN;

  /**
   * The 'creator' element provides the full name of the person, organization, or position who created the resource.
   * The list of creators for a resource represent the people and organizations who should be cited for the resource.
   *
   * @see <a href="https://knb.ecoinformatics.org/#external//emlparser/docs/eml-2.1.1/./eml-resource.html#creator">EML Resource creator</a>
   */
  private List<Agent> creators = Lists.newArrayList();

  /**
   * The 'metadataProvider' element provides the full name of the person, organization, or position who created
   * documentation for the resource.
   *
   * @see <a href="https://knb.ecoinformatics.org/#external//emlparser/docs/eml-2.1.1/./eml-resource.html#metadataProvider">EML Resource metadataProvider</a>
   */
  private List<Agent> metadataProviders = Lists.newArrayList();

  /**
   * The 'contact' field contains contact information for this dataset. This is the person or institution to contact
   * with questions about the use, interpretation of a data set.
   *
   * @see <a href="https://knb.ecoinformatics.org/#external//emlparser/docs/eml-2.1.1/./eml-dataset.html#contact">EML Dataset contact</a>
   */
  private List<Agent> contacts = Lists.newArrayList();

  /**
   * Picklist keyword indicating the process or technique used to prevent physical deterioration of non-living
   * collections. Expected to contain an instance from the Specimen Preservation Method Type Term vocabulary.
   *
   * @see <a href="http://rs.tdwg.org/ontology/voc/Collection#specimenPreservationMethod">TDWG Natural Collection
   *      Description</a>
   */
  private String specimenPreservationMethod;

  private List<TaxonomicCoverage> taxonomicCoverages = Lists.newArrayList();

  private List<TemporalCoverage> temporalCoverages = Lists.newArrayList();

  /**
   * URL linking to the resource homepage
   */
  private String link;

  private String guid;

  private String title;

  /**
   * The coverage field allows for a textual description of the specific sampling area, the sampling frequency
   * (temporal boundaries, frequency of occurrence), and groups of living organisms sampled (taxonomic coverage). This
   * implementation allows only the declaration of the extent description
   *
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-methods.html#studyExtent">EML Methods
   *      studyExtent keyword</a>
   */
  private String studyExtent;

  /**
   * The samplingDescription field allows for a text-based/human readable description of the sampling procedures used
   * in the research project. The content of this element would be similar to a description of sampling procedures found
   * in the methods section of a journal article.
   *
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-methods.html#samplingDescription">EML
   *      Methods samplingDescription keyword</a>
   */
  private String sampleDescription;

  /**
   * The qualityControl field provides a location for the description of actions taken to either control or assess the
   * quality of data resulting from the associated method step.
   *
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-methods.html#qualityControl">EML Methods
   *      qualityControl keyword</a>
   */
  private String qualityControl;

  /**
   * The methodStep field allows for repeated sets of elements that document a series of procedures followed to
   * produce a data object. These include text descriptions of the procedures, relevant literature, software,
   * instrumentation, source data and any quality control measures taken." This implementation allows only the
   * declaration of the step description
   *
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-methods.html#methodStep">EML Methods
   *      methodStep keyword</a>
   */
  private List<String> methodSteps = Lists.newArrayList();

  /**
   * Default constructor needed by Struts2
   */
  public Eml() {
    this.pubDate = new Date();
  }

  public String getAdditionalInfo() {
    if (additionalInfo == null || additionalInfo.isEmpty()) {
      return null;
    }
    return additionalInfo;
  }

  public void setAdditionalInfo(String additionalInfo) {
    this.additionalInfo = additionalInfo;
  }

  public List<String> getAlternateIdentifiers() {
    return alternateIdentifiers;
  }

  public void setAlternateIdentifiers(List<String> alternateIdentifiers) {
    this.alternateIdentifiers = alternateIdentifiers;
  }

  public List<Agent> getAssociatedParties() {
    return associatedParties;
  }

  public void setAssociatedParties(List<Agent> associatedParties) {
    this.associatedParties = associatedParties;
  }

  public BibliographicCitationSet getBibliographicCitationSet() {
    return bibliographicCitationSet;
  }

  public void setBibliographicCitationSet(BibliographicCitationSet val) {
    bibliographicCitationSet = val;
  }

  public Citation getCitation() {
    return citation;
  }

  public void setCitation(Citation citation) {
    this.citation = citation;
  }

    /**
     * Return the primary creator, the first creator in the list of creators. If there are
     * no creators, return the primary contact, the first contact in the list of contacts.
     *
     * @return the creator, or null if no creators or contacts exist
     */
    private Agent getCreator() {
        if (!creators.isEmpty()) {
            return creators.get(0);
        }
        if (!contacts.isEmpty()) {
            return contacts.get(0);
        }
        return null;
    }

    /**
     * Return the primary metadataProvider, the first metadataProvider in the list of metadataProviders. If there are
     * no metadataProviders, return the primary contact, the first contact in the list of contacts.
     *
     * @return the metadataProvider, or null if no metadataProviders or contacts exist
     */
    private Agent getPublisher() {
        if (!metadataProviders.isEmpty()) {
            return metadataProviders.get(0);
        }
        if (!contacts.isEmpty()) {
            return contacts.get(0);
        }
        return null;
    }

  public Date getDateStamp() {
    return dateStamp;
  }

  public void setDateStamp(Date dateStamp) {
    this.dateStamp = dateStamp;
  }

  /**
   * Utility to set the date with a textual format
   *
   * @param dateString To set
   *
   * @throws ParseException Should it be an erroneous format
   */
  public void setDateStamp(String dateString) throws ParseException {
    dateStamp = DateUtils.schemaDateTime(dateString);
  }

  public String getDistributionUrl() {
    if (distributionUrl == null || distributionUrl.isEmpty()) {
      return null;
    }
    return distributionUrl;
  }

  public void setDistributionUrl(String distributionUrl) {
    this.distributionUrl = distributionUrl;
  }

  public int getEmlVersion() {
    return emlVersion;
  }

  public void setEmlVersion(int emlVersion) {
    this.emlVersion = emlVersion;
  }

  public List<GeospatialCoverage> getGeospatialCoverages() {
    return geospatialCoverages;
  }

  public void setGeospatialCoverages(List<GeospatialCoverage> geospatialCoverages) {
    this.geospatialCoverages = geospatialCoverages;
  }

  public String getGuid() {
    return guid;
  }

  public void setGuid(String guid) {
    this.guid = guid;
  }

  public String getHierarchyLevel() {
    if (hierarchyLevel == null || hierarchyLevel.isEmpty()) {
      return null;
    }
    return hierarchyLevel;
  }

  public void setHierarchyLevel(String hierarchyLevel) {
    this.hierarchyLevel = hierarchyLevel;
  }

  public String getIntellectualRights() {
    if (intellectualRights == null || intellectualRights.isEmpty()) {
      return null;
    }
    return intellectualRights;
  }

  public void setIntellectualRights(String intellectualRights) {
    this.intellectualRights = intellectualRights;
  }

  public List<JGTICuratorialUnit> getJgtiCuratorialUnits() {
    return jgtiCuratorialUnits;
  }

  public void setJgtiCuratorialUnits(List<JGTICuratorialUnit> jgtiCuratorialUnit) {
    this.jgtiCuratorialUnits = jgtiCuratorialUnit;
  }

  public List<KeywordSet> getKeywords() {
    return keywords;
  }

  public void setKeywords(List<KeywordSet> keywords) {
    this.keywords = keywords;
  }

  public String getLanguage() {
    if (language == null || language.isEmpty()) {
      return null;
    }
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  @Override
  public String getLogoUrl() {
    if (logoUrl == null || logoUrl.isEmpty()) {
      return null;
    }
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  public String getMetadataLanguage() {
    if (metadataLanguage == null || metadataLanguage.isEmpty()) {
      return null;
    }
    return metadataLanguage;
  }

  public void setMetadataLanguage(String language) {
    metadataLanguage = language;
  }

  public LocaleBundle getMetadataLocale() {
    return metadataLocale;
  }

  public void setMetadataLocale(LocaleBundle metadataLocale) {
    this.metadataLocale = metadataLocale;
  }

  public List<String> getMethodSteps() {
    return methodSteps;
  }

  public void setMethodSteps(List<String> methodSteps) {
    this.methodSteps = methodSteps;
  }

  public List<Collection> getCollections() {
      return collections;
  }

  public void setCollections(List<Collection> collections) {
      this.collections = collections;
  }

  public List<PhysicalData> getPhysicalData() {
    return physicalData;
  }

  public void setPhysicalData(List<PhysicalData> physicalData) {
    this.physicalData = physicalData;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Date getPubDate() {
    return pubDate;
  }

  public void setPubDate(Date pubDate) {
    this.pubDate = pubDate;
  }

  public String getPurpose() {
    if (purpose == null || purpose.isEmpty()) {
      return null;
    }
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public String getUpdateFrequencyDescription() {
    return updateFrequencyDescription;
  }

  public void setUpdateFrequencyDescription(String updateFrequencyDescription) {
    this.updateFrequencyDescription = updateFrequencyDescription;
  }

  public MaintenanceUpdateFrequency getUpdateFrequency() {
    return updateFrequency;
  }

  /**
   * Sets the updateFrequency ENUM. The incoming string is matched to the MaintenanceUpdateFrequency's
   * displayValue, otherwise it's set to null.
   *
   * @param updateFrequency as per the EML enumeration in lowerCamel case, e.g. asNeeded
   */
  public void setUpdateFrequency(String updateFrequency) {
    this.updateFrequency = MaintenanceUpdateFrequency.findByDisplayValue(updateFrequency);
  }

  public List<Agent> getCreators() {
    return creators;
  }

  public void setCreators(List<Agent> creators) {
    this.creators = creators;
  }

  public List<Agent> getMetadataProviders() {
    return metadataProviders;
  }

  public void setMetadataProviders(List<Agent> metadataProviders) {
    this.metadataProviders = metadataProviders;
  }

  public List<Agent> getContacts() {
    return contacts;
  }

  public void setContacts(List<Agent> contacts) {
    this.contacts = contacts;
  }

  public String getQualityControl() {
    return qualityControl;
  }

  public void setQualityControl(String qualityControl) {
    this.qualityControl = qualityControl;
  }

  public String getSampleDescription() {
    return sampleDescription;
  }

  public void setSampleDescription(String sampleDescription) {
    this.sampleDescription = sampleDescription;
  }

  public String getSpecimenPreservationMethod() {
    if (specimenPreservationMethod == null || specimenPreservationMethod.isEmpty()) {
      return null;
    }
    return specimenPreservationMethod;
  }

  public void setSpecimenPreservationMethod(String specimenPreservationMethod) {
    this.specimenPreservationMethod = specimenPreservationMethod;
  }

  public String getStudyExtent() {
    return studyExtent;
  }

  public void setStudyExtent(String studyExtent) {
    this.studyExtent = studyExtent;
  }

  public List<TaxonomicCoverage> getTaxonomicCoverages() {
    return taxonomicCoverages;
  }

  public void setTaxonomicCoverages(List<TaxonomicCoverage> taxonomicCoverages) {
    this.taxonomicCoverages = taxonomicCoverages;
  }

  public List<TemporalCoverage> getTemporalCoverages() {
    return temporalCoverages;
  }

  public void setTemporalCoverages(List<TemporalCoverage> temporalCoverages) {
    this.temporalCoverages = temporalCoverages;
  }

  @Override
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String getCitationString() {
    if (citation != null) {
      return citation.getCitation();
    }
    return null;
  }

  @Override
  public String getCreatorEmail() {
    Agent creator = getCreator();
    if (creator != null) {
      return creator.getEmail();
    }
    return null;
  }

  @Override
  public String getCreatorName() {
    Agent creator = getCreator();
    if (creator != null) {
      return creator.getFullName();
    }
    return null;
  }

  @Override
  public String getDescription() {
    return getAbstract();
  }

  /**
   * HomepageUrl is equal the distributionUrl.
   *
   * @return homepageUrl
   */
  @Override
  public String getHomepageUrl() {
    return distributionUrl;
  }

  @Override
  public String getIdentifier() {
    return guid;
  }

  @Override
  public Date getPublished() {
    return pubDate;
  }

  @Override
  public String getPublisherEmail() {
    Agent publisher = getPublisher();
    if (publisher != null) {
      return publisher.getEmail();
    }
    return null;
  }

  @Override
  public String getPublisherName() {
    Agent publisher = getPublisher();
    if (publisher != null) {
      return publisher.getFullName();
    }
    return null;
  }

  @Override
  public String getRights() {
    return intellectualRights;
  }

  @Override
  public String getSubject() {
    List<String> subjects = new ArrayList<String>();
    for (KeywordSet ks : keywords) {
      subjects.add(JOINER.join(ks.getKeywords()));
    }
    return JOINER.join(subjects);
  }

  public void addAlternateIdentifier(String alternateIdentifier) {
    alternateIdentifiers.add(alternateIdentifier);
  }

  /**
   * Utility to add an agent to the creators list. This method was introduced to ease the Digester rules for parsing of
   * EML.
   *
   * @param agent to add
   */
  public void addCreator(Agent agent) {
    creators.add(agent);
  }

  /**
   * Utility to add an agent to the metadataProviders list. This method was introduced to ease the Digester rules for
   * parsing of EML.
   *
   * @param agent to add
   */
  public void addMetadataProvider(Agent agent) {
    metadataProviders.add(agent);
  }

  /**
   * Utility to add an agent to the contacts list. This method was introduced to ease the Digester rules for parsing of
   * EML.
   *
   * @param agent to add
   */
  public void addContact(Agent agent) {
    contacts.add(agent);
  }

  /**
   * Utility to add an Agent to the associatedParties list. This method was introduced to ease the Digester rules for
   * parsing of EML.
   *
   * @param agent to add
   */
  public void addAssociatedParty(Agent agent) {
    if (agent.getRole() == null) {
      agent.setRole("AssociatedParty");
    }
    associatedParties.add(agent);
  }

  /**
   * utility to add a bibliographic citation to the bibliographicCitations. This method was introduced to ease the
   * Digester rules for parsing of EML.
   *
   * @param citations to add
   */
  public void addBibliographicCitations(List<Citation> citations) {
    bibliographicCitationSet.getBibliographicCitations().addAll(citations);
  }

  /**
   * utility to add a coverage to the coverages This method was introduced to ease the Digester rules for parsing of
   * EML
   *
   * @param geospatialCoverage to add
   */
  public void addGeospatialCoverage(GeospatialCoverage geospatialCoverage) {
    geospatialCoverages.add(geospatialCoverage);
  }

  /**
   * utility to add a jgtiCuratorialUnit to the list. This method was introduced to ease the Digester rules for parsing
   * of EML
   *
   * @param unit to add
   */
  public void addJgtiCuratorialUnit(JGTICuratorialUnit unit) {
    jgtiCuratorialUnits.add(unit);
  }

  /**
   * utility to add keywords to the keyword sets This method was introduced to ease the Digester rules for parsing of
   * EML
   *
   * @param keywordSet to add
   */
  public void addKeywordSet(KeywordSet keywordSet) {
    keywords.add(keywordSet);
  }

  /**
   * utility to add steps to the methodSteps list. This method was introduced to ease the Digester rules for parsing of
   * EML
   *
   * @param step to add
   */
  public void addMethodStep(String step) {
    methodSteps.add(step);
  }

  /**
   * utility to add a PhysicalData instance to the physicalData list. This method was introduced to ease the Digester
   * rules for parsing of EML
   *
   * @param physicalData to add
   */
  public void addPhysicalData(PhysicalData physicalData) {
    this.physicalData.add(physicalData);
  }

  /**
   * Utility to add a Collection instance to the collections list. This method was introduced to ease the Digester
   * rules for parsing of EML.
   *
   * @param collection to add
   */
  public void addCollection(Collection collection) {
    this.collections.add(collection);
  }

  /**
   * utility to add a coverage to the coverages This method was introduced to ease the Digester rules for parsing of
   * EML
   *
   * @param coverage to add
   */
  public void addTaxonomicCoverage(TaxonomicCoverage coverage) {
    taxonomicCoverages.add(coverage);
  }

  /**
   * utility to add a coverage to the coverages This method was introduced to ease the Digester rules for parsing of
   * EML
   *
   * @param coverage to add
   */
  public void addTemporalCoverage(TemporalCoverage coverage) {
    temporalCoverages.add(coverage);
  }

  public String getAbstract() {
    return description;
  }

  public List<Citation> getBibliographicCitations() {
    return bibliographicCitationSet.getBibliographicCitations();
  }

  public String getPackageId() {
    return guid + "/v" + emlVersion;
  }

  public int increaseEmlVersion() {
    emlVersion += 1;
    return emlVersion;
  }

  public void setAbstract(String description) {
    this.description = description;
  }

  public void setBibliographicCitations(List<Citation> val) {
    bibliographicCitationSet.setBibliographicCitations(val);
  }

  public void setCitation(String citation, String identifier) {
    this.citation = new Citation(citation, identifier);
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setKeywordSet(List<KeywordSet> keywords) {
    this.keywords = keywords;
  }

  public void setPackageId(String packageId) {
    Matcher m = PACKAGED_ID_PATTERN.matcher(packageId);
    if (m.find()) {
      emlVersion = Integer.valueOf(m.group(1));
      packageId = m.replaceAll("");
    }
    guid = packageId;
  }

  /**
   * Utility to set the date with a textual format.
   *
   * @param dateString To set
   *
   * @throws ParseException Should it be an erroneous format
   */
  public void setPubDateAsString(String dateString) throws ParseException {
    pubDate = DateUtils.calendarDate(dateString);
  }

  public void setPublished(Date published) {
    pubDate = published;
  }

  public void setSubject(List<String> keywords) {
    KeywordSet ks = new KeywordSet(keywords);
    List<KeywordSet> list = new ArrayList<KeywordSet>();
    list.add(ks);
    this.keywords = list;
  }

  public void setSubject(String keywords) {
    if (keywords != null) {
      Iterable<String> tokens;
      int commas = CharMatcher.is(',').countIn(keywords);
      int semicolon = CharMatcher.is(';').countIn(keywords);
      int pipes = CharMatcher.is('|').countIn(keywords);
      if (semicolon >= commas && semicolon >= pipes) {
        // semicolons
        tokens = SEMICOLON_SPLITTER.split(keywords);
      } else if (pipes >= semicolon && pipes >= commas) {
        // pipes
        tokens = PIPE_SPLITTER.split(keywords);
      } else {
        // commas
        tokens = COMMA_SPLITTER.split(keywords);
      }
      List<String> keyList = new ArrayList<String>();
      for (String kw : tokens) {
        String k = Strings.emptyToNull(kw.trim());
        keyList.add(k);
      }
      setSubject(keyList);
    }
  }

  /**
   * Sets the title also given the language. Used to support multiple translated titles in the eml source document
   * while the Eml java classes still only support a single title, preferably in english. The setter will use the first
   * title but prefer any english title over any other language.
   */
  public void setTitle(String title, String language) {
    if (this.title == null || "en".equalsIgnoreCase(language) || "eng".equalsIgnoreCase(language)) {
      this.title = title;
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Eml other = (Eml) obj;
    return Objects.equal(this.description, other.description)
            && Objects.equal(this.additionalInfo, other.additionalInfo)
            && Objects.equal(this.alternateIdentifiers, other.alternateIdentifiers)
            && Objects.equal(this.associatedParties, other.associatedParties)
            && Objects.equal(this.bibliographicCitationSet, other.bibliographicCitationSet)
            && Objects.equal(this.citation, other.citation) && Objects.equal(this.contacts, other.contacts)
            && Objects.equal(this.dateStamp, other.dateStamp)
            && Objects.equal(this.distributionUrl, other.distributionUrl)
            && Objects.equal(this.emlVersion, other.emlVersion)
            && Objects.equal(this.geospatialCoverages, other.geospatialCoverages)
            && Objects.equal(this.hierarchyLevel, other.hierarchyLevel)
            && Objects.equal(this.intellectualRights, other.intellectualRights)
            && Objects.equal(this.jgtiCuratorialUnits, other.jgtiCuratorialUnits)
            && Objects.equal(this.keywords, other.keywords) && Objects.equal(this.language, other.language)
            && Objects.equal(this.logoUrl, other.logoUrl)
            && Objects.equal(this.metadataLanguage, other.metadataLanguage)
            && Objects.equal(this.metadataLocale, other.metadataLocale)
            && Objects.equal(this.metadataProviders, other.metadataProviders)
            && Objects.equal(this.collections, other.collections)
            && Objects.equal(this.physicalData, other.physicalData) && Objects.equal(this.project, other.project)
            && Objects.equal(this.pubDate, other.pubDate) && Objects.equal(this.purpose, other.purpose)
            && Objects.equal(this.creators, other.creators)
            && Objects.equal(this.specimenPreservationMethod, other.specimenPreservationMethod)
            && Objects.equal(this.taxonomicCoverages, other.taxonomicCoverages)
            && Objects.equal(this.temporalCoverages, other.temporalCoverages) && Objects.equal(this.link, other.link)
            && Objects.equal(this.guid, other.guid) && Objects.equal(this.title, other.title)
            && Objects.equal(this.studyExtent, other.studyExtent)
            && Objects.equal(this.sampleDescription, other.sampleDescription)
            && Objects.equal(this.qualityControl, other.qualityControl)
            && Objects.equal(this.methodSteps, other.methodSteps);
  }

  @Override
  public int hashCode() {
    return Objects
      .hashCode(description, additionalInfo, alternateIdentifiers, associatedParties, bibliographicCitationSet,
        citation, contacts, dateStamp, distributionUrl, emlVersion, geospatialCoverages, hierarchyLevel,
        intellectualRights, jgtiCuratorialUnits, keywords, language, logoUrl, metadataLanguage, metadataLocale,
        metadataProviders, collections, physicalData, project, pubDate, purpose, creators,
        specimenPreservationMethod, taxonomicCoverages, temporalCoverages, link, guid, title, studyExtent,
        sampleDescription, qualityControl, methodSteps);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).
      add("description", description).
      add("additionalInfo", additionalInfo).
      add("alternateIdentifiers", alternateIdentifiers).
      add("associatedParties", associatedParties).
      add("bibliographicCitationSet", bibliographicCitationSet).
      add("citation", citation).
      add("contacts", contacts).
      add("dateStamp", dateStamp).
      add("distributionUrl", distributionUrl).
      add("emlVersion", emlVersion).
      add("geospatialCoverages", geospatialCoverages).
      add("hierarchyLevel", hierarchyLevel).
      add("intellectualRights", intellectualRights).
      add("jgtiCuratorialUnits", jgtiCuratorialUnits).
      add("keywords", keywords).
      add("language", language).
      add("logoUrl", logoUrl).
      add("metadataLanguage", metadataLanguage).
      add("metadataLocale", metadataLocale).
      add("metadataProviders", metadataProviders).
      add("collections", collections).
      add("physicalData", physicalData).
      add("project", project).
      add("pubDate", pubDate).
      add("purpose", purpose).
      add("creator", creators).
      add("specimenPreservationMethod", specimenPreservationMethod).
      add("taxonomicCoverages", taxonomicCoverages).
      add("temporalCoverages", temporalCoverages).
      add("link", link).
      add("guid", guid).
      add("title", title).
      add("studyExtent", studyExtent).
      add("sampleDescription", sampleDescription).
      add("qualityControl", qualityControl).
      add("methodSteps", methodSteps).
      toString();
  }
}
