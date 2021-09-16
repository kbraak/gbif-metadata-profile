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
package org.gbif.metadata.handler;

import org.gbif.metadata.DateUtils;
import org.gbif.utils.text.EmailUtils;
import org.gbif.utils.text.EmailUtils.EmailWithName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.SAXException;

public class DcHandler extends BasicMetadataSaxHandler {

  List<String> description;

  @Override
  public void endDocument() throws SAXException {
    super.endDocument();
    bm.setDescription(description);
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    // calling the super method to stringify the character buffer
    super.endElement(uri, localName, qName);

    // dcPrefix
    if (uri != null && (uri.equalsIgnoreCase(NS_DC) || uri.equalsIgnoreCase(NS_DCTERMS))) {
      if (localName.equalsIgnoreCase("title")) {
        bm.setTitle(content);

      } else if (localName.equalsIgnoreCase("description") || localName.equalsIgnoreCase("abstract")) {
        // split description into paragraphs
        List<String> paragraphs = Arrays.stream(content.split("\r?\n"))
            .filter(StringUtils::isNotBlank)
            .map(String::trim)
            .collect(Collectors.toList());
        description.addAll(paragraphs);
      } else if (localName.equalsIgnoreCase("subject") || localName.equalsIgnoreCase("coverage") || localName
        .equalsIgnoreCase("spatial") || localName.equalsIgnoreCase("temporal")) {
        bm.addSubject(content);
      } else if (localName.equalsIgnoreCase("created")) {
        Date published = DateUtils.parse(content, DateUtils.ISO_DATE_FORMAT);
        bm.setPublished(published);

      } else if (localName.equalsIgnoreCase("relation")) {
        if (bm.getHomepageUrl() == null) {
          bm.setHomepageUrl(content);
        }
      } else if (localName.equalsIgnoreCase("identifier")) {
        bm.setSourceId(content);

      } else if (localName.equalsIgnoreCase("rights")) {
        bm.setRights(content);

      } else if (localName.equalsIgnoreCase("bibliographicCitation")) {
        bm.setCitationString(content);

      } else if (localName.equalsIgnoreCase("creator") || localName.equalsIgnoreCase("publisher")) {
        // try to parse our email and name
        String creator = StringUtils.trimToNull(content);
        if (creator != null) {
          EmailWithName n = EmailUtils.parseEmail(creator);
          bm.setCreatorEmail(n.email);
          bm.setCreatorName(n.name);
        }

      } else if (localName.equalsIgnoreCase("source")) {
        bm.setHomepageUrl(content);

      }
    } else if (uri == null && localName.equalsIgnoreCase("onlineurl") || uri == null && localName
      .equalsIgnoreCase("homepage")) {
      bm.setHomepageUrl(content);
    }
  }

  @Override
  public void startDocument() {
    super.startDocument();
    description = new ArrayList<>();
  }
}
