package org.gbif.metadata.eml;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(directory, userId.directory)
            && Objects.equals(identifier, userId.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directory, identifier);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserId.class.getSimpleName() + "[", "]")
            .add("directory='" + directory + "'")
            .add("identifier='" + identifier + "'")
            .toString();
    }
}
