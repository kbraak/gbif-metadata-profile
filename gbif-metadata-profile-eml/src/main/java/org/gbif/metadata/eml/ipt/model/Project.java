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
package org.gbif.metadata.eml.ipt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A class encapsulating the project information
 */
public class Project implements Serializable {

  private static final long serialVersionUID = 2224956553560612242L;

  /**
   * A descriptive title for the research project.
   *
   * @see <a href="https://eml.ecoinformatics.org/schema/eml-project_xsd.html#ResearchProjectType_title">EML Project title
   *      keyword</a>
   */
  private String title;

  /**
   * A unique identifier for the research project.
   * </br>
   * This can be used to link multiple dataset/EML document instances that are associated in some way with the same
   * project, e.g. a monitoring series. The nature of the association can be described in the Project description.
   */
  private String identifier;

  /**
   * Summary about the research project.
   */
  private String description;

  /**
   * The Personnel field extends ResponsibleParty with role information and is used to document people involved in a
   * research project by providing contact information and their role in the project.
   *
   * @see <a href="https://eml.ecoinformatics.org/schema/eml-project_xsd.html#ResearchProjectType_personnel">EML Project
   *      personnel
   *      keyword</a>
   */
  private List<Agent> personnel = new ArrayList<>();

  /**
   * The funding field is used to provide information about funding sources for the project such as: grant and contract
   * numbers; names and addresses of funding sources.
   *
   * @see <a href="https://eml.ecoinformatics.org/schema/eml-project_xsd.html#ResearchProjectType_funding">EML Project funding
   *      keyword</a>
   */
  private String funding;

  /**
   * The studyAreaDescription field documents the physical area associated with the research project. It can include
   * descriptions of the geographic, temporal, and taxonomic coverage of the research location.
   *
   * @see <a href="https://eml.ecoinformatics.org/schema/eml-project_xsd.html#ResearchProjectType_ResearchProjectType_studyAreaDescription_descriptor">EML Project
   *      descriptor keyword</a>
   */
  private StudyAreaDescription studyAreaDescription = new StudyAreaDescription();

  /**
   * A general textual description of research design. It can include detailed accounts of goals, motivations, theory,
   * hypotheses, strategy, statistical design, and actual work.
   *
   * @see <a href="https://eml.ecoinformatics.org/schema/eml-project_xsd.html#ResearchProjectType_designDescription">EML Project
   *      designDescription keyword</a>
   */
  private String designDescription;

  /**
   * Required by Struts2
   */
  public Project() {
    for (Agent agent : personnel) {
      agent.setRole("pointOfContact");
    }
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
  public List<Agent> getPersonnel() {
    return personnel;
  }

  /**
   * @param personnel the personnel to set
   */
  public void setPersonnel(List<Agent> personnel) {
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
   * @return project identifier
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * @param identifier the identifier of the project to set
   */
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  /**
   * @return project description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description of the project to set
   */
  public void setDescription(String description) {
    this.description = description;
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

  /**
   * Adds an Agent to the project personnel list. This method was introduced to ease the Digester rules for
   * parsing of EML.
   *
   * @param agent to add
   */
  public void addProjectPersonnel(Agent agent) {
    if (agent.getRole() == null) {
      agent.setRole("pointOfContact");
    }
    getPersonnel().add(agent);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Project project = (Project) o;
    return Objects.equals(title, project.title)
        && Objects.equals(identifier, project.identifier)
        && Objects.equals(description, project.description)
        && Objects.equals(personnel, project.personnel)
        && Objects.equals(funding, project.funding)
        && Objects.equals(studyAreaDescription, project.studyAreaDescription)
        && Objects.equals(designDescription, project.designDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        title,
        identifier,
        description,
        personnel,
        funding,
        studyAreaDescription,
        designDescription);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Project.class.getSimpleName() + "[", "]")
        .add("title='" + title + "'")
        .add("identifier='" + identifier + "'")
        .add("description='" + description + "'")
        .add("personnel=" + personnel)
        .add("funding='" + funding + "'")
        .add("studyAreaDescription=" + studyAreaDescription)
        .add("designDescription='" + designDescription + "'")
        .toString();
  }
}
