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

import org.gbif.utils.PreconditionUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * This class can be used to encapsulate generic attribute information. Each attribute has a category, a name, and a
 * type within the context of a {@link LocaleBundle}.
 * Note that this class is immutable. New instances can be created using the create method.
 */
public class Attribute implements Serializable {

  private static final long serialVersionUID = 8805087340650428951L;

  private final String category;
  private final LocaleBundle localeBundle;
  private final String name;
  private final String value;

  /**
   * Creates a new Attribute instance. Throws {@link NullPointerException} if any of the arguments are null. Throws
   * {@link IllegalArgumentException} if category, name, or value arguments are the empty string.
   *
   * @param category     the category
   * @param localeBundle the locale bundle
   * @param name         the name
   * @param value        the value
   *
   * @return new instance of Attribute
   */
  public static Attribute create(String category, LocaleBundle localeBundle, String name, String value) {
    Objects.requireNonNull(category, "Category was null");
    PreconditionUtils.checkArgument(category.trim().length() != 0, "Category was empty");
    Objects.requireNonNull(localeBundle, "LocaleBundle was null");
    Objects.requireNonNull(name, "Name was null");
    PreconditionUtils.checkArgument(name.trim().length() != 0, "Name was empty");
    Objects.requireNonNull(value, "Value was null");
    PreconditionUtils.checkArgument(value.trim().length() != 0, "Value was empty");
    return new Attribute(category, localeBundle, name, value);
  }

  private Attribute(String category, LocaleBundle localeBundle, String name, String value) {
    this.category = category;
    this.localeBundle = localeBundle;
    this.name = name;
    this.value = value;
  }

  public String getCategory() {
    return category;
  }

  public LocaleBundle getLocaleBundle() {
    return localeBundle;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Attribute attribute = (Attribute) o;
    return Objects.equals(category, attribute.category)
        && Objects.equals(localeBundle, attribute.localeBundle)
        && Objects.equals(name, attribute.name)
        && Objects.equals(value, attribute.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(category, localeBundle, name, value);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Attribute.class.getSimpleName() + "[", "]")
        .add("category='" + category + "'")
        .add("localeBundle=" + localeBundle)
        .add("name='" + name + "'")
        .add("value='" + value + "'")
        .toString();
  }
}
