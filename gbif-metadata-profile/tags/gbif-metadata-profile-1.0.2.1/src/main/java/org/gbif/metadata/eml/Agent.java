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

import java.io.Serializable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * Encapsulates all the information for an Agent
 */
public class Agent implements Serializable {

  private static final long serialVersionUID = 7028536657833651816L;

  private String firstName;
  private String lastName;
  private String organisation;
  private String position;
  private Address address = new Address();
  private String phone;
  private String email;
  private String role;
  private String homepage;

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getEmail() {
    if (email == null || email.isEmpty()) {
      return null;
    }
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public String getHomepage() {
    if (homepage == null || homepage.isEmpty()) {
      return null;
    }
    return homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
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

  public String getPhone() {
    if (phone == null || phone.isEmpty()) {
      return null;
    }
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
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
    return Strings.emptyToNull(name.trim());
  }

  public boolean isEmpty() {
    return Strings.nullToEmpty(firstName).trim().isEmpty() && Strings.nullToEmpty(lastName).trim().isEmpty() &&
           Strings.nullToEmpty(organisation).trim().isEmpty() && Strings.nullToEmpty(position).trim().isEmpty() &&
           address.isEmpty() && Strings.nullToEmpty(phone).trim().isEmpty() &&
           Strings.nullToEmpty(email).trim().isEmpty() && Strings.nullToEmpty(role).trim().isEmpty() &&
           Strings.nullToEmpty(homepage).trim().isEmpty();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Agent other = (Agent) obj;
    return Objects.equal(this.firstName, other.firstName) && Objects.equal(this.lastName, other.lastName) && Objects
      .equal(this.organisation, other.organisation) && Objects.equal(this.position, other.position) && Objects
      .equal(this.address, other.address) && Objects.equal(this.phone, other.phone) && Objects
      .equal(this.email, other.email) && Objects.equal(this.role, other.role) && Objects
      .equal(this.homepage, other.homepage);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(firstName, lastName, organisation, position, address, phone, email, role, homepage);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).
      add("firstName", firstName).
      add("lastName", lastName).
      add("organisation", organisation).
      add("position", position).
      add("address", address).
      add("phone", phone).
      add("email", email).
      add("role", role).
      add("homepage", homepage).
      toString();
  }

}
