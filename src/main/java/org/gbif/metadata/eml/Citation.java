package org.gbif.metadata.eml;

import java.io.Serializable;

import com.google.common.base.Objects;

/**
 * A single literature citation with an optional identifier TODO Documentation
 */
public class Citation implements Serializable {

  /**
   * Generated
   */
  private static final long serialVersionUID = 8611377167438888243L;

  private String identifier;
  private String citation;

  /**
   * Default constructor required by Struts2
   */
  public Citation() {
  }

  public Citation(String citation, String identifier) {
    this.citation = citation;
    this.identifier = identifier;
  }

  /**
   * @return the name
   */
  public String getCitation() {
    return citation;
  }

  public void setCitation(String citation) {
    this.citation = citation;
  }

  /**
   * @return the identifier
   */
  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Citation other = (Citation) obj;
    return Objects.equal(this.identifier, other.identifier) && Objects.equal(this.citation, other.citation);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(identifier, citation);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).
      add("identifier", identifier).
      add("citation", citation).
      toString();
  }

}
