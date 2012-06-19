package org.gbif.metadata.handler;

import org.gbif.metadata.DateUtils;
import org.gbif.utils.text.EmailUtils;
import org.gbif.utils.text.EmailUtils.EmailWithName;

import java.util.Date;

import com.google.common.base.Strings;
import org.xml.sax.SAXException;

public class DcHandler extends BasicMetadataSaxHandler {

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    // calling the super method to stringify the character buffer
    super.endElement(uri, localName, qName);

    // dcPrefix
    if (uri != null && (uri.equalsIgnoreCase(NS_DC) || uri.equalsIgnoreCase(NS_DCTERMS))) {
      if (localName.equalsIgnoreCase("title")) {
        bm.setTitle(content);

      } else if (localName.equalsIgnoreCase("description")
                 || localName.equalsIgnoreCase("abstract") && bm.getDescription() == null) {
        bm.setDescription(content);
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
        String creator = Strings.emptyToNull(content.trim());
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
}
