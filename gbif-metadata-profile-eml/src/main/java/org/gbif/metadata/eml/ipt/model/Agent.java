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

import org.apache.commons.lang3.StringUtils;

/**
 * Encapsulates all the information for an Agent.
 */
public class Agent implements Serializable {

  private static final long serialVersionUID = 7028536657833651816L;

  private String firstName;
  private String lastName;
  private String organisation;
  private String position;
  // TODO: rename fields, phone -> phones etc. ???
  // TODO: use lombok where possible?
  private Address address = new Address();
  private List<String> phone = new ArrayList<>();
  private List<String> email = new ArrayList<>();
  private String role;
  private List<String> homepage = new ArrayList<>();
  private List<UserId> userIds = new ArrayList<>();

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public List<String> getEmail() {
    return email;
  }

  public void setEmail(List<String> email) {
    this.email = email;
  }

  public void addEmail(String email) {
    this.email.add(email);
  }

  public String getFirstName() {
    if (firstName == null || firstName.isEmpty()) {
      return null;
    }
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public List<String> getHomepage() {
    return homepage;
  }

  public void setHomepage(List<String> homepage) {
    this.homepage = homepage;
  }

  public void addHomepage(String homepage) {
    this.homepage.add(homepage);
  }

  public String getLastName() {
    if (lastName == null || lastName.isEmpty()) {
      return null;
    }
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getOrganisation() {
    if (organisation == null || organisation.isEmpty()) {
      return null;
    }
    return organisation;
  }

  public void setOrganisation(String organisation) {
    this.organisation = organisation;
  }

  public List<String> getPhone() {
    return phone;
  }

  public void setPhone(List<String> phone) {
    this.phone = phone;
  }

  public void addPhone(String phone) {
    this.phone.add(phone);
  }

  public String getPosition() {
    if (position == null || position.isEmpty()) {
      return null;
    }
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public List<UserId> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<UserId> userIds) {
    this.userIds = userIds;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getFullName() {
    String name = "";
    if (firstName != null && !firstName.isEmpty()) {
      name += firstName;
    }
    if (lastName != null && !lastName.isEmpty()) {
      name += " " + lastName;
    }
    return StringUtils.trimToNull(name);
  }

  /**
   * Utility to add a userId to the list of userIds. This method was introduced to ease the Digester rules for
   * parsing of EML.
   * @param userId to add
   */
  public void addUserId(UserId userId) {
    userIds.add(userId);
  }

  public boolean isEmpty() {
    return StringUtils.isAllBlank(
            firstName, lastName, organisation, position, role)
        && phone.isEmpty()
        && email.isEmpty()
        && homepage.isEmpty()
        && address.isEmpty()
        && userIds.isEmpty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Agent agent = (Agent) o;
    return Objects.equals(firstName, agent.firstName)
        && Objects.equals(lastName, agent.lastName)
        && Objects.equals(organisation, agent.organisation)
        && Objects.equals(position, agent.position)
        && Objects.equals(address, agent.address)
        && Objects.equals(phone, agent.phone)
        && Objects.equals(email, agent.email)
        && Objects.equals(role, agent.role)
        && Objects.equals(homepage, agent.homepage)
        && Objects.equals(userIds, agent.userIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        firstName,
        lastName,
        organisation,
        position,
        address,
        phone,
        email,
        role,
        homepage,
        userIds);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Agent.class.getSimpleName() + "[", "]")
        .add("firstName='" + firstName + "'")
        .add("lastName='" + lastName + "'")
        .add("organisation='" + organisation + "'")
        .add("position='" + position + "'")
        .add("address=" + address)
        .add("phone='" + phone + "'")
        .add("email='" + email + "'")
        .add("role='" + role + "'")
        .add("homepage='" + homepage + "'")
        .add("userIds=" + userIds)
        .toString();
  }
}
