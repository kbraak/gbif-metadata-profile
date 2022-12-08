<#escape x as x?xml>
    <#macro agentBlock agent withRole=false>
        <#if (agent.getLastName())?? || ((!(agent.getOrganisation())??) && (!(agent.getPosition())??))>
            <individualName>
                <#if (agent.getFirstName())??>
                    <givenName>${agent.firstName}</givenName>
                </#if>
                <surName>${agent.lastName!}</surName>
            </individualName>
        </#if>
        <#if (agent.getOrganisation())??>
            <organizationName>${agent.organisation}</organizationName>
        </#if>
        <#if (agent.getPosition())??>
            <positionName>${agent.position}</positionName>
        </#if>
        <#assign adr=agent.getAddress()/>
        <#if (adr.getAddress())?has_content
        || (adr.getCity())??
        || (adr.getProvince())??
        || (adr.getProvince())??
        || (adr.getPostalCode())??
        || (adr.getCountry())?? >
            <address>
                <#if (adr.getAddress())?has_content >
                    <deliveryPoint>${adr.address}</deliveryPoint>
                </#if>
                <#if (adr.getCity())?? >
                    <city>${adr.city}</city>
                </#if>
                <#if (adr.getProvince())?? >
                    <administrativeArea>${adr.province}</administrativeArea>
                </#if>
                <#if (adr.getPostalCode())?? >
                    <postalCode>${adr.postalCode}</postalCode>
                </#if>
                <#if (adr.getCountry())?? >
                    <country>${adr.country}</country>
                </#if>
            </address>
        </#if>
        <#if (agent.getPhone())??>
            <phone>${agent.phone}</phone>
        </#if>
        <#if (agent.getEmail())??>
            <electronicMailAddress>${agent.email}</electronicMailAddress>
        </#if>
        <#if (agent.getHomepage())??>
            <onlineUrl>${agent.homepage}</onlineUrl>
        </#if>
        <#if (agent.userIds?size>0)>
            <#list agent.userIds as userId>
                <#if userId.identifier?has_content && userId.directory?has_content>
                    <userId directory="${userId.directory}">${userId.identifier}</userId>
                </#if>
            </#list>
        </#if>
        <#if withRole && (agent.getRole())??>
            <role>${agent.role!}</role>
        </#if>
    </#macro>
    <#macro xmlSchemaDateTime dt><#assign dt2=dt?datetime?string("yyyy-MM-dd'T'HH:mm:ss.SSSZ")/>${dt2?substring(0, dt2?length-2)}:${dt2?substring(dt2?length-2, dt2?length)}</#macro>
    <#assign DATEIsoFormat="yyyy-MM-dd"/>
    <eml:eml xmlns:eml="eml://ecoinformatics.org/eml-2.1.1"
             xmlns:dc="http://purl.org/dc/terms/"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="eml://ecoinformatics.org/eml-2.1.1 http://rs.gbif.org/schema/eml-gbif-profile/1.2/eml.xsd"
             packageId="${eml.packageId}" system="http://gbif.org" scope="system"<#if (eml.metadataLanguage)??>
    xml:lang="${eml.metadataLanguage!}"</#if>>

        <dataset>
            <#list eml.getAlternateIdentifiers() as altid>
                <alternateIdentifier>${altid!}</alternateIdentifier>
            </#list>
            <#if eml.title??>
                <title xml:lang="${eml.metadataLanguage!"en"}"><#if eml.title?has_content>${eml.title}<#else><@s.text name='eml.title'/></#if></title>
            </#if>
            <#-- The creator is the person, organization, or position who created the resource (not necessarily the author of this metadata about the resource). -->
            <#if (eml.creators?size>0) >
                <#list eml.creators as creator>
                    <creator>
                        <@agentBlock agent=creator />
                    </creator>
                </#list>
            </#if>
            <#-- The metadataProvider created documentation for the resource. -->
            <#if (eml.metadataProviders?size>0)>
                <#list eml.metadataProviders as metadataProvider>
                    <metadataProvider>
                        <@agentBlock agent=metadataProvider />
                    </metadataProvider>
                </#list>
            </#if>
            <#-- Any other party associated with the resource, along with their role. -->
            <#if (eml.associatedParties?size > 0)>
                <#list eml.associatedParties as associatedParty>
                    <associatedParty>
                        <@agentBlock agent=associatedParty withRole=true />
                    </associatedParty>
                </#list>
            </#if>
            <#-- The date on which the resource was published. -->
            <pubDate>
                <#if (eml.getPubDate()??)>
                    <#if (eml.getPubDate()?string("SSS"))=="001">${eml.pubDate?date?string("yyyy")}<#else>${eml.pubDate?date?string(DATEIsoFormat)}</#if>
                </#if>
            </pubDate>
            <language>${eml.language!"en"}</language>
            <#-- A description of the resource -->
            <#if (eml.abstract?size>0)>
                <abstract>
                    <#list eml.abstract as p>
                        <#if p?has_content>
                            <para>${p}</para>
                        </#if>
                    </#list>
                </abstract>
            </#if>
            <#-- Zero or more sets of keywords and an associated thesaurus for each. -->
            <#if (eml.keywords ? size > 0)>
                <#list eml.keywords as ks>
                    <#if (ks.keywordThesaurus)?has_content>
                        <keywordSet>
                            <#if (ks.keywords ? size > 0)>
                                <#list ks.keywords as k>
                                    <keyword>${k!""}</keyword>
                                </#list>
                            </#if>
                            <keywordThesaurus>${ks.keywordThesaurus!}</keywordThesaurus>
                        </keywordSet>
                    </#if>
                </#list>
            </#if>
            <#-- Any additional information about the resource not covered in any other element. -->
            <#if (eml.getAdditionalInfo())??>
                <additionalInfo>
                    <para>${eml.additionalInfo!}</para>
                </additionalInfo>
            </#if>
            <#-- A statement of the intellectual property rights associated with the resource. -->
            <#if (eml.getIntellectualRightsXml())??>
                <intellectualRights>
                    <para><#noescape>${eml.getIntellectualRightsXml()!}</#noescape></para>
                </intellectualRights>
            </#if>
            <#if (eml.getDistributionUrl())??>
                <distribution scope="document">
                    <online>
                        <url function="information">${eml.distributionUrl!}</url>
                    </online>
                </distribution>
            </#if>
            <#if ((eml.geospatialCoverages ? size > 0)
            ||  (eml.taxonomicCoverages ? size > 0)
            ||  (eml.temporalCoverages ? size > 0))>
                <coverage>
                    <#list eml.getGeospatialCoverages() as geocoverage>
                        <geographicCoverage>
                            <#if (geocoverage.getDescription())?has_content>
                                <geographicDescription>${geocoverage.description}</geographicDescription>
                            </#if>
                            <boundingCoordinates>
                                <westBoundingCoordinate>${geocoverage.boundingCoordinates.min.longitude!}</westBoundingCoordinate>
                                <eastBoundingCoordinate>${geocoverage.boundingCoordinates.max.longitude!}</eastBoundingCoordinate>
                                <northBoundingCoordinate>${geocoverage.boundingCoordinates.max.latitude!}</northBoundingCoordinate>
                                <southBoundingCoordinate>${geocoverage.boundingCoordinates.min.latitude!}</southBoundingCoordinate>
                            </boundingCoordinates>
                        </geographicCoverage>
                    </#list>
                    <#if (eml.temporalCoverages ? size > 0)>
                        <#list eml.temporalCoverages as tempcoverage>
                            <#if (tempcoverage.startDate)??>
                                <temporalCoverage>
                                    <#if (tempcoverage.endDate)??>
                                        <rangeOfDates>
                                            <#if (tempcoverage.startDate)??>
                                                <beginDate>
                                                    <calendarDate><#if (tempcoverage.startDate?string("SSS"))=="001">${tempcoverage.startDate?string("yyyy")}<#else>${tempcoverage.startDate?string(DATEIsoFormat)}</#if></calendarDate>
                                                </beginDate>
                                            </#if>
                                            <endDate>
                                                <calendarDate><#if (tempcoverage.endDate?string("SSS"))=="001">${tempcoverage.endDate?string("yyyy")}<#else>${tempcoverage.endDate?string(DATEIsoFormat)}</#if></calendarDate>
                                            </endDate>
                                        </rangeOfDates>
                                    <#else>
                                        <#if (tempcoverage.startDate)??>
                                            <singleDateTime>
                                                <calendarDate><#if (tempcoverage.startDate?string("SSS"))=="001">${tempcoverage.startDate?string("yyyy")}<#else>${tempcoverage.startDate?string(DATEIsoFormat)}</#if></calendarDate>
                                            </singleDateTime>
                                        </#if>
                                    </#if>
                                </temporalCoverage>
                            </#if>
                        </#list>
                    </#if>
                    <#if (eml.taxonomicCoverages ? size > 0)>
                        <#list eml.getTaxonomicCoverages() as taxoncoverage>
                            <#if (taxoncoverage.taxonKeywords ? size > 0)>
                                <taxonomicCoverage>
                                    <#if (taxoncoverage.getDescription())?has_content>
                                        <generalTaxonomicCoverage>${taxoncoverage.description!}</generalTaxonomicCoverage>
                                    </#if>
                                    <#list taxoncoverage.taxonKeywords as tk>
                                        <taxonomicClassification>
                                            <#if tk.rank?has_content>
                                                <taxonRankName>${tk.rank}</taxonRankName>
                                            </#if>
                                            <taxonRankValue>${tk.scientificName!}</taxonRankValue>
                                            <#if tk.commonName?has_content>
                                                <commonName>${tk.commonName}</commonName>
                                            </#if>
                                        </taxonomicClassification>
                                    </#list>
                                </taxonomicCoverage>
                            </#if>
                        </#list>
                    </#if>
                </coverage>
            </#if>
            <#if eml.purpose?has_content>
                <purpose>
                    <para>${eml.purpose}</para>
                </purpose>
            </#if>
            <#if eml.updateFrequency??>
                <maintenance>
                    <description>
                        <para>${eml.updateFrequencyDescription!}</para>
                    </description>
                    <maintenanceUpdateFrequency>${eml.updateFrequency.identifier}</maintenanceUpdateFrequency>
                </maintenance>
            </#if>

            <#-- The contact is the person or institution to contact with questions about the use, interpretation of a data set. -->
            <#if (eml.contacts?size>0)>
                <#list eml.contacts as contact>
                    <contact>
                        <@agentBlock agent=contact />
                    </contact>
                </#list>
            </#if>
            <#if (eml.getStudyExtent())?? || (eml.getSampleDescription())?? || (eml.getQualityControl())?? ||  ((eml.methodSteps)?? && ((eml.methodSteps) ? size > 0)) >
                <methods>
                    <#if (eml.methodSteps)??>
                        <#list eml.getMethodSteps() as methodStep>
                            <methodStep>
                                <description>
                                    <para>${methodStep!}</para>
                                </description>
                            </methodStep>
                        </#list>
                    </#if>
                    <#if (eml.getStudyExtent())?has_content || (eml.getSampleDescription())?has_content >
                        <sampling>
                            <studyExtent>
                                <description>
                                    <para>${eml.studyExtent!}</para>
                                </description>
                            </studyExtent>
                            <samplingDescription>
                                <para>${eml.sampleDescription!}</para>
                            </samplingDescription>
                        </sampling>
                    </#if>
                    <#if (eml.getQualityControl())?has_content>
                        <qualityControl>
                            <description>
                                <para>${eml.qualityControl!}</para>
                            </description>
                        </qualityControl>
                    </#if>
                </methods>
            </#if>
            <#if eml.project.title?has_content>
                <project <#if eml.project.identifier?has_content>id="${eml.project.identifier}"</#if>>
                    <title>${eml.project.title}</title>
                    <#list (eml.project.getPersonnel())! as personnel>
                        <personnel>
                            <individualName>
                                <#if (personnel.getFirstName())??>
                                    <givenName>${personnel.firstName}</givenName>
                                </#if>
                                <surName>${personnel.lastName!}</surName>
                            </individualName>
                            <#if (personnel.userIds?size>0)>
                                <#list personnel.userIds as userId>
                                    <#if userId.identifier?has_content && userId.directory?has_content>
                                        <userId directory="${userId.directory}">${userId.identifier}</userId>
                                    </#if>
                                </#list>
                            </#if>
                            <role>${personnel.role!}</role>
                        </personnel>
                    </#list>
                    <#if eml.project.description?has_content>
                        <abstract>
                            <para>${eml.project.description}</para>
                        </abstract>
                    </#if>
                    <#if eml.project.funding?has_content>
                        <funding>
                            <para>${eml.project.funding}</para>
                        </funding>
                    </#if>
                    <#if eml.project.studyAreaDescription?? && eml.project.studyAreaDescription.descriptorValue?has_content>
                        <studyAreaDescription>
                            <descriptor name="${eml.project.studyAreaDescription.getName().getName()!}"
                                        citableClassificationSystem="${eml.project.studyAreaDescription.citableClassificationSystem!}">
                                <descriptorValue>${eml.project.studyAreaDescription.descriptorValue}</descriptorValue>
                            </descriptor>
                        </studyAreaDescription>
                    </#if>
                    <#if eml.project.designDescription?has_content>
                        <designDescription>
                            <description>
                                <para>${eml.project.designDescription}</para>
                            </description>
                        </designDescription>
                    </#if>
                </project>
            </#if>
        </dataset>
        <#if ((eml.citation)??) ||
        (eml.bibliographicCitations ? size > 0) ||
        (eml.metadataLanguage)?? ||
        (eml.hierarchyLevel)?? ||
        (eml.PhysicalData ? size > 0) ||
        ((eml.jgtiCuratorialUnit)??) ||
        (eml.specimenPreservationMethod)?? ||
        (eml.temporalCoverages ? size > 0) ||
        (eml.parentCollectionId)?? ||
        (eml.collectionId)?? ||
        (eml.collectionName)?? ||
        (eml.logoUrl)?? ||
        (eml.getEmlVersion()>1)>
            <additionalMetadata>
                <metadata>
                    <gbif>
                        <#if (eml.dateStamp)??>
                            <dateStamp><@xmlSchemaDateTime eml.dateStamp/></dateStamp>
                        </#if>
                        <#if (eml.hierarchyLevel)??>
                            <hierarchyLevel>${eml.hierarchyLevel!}</hierarchyLevel>
                        </#if>
                        <#if (eml.citation.citation)?has_content>
                        <#-- How to cite the resource. -->
                            <#if (eml.citation.identifier)?has_content>
                                <citation identifier="${eml.citation.identifier!}">${eml.citation.citation!}</citation>
                            <#else>
                                <citation>${eml.citation.citation!}</citation>
                            </#if>
                        </#if>
                        <#if (eml.bibliographicCitations ? size > 0)>
                        <#-- Citations about the resource. -->
                            <bibliography>
                                <#list eml.getBibliographicCitations() as bcit>
                                    <#if (bcit.identifier)?has_content>
                                        <citation identifier="${bcit.identifier!}">${bcit.citation!}</citation>
                                    <#else>
                                        <citation>${bcit.citation!}</citation>
                                    </#if>
                                </#list>
                            </bibliography>
                        </#if>
                        <#if (eml.physicalData ? size > 0)>
                            <#list eml.getPhysicalData() as pdata>
                                <physical>
                                    <objectName>${pdata.name!}</objectName>
                                    <characterEncoding>${pdata.charset!}</characterEncoding>
                                    <dataFormat>
                                        <externallyDefinedFormat>
                                            <formatName>${pdata.format!}</formatName>
                                            <#if pdata.formatVersion?has_content>
                                                <formatVersion>${pdata.formatVersion}</formatVersion>
                                            </#if>
                                        </externallyDefinedFormat>
                                    </dataFormat>
                                    <distribution>
                                        <online>
                                            <url function="download">${pdata.distributionUrl!}</url>
                                        </online>
                                    </distribution>
                                </physical>
                            </#list>
                        </#if>
                        <#if (eml.getLogoUrl())??>
                            <resourceLogoUrl>${eml.logoUrl!}</resourceLogoUrl>
                        </#if>
                        <#if (eml.collections?? && eml.collections?size>0) >
                            <#list eml.collections as collection>
                                <#if collection.collectionName?has_content>
                                    <collection>
                                        <#if collection.parentCollectionId?has_content>
                                            <parentCollectionIdentifier>${collection.parentCollectionId}</parentCollectionIdentifier>
                                        </#if>
                                        <#if collection.collectionId?has_content>
                                            <collectionIdentifier>${collection.collectionId}</collectionIdentifier>
                                        </#if>
                                        <collectionName>${collection.collectionName}</collectionName>
                                    </collection>
                                </#if>
                            </#list>
                        </#if>
                        <#list eml.getTemporalCoverages() as tcoverage>
                            <#if (tcoverage.getFormationPeriod())??>
                                <formationPeriod>${tcoverage.formationPeriod}</formationPeriod>
                            </#if>
                        </#list>
                        <#if (eml.specimenPreservationMethods?size >0)>
                            <#list eml.specimenPreservationMethods as preservationMethod>
                                <#if preservationMethod?has_content>
                                    <specimenPreservationMethod>${preservationMethod}</specimenPreservationMethod>
                                </#if>
                            </#list>
                        </#if>
                        <#if (eml.temporalCoverages ? size > 0)>
                            <#list eml.getTemporalCoverages() as tcoverage>
                                <#if (tcoverage.getLivingTimePeriod())??>
                                    <livingTimePeriod>${tcoverage.livingTimePeriod}</livingTimePeriod>
                                </#if>
                            </#list>
                        </#if>
                        <#if (eml.jgtiCuratorialUnits ? size > 0)>
                            <#list eml.getJgtiCuratorialUnits() as cdata>
                                <jgtiCuratorialUnit>
                                    <#if (cdata.getUnitType())??>
                                        <jgtiUnitType>${cdata.unitType}</jgtiUnitType>
                                    </#if>
                                    <#if (cdata.rangeEnd)??>
                                        <jgtiUnitRange>
                                            <beginRange><#if (cdata.rangeStart)??>${cdata.rangeStart?string("####0")}</#if></beginRange>
                                            <endRange><#if (cdata.rangeEnd)??>${cdata.rangeEnd?string("####0")}</#if></endRange>
                                        </jgtiUnitRange>
                                    <#else>
                                        <jgtiUnits
                                                uncertaintyMeasure="<#if (cdata.uncertaintyMeasure)??>${cdata.uncertaintyMeasure?string("####0")}</#if>"><#if (cdata.rangeMean)??>${cdata.rangeMean?string("####0")}</#if></jgtiUnits>
                                    </#if>
                                </jgtiCuratorialUnit>
                            </#list>
                        </#if>
                        <#if (eml.getPreviousEmlVersion()?string != "1" && eml.getPreviousEmlVersion()?string != "1.0")>
                            <dc:replaces>${eml.guid}/v${eml.getPreviousEmlVersion()}.xml</dc:replaces>
                        </#if>
                    </gbif>
                </metadata>
            </additionalMetadata>
        </#if>
    </eml:eml>
</#escape>
