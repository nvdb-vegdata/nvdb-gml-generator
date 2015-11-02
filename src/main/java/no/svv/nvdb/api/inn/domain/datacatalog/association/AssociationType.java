package no.svv.nvdb.api.inn.domain.datacatalog.association;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import no.svv.nvdb.api.inn.domain.datacatalog.FeatureType;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.AttributeType;
import no.svv.nvdb.api.inn.domain.datacatalog.datatype.Association;

import java.time.OffsetDateTime;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


public class AssociationType extends AttributeType<Association> {
    public static int TYPE_ID_BASE = 200000;
    public static int LIST_TYPE_ID_BASE = 220000;

    @JsonProperty
    private Integer targetFeatureTypeId;
    private FeatureType targetFeatureType;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private InsideParent insideParent;
    private Affiliation affiliation;

    private AssociationType() {
    }

    public void setTargetFeatureType(FeatureType targetFeatureType) {
        this.targetFeatureType = targetFeatureType;
    }

    @JsonIgnore
    public Integer getParentFeatureTypeId() {
        return getFeatureTypeId();
    }

    @JsonIgnore
    public Integer getChildFeatureTypeId() {
        return targetFeatureTypeId;
    }

    @JsonIgnore
    public FeatureType getChildFeatureType() {
        return targetFeatureType;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public InsideParent getInsideParent() {
        return insideParent;
    }

    @JsonIgnore
    public boolean isInsideParentRequired() {
        return asList(InsideParent.YES, InsideParent.YES_WITH_DEVIATIONS).contains(getInsideParent());
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    /**
     * Tells whether association represents a parent - child relation with weak ownership, i.e. child object can live without parent object.
     */
    @JsonIgnore
    public boolean isHierarchical() {
        return asList(Affiliation.COMPOSITION, Affiliation.AGGREGATION).contains(affiliation);
    }

    /**
     * Tells whether association represents a parent - child relation with strong ownership, i.e. child object dies with parent object.
     */
    @JsonIgnore
    public boolean isCompositional() {
        return Affiliation.COMPOSITION == affiliation;
    }

    @Override
    public boolean isInObjectList() {
        return nonNull(startDate) && isNull(endDate);
    }


    @Override
    public String toString() {
        return getId() + " (" + getName() + ") : " + getParentFeatureTypeId() + " -> " + getChildFeatureTypeId() + " " + affiliation;
    }
}
