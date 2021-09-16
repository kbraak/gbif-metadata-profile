package org.gbif.metadata;

import org.gbif.metadata.eml.ValidatorFactory;
import org.gbif.utils.file.FileUtils;

import java.io.File;
import java.net.MalformedURLException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * For validation of EML files see also http://knb.ecoinformatics.org/emlparser/.
 */
public class ValidateEmlSampleTest {

  @Test
  public void validateSamples() throws MalformedURLException, SAXException {
    // eml file to validate
    File sample1 = FileUtils.getClasspathFile("eml/sample.xml");
    File sample2 = FileUtils.getClasspathFile("eml/sample2.xml");


    Validator validator = ValidatorFactory.getGbifValidator();
    try {
      validator.validate(new StreamSource(sample1));
      System.out.println(sample1.getName() + " validates successfully with GBIF EML profile");
    } catch (Exception e) {
      fail(sample1.getName() + " validation ERROR with GBIF EML profile");
    }
    try {
      validator.validate(new StreamSource(sample2));
      System.out.println(sample2.getName() + " validates successfully with GBIF EML profile");
    } catch (Exception e) {
      fail(sample2.getName() + " validation ERROR with GBIF EML profile");
    }

    validator = ValidatorFactory.getEmlValidator();
    try {
      validator.validate(new StreamSource(sample1));
      System.out.println(sample1.getName() + " validates successfully with EML schema");
    } catch (Exception e) {
      fail(sample1.getName() + " validation ERROR with EML schema");
    }
    try {
      validator.validate(new StreamSource(sample2));
      System.out.println(sample2.getName() + " validates successfully with EML schema");
    } catch (Exception e) {
      fail(sample2.getName() + " validation ERROR with EML schema");
    }
  }
}
