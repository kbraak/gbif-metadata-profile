package org.gbif.metadata.eml;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * This class can be used to encapsulate information about an identifier for the agent composed of an identifier and
 * the personnel directory the identifier is linked to (e.g. http://orcid.org/).
 */
public class UserId implements Serializable {
    /**
     * Generated
     */
    private static final long serialVersionUID = 8600344167438888243L;

    private String directory;
    private String identifier;

    /**
     * Default constructor required by Struts2.
     */
    public UserId() {
    }

    public UserId(String directory, String identifier) {
        this.directory = directory;
        this.identifier = identifier;
    }

    /**
     * @return the name of the directory system to which the identifier applies.
     */
    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /**
     * @return the identifier that links this agent to a directory of personnel.
     */
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserId other = (UserId) obj;
        return Objects.equal(this.identifier, other.identifier) && Objects.equal(this.directory, other.directory);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifier, directory);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).
                add("identifier", identifier).
                add("directory", directory).
                toString();
    }
}
