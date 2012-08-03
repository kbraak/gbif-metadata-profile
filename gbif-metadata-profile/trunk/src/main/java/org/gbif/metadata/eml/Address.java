package org.gbif.metadata.eml;

import java.io.Serializable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import static com.google.common.base.Objects.equal;

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
    return Strings.nullToEmpty(address).trim().isEmpty() && Strings.nullToEmpty(city).trim().isEmpty() && Strings
      .nullToEmpty(province).trim().isEmpty() && Strings.nullToEmpty(postalCode).trim().isEmpty() && Strings
      .nullToEmpty(country).trim().isEmpty();
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Address)) {
      return false;
    }
    Address o = (Address) other;
    return equal(address, o.address) && equal(city, o.city) && equal(province, o.province) &&
           equal(country, o.country) && equal(postalCode, o.postalCode);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(address, city, province, country, postalCode);
  }

  /**
   * Returns a multi-line String with key=value pairs.
   *
   * @return a String representation of this class.
   */
  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("country", country).add("address", address).add("province", province)
      .add("postalCode", postalCode).add("city", city).toString();
  }

  /**
   * Construct a formatted address string, from the deliveryPoint, city, province, postal code, and country.
   * The format used is: delivery point, city, province country, postal code
   *
   * @return formatted address
   */
  public String toFormattedString() {
    String formattedAddress = null;
    if (!Strings.isNullOrEmpty(address)) {
      formattedAddress = address;
    }
    if (!Strings.isNullOrEmpty(city)) {
      if (!Strings.isNullOrEmpty(formattedAddress)) {
        formattedAddress = formattedAddress + ", " + city;
      } else {
        formattedAddress = city;
      }

    }
    if (!Strings.isNullOrEmpty(province)) {
      if (!Strings.isNullOrEmpty(formattedAddress)) {
        formattedAddress = formattedAddress + ", " + province;
      } else {
        formattedAddress = province;
      }

    }
    if (!Strings.isNullOrEmpty(country)) {
      if (!Strings.isNullOrEmpty(formattedAddress)) {
        formattedAddress = formattedAddress + " " + country;
      } else {
        formattedAddress = country;
      }

    }
    if (!Strings.isNullOrEmpty(postalCode)) {
      if (!Strings.isNullOrEmpty(formattedAddress)) {
        formattedAddress = formattedAddress + ", " + postalCode;
      } else {
        formattedAddress = postalCode;
      }
    }

    return formattedAddress;
  }

}
