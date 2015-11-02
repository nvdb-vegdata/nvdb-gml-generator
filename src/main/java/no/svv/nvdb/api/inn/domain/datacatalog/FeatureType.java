package no.svv.nvdb.api.inn.domain.datacatalog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.*;
import no.svv.nvdb.api.inn.domain.datacatalog.association.AssociationType;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static no.svv.nvdb.api.inn.domain.datacatalog.attribute.AttributeType.Type.LOCATIONAL;
import static no.svv.nvdb.api.inn.domain.datacatalog.attribute.AttributeType.Type.LIST;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class FeatureType {
    private int id;
    private String name;
    private String shortName;
    private String description;
    private int sortNum;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private boolean abstractFeatureType;
    private boolean filterOn;
    private boolean timePeriodRelevant;
    private boolean derived;
    private boolean parentNeeded;
    private boolean measureSet;
    private boolean secType2Ok;
    private String instructions;
    private String sosiName;
    private String standardSosiName;
    private String additionalInformation;
    private List<Integer> categories;

    @JsonProperty
    private AttributeTypeMap attributeTypeMap = new AttributeTypeMap();

    private List<AssociationType> incomingAssociations = new ArrayList<>();
    private List<AssociationType> outgoingAssociations = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public void setAbstractFeatureType(boolean abstractFeatureType) {
        this.abstractFeatureType = abstractFeatureType;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public void setDerived(boolean derived) {
        this.derived = derived;
    }

    public void setFilterOn(boolean filterOn) {
        this.filterOn = filterOn;
    }

    public void setParentNeeded(boolean parentNeeded) {
        this.parentNeeded = parentNeeded;
    }

    public void setMeasureSet(boolean measureSet) {
        this.measureSet = measureSet;
    }

    public void setTimePeriodRelevant(boolean timePeriodRelevant) {
        this.timePeriodRelevant = timePeriodRelevant;
    }

    public void setSosiName(String sosiName) {
        this.sosiName = sosiName;
    }

    public void setStandardSosiName(String standardSosiName) {
        this.standardSosiName = standardSosiName;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public void setSecType2Ok(boolean secType2Ok) {
        this.secType2Ok = secType2Ok;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public Stream<AttributeType> attributeTypes() {
        return attributeTypeMap.values().stream();
    }

    public void addAttributeType(AttributeType attributeType) {
        this.attributeTypeMap.put(attributeType.getId(), attributeType);
    }

    public Stream<AssociationType> incomingAssociations() {
        return incomingAssociations.stream();
    }

    public void addIncomingAssociation(AssociationType associationType) {
        incomingAssociations.add(associationType);
    }

    public Stream<AssociationType> outgoingAssociations() {
        return outgoingAssociations.stream();
    }

    public void addOutgoingAssociation(AssociationType associationType) {
        outgoingAssociations.add(associationType);
    }

    public boolean isTimePeriodRelevant() {
        return timePeriodRelevant;
    }

    public boolean isSecType2Ok() {
        return secType2Ok;
    }

    public boolean isFilterOn() {
        return filterOn;
    }

    public boolean isDerived() {
        return derived;
    }

    public boolean isParentNeeded() {
        return parentNeeded;
    }

    public boolean isMeasureSet() {
        return measureSet;
    }

    public boolean isAbstractFeatureType() {
        return abstractFeatureType;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getSortNum() {
        return sortNum;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public String getSosiName() {
        return sosiName;
    }

    public String getStandardSosiName() {
        return standardSosiName;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public boolean hasAttributeType(Integer typeId) {
        return typeId != null && typeId != 0 && attributeTypeMap.containsKey(typeId);
    }

    public boolean hasIncomingAssociations() {
        return !incomingAssociations.isEmpty();
    }

    public AttributeType getAttributeType(int id) {
        return attributeTypeMap.get(id);
    }

    @JsonIgnore
    public boolean isInObjectList() {
        return nonNull(startDate) && isNull(endDate);
    }

    /**
     * Fetches the locational attribute type for the given feature type, as long as it exists.
     *
     * @return The locational attribute
     */
    @JsonIgnore
    public LocationalAttributeType getLocationalAttributeType() {
        for (AttributeType attributeType : attributeTypeMap.values()) {
            if (attributeType.isType(LOCATIONAL)) {
                return (LocationalAttributeType) attributeType;
            }
            if (attributeType.isType(LIST)) {
                ListAttributeType listAttributeType = (ListAttributeType) attributeType;
                AttributeType contentAttributeType = listAttributeType.getContentType();
                if (contentAttributeType != null && contentAttributeType.isType(LOCATIONAL)) {
                    return (LocationalAttributeType) listAttributeType.getContentType();
                }
            }
        }
        return null;
    }

    private List<AttributeType> attributeTypesSortedById() {
        return attributeTypes().filter(at -> !at.isListContent()).sorted(Comparator.comparing(AttributeType::getId)).collect(toList());
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", name, id);
    }

    @JsonIgnore
    public boolean isCoverage() {
        return getLocationalAttributeType() != null && getLocationalAttributeType().isCovering();
    }

    @JsonIgnore
    public ExtentType getExtentType() {
        return getLocationalAttributeType() != null ? getLocationalAttributeType().getExtentType() : null;
    }
}
