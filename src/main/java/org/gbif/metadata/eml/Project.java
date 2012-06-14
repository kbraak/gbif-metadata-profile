/*
 * Copyright 2009 GBIF.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gbif.metadata.eml;

import java.io.Serializable;

import com.google.common.base.Objects;

/**
 * A class encapsulating the project information
 */
public class Project implements Serializable {

  /**
   * Generated
   */
  private static final long serialVersionUID = 2224956553560612242L;

  /**
   * A descriptive title for the research project.
   *
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-project.html#title">EML Project title
   *      keyword</a>
   */
  private String title;

  /**
   * The Personnel field extends ResponsibleParty with role information and is used to document people involved in a
   * research project by providing contact information and their role in the project.
   *
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-project.html#personnel">EML Project
   *      personnel
   *      keyword</a>
   */
  private Agent personnel = new Agent();

  /**
   * The funding field is used to provide information about funding sources for the project such as: grant and contract
   * numbers; names and addresses of funding sources.
   *
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-project.html#funding">EML Project funding
   *      keyword</a>
   */
  private String funding;

  /**
   * The studyAreaDescription field documents the physical area associated with the research project. It can include
   * descriptions of the geographic, temporal, and taxonomic coverage of the research location.
   *
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-project.html#descriptor">EML Project
   *      descriptor keyword</a>
   */
  private StudyAreaDescription studyAreaDescription = new StudyAreaDescription();

  /**
   * A general description in textual form describing some aspect of the study area
   *
   * @see <a href="http://knb.ecoinformatics.org/software/eml/eml-2.1.0/eml-project.html#designDescription">EML Project
   *      designDescription keyword</a>
   */
  private String designDescription;

  /**
   * Required by Struts2
   */
  public Project() {
    personnel.setRole("ResponsibleParty");
  }

  /**
   * @return the designDescription
   */
  public String getDesignDescription() {
    return designDescription;
  }

  /**
   * @param designDescription the designDescription to set
   */
  public void setDesignDescription(String designDescription) {
    this.designDescription = designDescription;
  }

  /**
   * @return the funding
   */
  public String getFunding() {
    return funding;
  }

  /**
   * @param funding the funding to set
   */
  public void setFunding(String funding) {
    this.funding = funding;
  }

  /**
   * @return the personnel
   */
  public Agent getPersonnel() {
    return personnel;
  }

  /**
   * @param personnel the personnel to set
   */
  public void setPersonnel(Agent personnel) {
    this.personnel = personnel;
  }

  /**
   * @return the studyAreaDescription
   */
  public StudyAreaDescription getStudyAreaDescription() {
    return studyAreaDescription;
  }

  /**
   * @param studyAreaDescription the studyAreaDescription to set
   */
  public void setStudyAreaDescription(StudyAreaDescription studyAreaDescription) {
    this.studyAreaDescription = studyAreaDescription;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Project other = (Project) obj;
    return Objects.equal(this.title, other.title) && Objects.equal(this.personnel, other.personnel) && Objects
      .equal(this.funding, other.funding) && Objects.equal(this.studyAreaDescription, other.studyAreaDescription)
           && Objects.equal(this.designDescription, other.designDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(title, personnel, funding, studyAreaDescription, designDescription);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).
      add("title", title).
      add("personnel", personnel).
      add("funding", funding).
      add("studyAreaDescription", studyAreaDescription).
      add("designDescription", designDescription).
      toString();
  }

}
