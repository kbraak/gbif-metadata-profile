package org.gbif.metadata.eml;

/**
 * This enumeration mirrors the MaintUpFreqType enumeration coming from EML. The maintenance update frequency is
 * the frequency with which changes and additions are made to the dataset after the initial dataset is completed.
 *
 * @see <a href="https://knb.ecoinformatics.org/#external//emlparser/docs/eml-2.1.1/./eml-dataset.html#MaintUpFreqType">MaintUpFreqType EML ENUM</a>
 */
public enum MaintenanceUpdateFrequency {
    ANNUALLY("annually"),
    AS_NEEDED("asNeeded"),
    BIANNUALLY("biannually"),
    CONTINUALLY("continually"),
    DAILY("daily"),
    IRREGULAR("irregular"),
    MONTHLY("monthly"),
    NOT_PLANNED("notPlanned"),
    WEEKLY("weekly"),
    /**
     * Ignore the typo in "unknown", this is exactly how this term is spelt in the EML 2.1.1 specification.
     */
    UNKOWN("unkown"),
    OTHER_MAINTENANCE_PERIOD("otherMaintenancePeriod");

    // EML requires the ENUM value be in lowerCamelCase
    private String displayValue;

    MaintenanceUpdateFrequency(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    /**
     * Iterate through the enumerations, and try to find a match to the incoming string by comparing it against
     * the displayValue of each enumeration.
     *
     * @param f incoming maintenanceUpdate string
     * @return enumeration or null if no match was made
     */
    public static MaintenanceUpdateFrequency findByDisplayValue(String f) {
        for (MaintenanceUpdateFrequency entry : MaintenanceUpdateFrequency.values()) {
            if (entry.getDisplayValue().equals(f)) {
                return entry;
            }
        }
        return null;
    }
}
