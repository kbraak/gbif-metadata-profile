/*
 * Copyright 2020 Global Biodiversity Information Facility (GBIF)
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
package org.gbif.metadata.common.util;

import org.gbif.api.vocabulary.MetadataType;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import static org.gbif.api.vocabulary.MetadataType.EML;

public final class MetadataUtils {

  private static final Logger LOG = LoggerFactory.getLogger(MetadataUtils.class);

  private MetadataUtils() {}

  /**
   * @return the detected parser type or null
   * @throws java.lang.IllegalArgumentException in case no parser exists for this document
   */
  public static MetadataType detectParserType(InputStream xml) {
    try {
      XMLReader xmlReader = XMLReaderFactory.createXMLReader();
      ParserDetectionHandler handler = new ParserDetectionHandler();
      xmlReader.setContentHandler(handler);
      InputSource inputSource = new InputSource(xml);
      xmlReader.parse(inputSource);
      if (handler.parserType != null) {
        return handler.parserType;
      }
    } catch (SAXException e) {
      LOG.error("Failed to SAX parse a document for parser type detection", e);
    } catch (IOException e) {
      LOG.warn("Failed to read metadata document for parser type detection", e);
    }
    throw new IllegalArgumentException(
        "No parser found for this metadata document. Only EML or DC supported");
  }

  private static class ParserDetectionHandler extends DefaultHandler {
    private static final String DC_NAMESPACE = "http://purl.org/dc/terms/";
    private MetadataType parserType;
    private final LinkedList<String> path = new LinkedList<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException {
      // look for EML
      if (path.size() == 1 && path.get(0).equals("eml") && localName.equals("dataset")) {
        parserType = EML;
      }

      // look for DC title
      if (parserType == null && DC_NAMESPACE.equals(uri)) {
        parserType = MetadataType.DC;
      }

      path.add(localName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
      String last = path.removeLast();
      if (!last.equals(localName)) {
        LOG.warn("XML path broken. Got {} but path stack gave {}", localName, last);
      }
    }
  }
}
