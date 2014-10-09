package org.gbif.metadata.eml;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * This class can be used to encapsulate information about collection data.
 */
public class Collection implements Serializable {

    /**
     * Generated
     */
    private static final long serialVersionUID = 7028536657811651816L;

    /**
     * Official name of the Collection in the local language.
     * Note: this could potentially be sourced from the resource title, but this is declared explicitly in the GBIF IPT
     * metadata profile, so must assume that this is required for a title in a different language, presumably to aid free
     * text discovery in original language
     *
     * @see <a href="http://purl.org/dc/elements/1.1/title">DublinCore</a>
     */
    private String collectionName;

    /**
     * The URI (LSID or URL) of the collection. In RDF, used as URI of the collection resource.
     *
     * @see <a href="http://rs.tdwg.org/ontology/voc/Collection#collectionId">TDWG Natural Collection Description</a>
     */
    private String collectionId;

    /**
     * Identifier for the parent collection for this sub-collection. Enables a hierarchy of collections and sub
     * collections to be built.
     *
     * @see <a href="http://rs.tdwg.org/ontology/voc/Collection#isPartOfCollection">TDWG Natural Collection
     * Description</a>
     */
    private String parentCollectionId;

    /**
     * Required by Struts2
     */
    public Collection() {
    }

    public String getCollectionName() {
        if (collectionName == null || collectionName.isEmpty()) {
            return null;
        }
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionId() {
        if (collectionId == null || collectionId.isEmpty()) {
            return null;
        }
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getParentCollectionId() {
        if (parentCollectionId == null || parentCollectionId.isEmpty()) {
            return null;
        }
        return parentCollectionId;
    }

    public void setParentCollectionId(String parentCollectionId) {
        this.parentCollectionId = parentCollectionId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Collection other = (Collection) obj;
        return Objects.equal(this.collectionId, other.collectionId) && Objects
                .equal(this.collectionName, other.collectionName) && Objects
                .equal(this.parentCollectionId, other.parentCollectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(collectionId, collectionName, parentCollectionId);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).
                add("collectionId", collectionId).
                add("collectionName", collectionName).
                add("parentCollectionId", parentCollectionId).
                toString();
    }
}
