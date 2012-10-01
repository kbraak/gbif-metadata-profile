package org.gbif.metadata;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

public class BasicMetadataImpl implements Serializable, BasicMetadata {

  private static final long serialVersionUID = 12073642352837495L;
  private static final Joiner SUBJECT_JOINER = Joiner.on("; ").useForNull("");

  private String title;
  private String sourceId;
  private String description;
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
  private Map<String, String> additionalMetadata = new HashMap<String, String>();

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
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
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
    if (Strings.isNullOrEmpty(subject)) {
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
    subject = SUBJECT_JOINER.join(keywords);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final BasicMetadataImpl other = (BasicMetadataImpl) obj;
    return Objects.equal(this.title, other.title) && Objects.equal(this.sourceId, other.sourceId) && Objects
      .equal(this.description, other.description) && Objects.equal(this.homepageUrl, other.homepageUrl) && Objects
      .equal(this.logoUrl, other.logoUrl) && Objects.equal(this.subject, other.subject) && Objects
      .equal(this.rights, other.rights) && Objects.equal(this.citation, other.citation) && Objects
      .equal(this.creatorName, other.creatorName) && Objects.equal(this.creatorEmail, other.creatorEmail) && Objects
      .equal(this.publisherName, other.publisherName) && Objects.equal(this.publisherEmail, other.publisherEmail)
           && Objects.equal(this.published, other.published) && Objects
      .equal(this.additionalMetadata, other.additionalMetadata);
  }

  @Override
  public int hashCode() {
    return Objects
      .hashCode(title, sourceId, description, homepageUrl, logoUrl, subject, rights, citation, creatorName, creatorEmail,
        publisherName, publisherEmail, published, additionalMetadata);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).
      add("title", title).
      add("sourceId", sourceId).
      add("description", description).
      add("homepageUrl", homepageUrl).
      add("logoUrl", logoUrl).
      add("subject", subject).
      add("rights", rights).
      add("citation", citation).
      add("creatorName", creatorName).
      add("creatorEmail", creatorEmail).
      add("publisherName", publisherName).
      add("publisherEmail", publisherEmail).
      add("published", published).
      add("additionalMetadata", additionalMetadata).
      toString();
  }

}
