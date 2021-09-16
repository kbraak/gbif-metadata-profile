/*
 * Copyright 2021 Global Biodiversity Information Facility (GBIF)
 *
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

import java.io.Serializable;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

/**
 * This class is used to represent an address with address, city, province and postal-code information.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class Address implements Serializable {

  private static final long serialVersionUID = 3617859655330969141L;
  private String address;
  private String city;
  private String province;
  private String country;
  private String postalCode;

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    if (city == null || city.isEmpty()) {
      return null;
    }
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    if (country == null || country.isEmpty()) {
      return null;
    }
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPostalCode() {
    if (postalCode == null || postalCode.isEmpty()) {
      return null;
    }
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getProvince() {
    if (province == null || province.isEmpty()) {
      return null;
    }
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public boolean isEmpty() {
    return StringUtils.isAllBlank(address, city, province, postalCode, country);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Address address1 = (Address) o;
    return java.util.Objects.equals(address, address1.address)
        && java.util.Objects.equals(city, address1.city)
        && java.util.Objects.equals(province, address1.province)
        && java.util.Objects.equals(country, address1.country)
        && java.util.Objects.equals(postalCode, address1.postalCode);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(address, city, province, country, postalCode);
  }

  /**
   * Returns a multi-line String with key=value pairs.
   *
   * @return a String representation of this class.
   */
  @Override
  public String toString() {
    return new StringJoiner(", ", Address.class.getSimpleName() + "[", "]")
        .add("address='" + address + "'")
        .add("city='" + city + "'")
        .add("province='" + province + "'")
        .add("country='" + country + "'")
        .add("postalCode='" + postalCode + "'")
        .toString();
  }

  /**
   * Construct a formatted address string, from the deliveryPoint, city, province, postal code, and country.
   * The format used is: delivery point, city, province country, postal code
   *
   * @return formatted address
   */
  public String toFormattedString() {
    String formattedAddress = null;
    if (StringUtils.isNotBlank(address)) {
      formattedAddress = address;
    }
    if (StringUtils.isNotBlank(city)) {
      if (StringUtils.isNotBlank(formattedAddress)) {
        formattedAddress = formattedAddress + ", " + city;
      } else {
        formattedAddress = city;
      }

    }
    if (StringUtils.isNotBlank(province)) {
      if (StringUtils.isNotBlank(formattedAddress)) {
        formattedAddress = formattedAddress + ", " + province;
      } else {
        formattedAddress = province;
      }

    }
    if (StringUtils.isNotBlank(country)) {
      if (StringUtils.isNotBlank(formattedAddress)) {
        formattedAddress = formattedAddress + " " + country;
      } else {
        formattedAddress = country;
      }

    }
    if (StringUtils.isNotBlank(postalCode)) {
      if (StringUtils.isNotBlank(formattedAddress)) {
        formattedAddress = formattedAddress + ", " + postalCode;
      } else {
        formattedAddress = postalCode;
      }
    }

    return formattedAddress;
  }

}
