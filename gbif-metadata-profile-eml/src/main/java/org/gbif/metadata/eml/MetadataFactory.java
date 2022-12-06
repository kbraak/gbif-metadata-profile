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
package org.gbif.metadata.eml;

import org.gbif.metadata.eml.handler.BasicMetadataSaxHandler;
import org.gbif.metadata.eml.handler.DcHandler;
import org.gbif.metadata.eml.handler.EmlHandler;
import org.gbif.utils.file.BomSafeInputStreamWrapper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataFactory {

  private final Logger log = LoggerFactory.getLogger(MetadataFactory.class);
  private final SAXParserFactory saxFactory;
  private final List<BasicMetadataSaxHandler> handler = new ArrayList<>();

  {
    handler.add(new EmlHandler());
    handler.add(new DcHandler());
  }

  public MetadataFactory() {
    saxFactory = SAXParserFactory.newInstance();
    saxFactory.setNamespaceAware(true);
    saxFactory.setValidating(false);
  }

  /**
   * @return a suitable reader or null if none can be found
   */
  private boolean hasContent(BasicMetadataImpl bm) {
    return bm != null
        && (bm.getTitle() != null
            || !bm.getDescription().isEmpty()
            || bm.getSubject() != null
            || bm.getSourceId() != null
            || bm.getHomepageUrl() != null
            || bm.getPublished() != null);
  }

  public BasicMetadataImpl read(File metadataFile) throws MetadataException {
    try {
      return read(new FileInputStream(metadataFile));
    } catch (FileNotFoundException e) {
      throw new MetadataException("FileNotFound", e);
    }
  }

  public BasicMetadataImpl read(InputStream stream) throws MetadataException {
    // in order to test different handlers we need to process the same stream several times
    // we therefore read the entire stream into memory first
    try {
      byte[] data = IOUtils.toByteArray(stream);
      // find handler by testing one by one
      for (BasicMetadataSaxHandler h : handler) {
        try {
          InputStream in = new ByteArrayInputStream(data);
          BasicMetadataImpl bm = read(in, h);
          if (hasContent(bm)) {
            // works!
            log.debug("Using " + h.toString() + " for parsing metadata");
            return bm;
          }
        } catch (MetadataException ignored) {
          // just try another one
        }
      }
    } catch (IOException e1) {
      throw new MetadataException("Can't read input stream", e1);
    }
    throw new MetadataException("Can't find suitable metadata parser");
  }

  public BasicMetadataImpl read(InputStream stream, BasicMetadataSaxHandler handler)
      throws MetadataException {
    try {
      SAXParser p = saxFactory.newSAXParser();
      p.parse(new BomSafeInputStreamWrapper(stream), handler);
      BasicMetadataImpl bm = handler.yield();
      if (hasContent(bm)) {
        return bm;
      }
    } catch (Exception e) {
      log.error("Error parsing metadata document: " + e.getMessage());
    }
    return null;
  }
}
