package org.gbif.metadata.handler;

import org.gbif.metadata.DateUtils;

import java.util.Date;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.xml.sax.SAXException;

/**
 * An EML sax handler building a BasicMetadata instance.
 * Ths handler requires a namespace aware parser, but internally ignores the namespace to allow parsing of all kind of
 * EML documents regardless their version!
 */
public class EmlHandler extends BasicMetadataSaxHandler {

  private List<String> keywords;
  private List<String> description;

  @Override
  public void endDocument() throws SAXException {
    super.endDocument();
    bm.setSubject(Strings.emptyToNull(Joiner.on("; ").useForNull("").join(keywords).trim()));
    bm.setDescription(description);
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    // calling the super method to stringify the character buffer
    super.endElement(uri, localName, qName);

    if (content != null) {
      if (parents.startsWith("/eml/additionalmetadata/metadata")) {
        bm.addAdditionalMetadata(localName, content);
      } else if (parents.startsWith("/eml/dataset/keywordset")) {
        // dataset/keywordSet/keyword
        if (localName.equalsIgnoreCase("keyword")) {
          keywords.add(content);
        }
      } else if (parents.startsWith("/eml/dataset/abstract")) {
        // dataset/abstract/para
        if (localName.equalsIgnoreCase("para")) {
          if (!Strings.isNullOrEmpty(content)) {
            description.add(content.trim());
          }
        }
      } else if (parents.startsWith("/eml/dataset/creator")) {
        // dataset/creator/onlineUrl
        if (localName.equalsIgnoreCase("onlineUrl")) {
          // only use this url if no distribution url exists
          if (bm.getHomepageUrl() == null) {
            bm.setHomepageUrl(content);
          }
        }
      } else if (parents.startsWith("/eml/dataset/distribution/online")) {
        // dataset/distribution/online/url
        if (localName.equalsIgnoreCase("url")) {
          bm.setHomepageUrl(content);
        }
      } else if (parents.startsWith("/eml/dataset")) {
        if (localName.equalsIgnoreCase("title")) {
          bm.setTitle(content);
        } else if (localName.equalsIgnoreCase("alternateIdentifier")) {
          bm.setSourceId(content);
        } else if (localName.equalsIgnoreCase("pubDate")) {
          Date published = DateUtils.parse(content, DateUtils.ISO_DATE_FORMAT);
          bm.setPublished(published);
        }
      }
    }
  }

  @Override
  public void startDocument() {
    super.startDocument();
    keywords = Lists.newArrayList();
    description = Lists.newArrayList();
  }
}
