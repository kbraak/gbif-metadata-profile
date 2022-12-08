# gbif-metadata-profile

The gbif-metadata-profile library provides model and serialization utilities for the GBIF metadata profiles:
 
 * [Ecological Metadata Language (EML)](https://eml.ecoinformatics.org/)

 * [Open Archive Initiative Darwin Core (OAI_DC)](https://www.openarchives.org/OAI/2.0/oai_dc.xsd)

## To build the project
```
mvn clean install
```

## Policies
 * This project used to be used exclusively by the [IPT](https://github.com/gbif/ipt), now this code is in the [ipt package](gbif-metadata-profile-eml/src/main/java/org/gbif/metadata/eml/ipt).
 * This project contains functionality moved from the [registry-metadata](https://github.com/gbif/registry/tree/master/registry-metadata) library
