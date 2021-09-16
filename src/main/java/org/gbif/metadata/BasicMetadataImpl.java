package org.gbif.metadata;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class BasicMetadataImpl implements Serializable, BasicMetadata {

  private static final long serialVersionUID = 12073642352837495L;

  private String title;
  private String sourceId;
  private List<String> description;
  private String homepageUrl;
  private String logoUrl;
  private String subject;
  private String rights;
  private String citation;
  private String creatorName;
  private String creatorEmail;
  private String publisherName;
  private String publisherEmail;
  private Date published;
  private Map<String, String> additionalMetadata = new HashMap<>();

  public Map<String, String> getAdditionalMetadata() {
    return additionalMetadata;
  }

  public void setAdditionalMetadata(Map<String, String> additionalMetadata) {
    this.additionalMetadata = additionalMetadata;
  }

  @Override
  public String getCreatorEmail() {
    return creatorEmail;
  }

  public void setCreatorEmail(String creatorEmail) {
    this.creatorEmail = creatorEmail;
  }

  @Override
  public String getCreatorName() {
    return creatorName;
  }

  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  @Override
  public List<String> getDescription() {
    return description;
  }

  public void setDescription(List<String> description) {
    this.description = description;
  }

  @Override
  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  @Override
  public Date getPublished() {
    return published;
  }

  public void setPublished(Date published) {
    this.published = published;
  }

  @Override
  public String getPublisherEmail() {
    return publisherEmail;
  }

  public void setPublisherEmail(String publisherEmail) {
    this.publisherEmail = publisherEmail;
  }

  @Override
  public String getPublisherName() {
    return publisherName;
  }

  public void setPublisherName(String publisherName) {
    this.publisherName = publisherName;
  }

  @Override
  public String getRights() {
    return rights;
  }

  public void setRights(String rights) {
    this.rights = rights;
  }

  public String getSourceId() {
    return sourceId;
  }

  public void setSourceId(String sourceId) {
    this.sourceId = sourceId;
  }

  @Override
  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
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
    return citation;
  }

  @Override
  public String getHomepageUrl() {
    return homepageUrl;
  }

  @Override
  public String getIdentifier() {
    return sourceId;
  }

  public void addAdditionalMetadata(String key, String value) {
    this.additionalMetadata.put(key, value);
  }

  /**
   * adds more subjects/keywords, concatenating it to the existing one
   */
  public void addSubject(String newSubject) {
    if (StringUtils.isBlank(subject)) {
      subject = newSubject;
    } else {
      subject += "; " + newSubject.trim();
    }
  }

  public String getAdditionalMetadata(String key) {
    return additionalMetadata.get(key);
  }

  public void getCitationString(String citation) {
    this.citation = citation;
  }

  public void setCitationString(String citation) {
    this.citation = citation;
  }

  public void setHomepageUrl(String hohomepageUrl) {
    this.homepageUrl = hohomepageUrl;
  }

  public void setSubject(List<String> keywords) {
    subject = keywords.stream()
        .map(StringUtils::trimToEmpty)
        .collect(Collectors.joining("; "));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BasicMetadataImpl that = (BasicMetadataImpl) o;
    return Objects.equals(title, that.title)
        && Objects.equals(sourceId, that.sourceId)
        && Objects.equals(description, that.description)
        && Objects.equals(homepageUrl, that.homepageUrl)
        && Objects.equals(logoUrl, that.logoUrl)
        && Objects.equals(subject, that.subject)
        && Objects.equals(rights, that.rights)
        && Objects.equals(citation, that.citation)
        && Objects.equals(creatorName, that.creatorName)
        && Objects.equals(creatorEmail, that.creatorEmail)
        && Objects.equals(publisherName, that.publisherName)
        && Objects.equals(publisherEmail, that.publisherEmail)
        && Objects.equals(published, that.published)
        && Objects.equals(additionalMetadata, that.additionalMetadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, sourceId, description, homepageUrl, logoUrl, subject, rights, citation, creatorName,
        creatorEmail, publisherName, publisherEmail, published, additionalMetadata);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", BasicMetadataImpl.class.getSimpleName() + "[", "]")
        .add("title='" + title + "'")
        .add("sourceId='" + sourceId + "'")
        .add("description=" + description)
        .add("homepageUrl='" + homepageUrl + "'")
        .add("logoUrl='" + logoUrl + "'")
        .add("subject='" + subject + "'")
        .add("rights='" + rights + "'")
        .add("citation='" + citation + "'")
        .add("creatorName='" + creatorName + "'")
        .add("creatorEmail='" + creatorEmail + "'")
        .add("publisherName='" + publisherName + "'")
        .add("publisherEmail='" + publisherEmail + "'")
        .add("published=" + published)
        .add("additionalMetadata=" + additionalMetadata)
        .toString();
  }
}
