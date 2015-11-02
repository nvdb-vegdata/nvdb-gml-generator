package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import no.svv.nvdb.api.inn.domain.datacatalog.datatype.Locational;


public class LocationalAttributeType extends AttributeType<Locational> {
    public static int TYPE_ID_BASE = 100000;

    private ExtentType extentType;
    private boolean heightRelevant;
    private boolean directionRelevant;
    private Relevance laneRelevance;
    private Relevance sidePositionRelevance;
    private String coverage; // Dekningsgrad
    private boolean overlapAllowed;
    private Survival updateSurvival; // AjourholdI
    private double updateSupplementaryLength; // Suppleringslengde
    private Divisibility updateDivisibility; // AjourholdSplitt
    private boolean transferableBetweenLevels;
    private boolean insideParent;

    private LocationalAttributeType() {
    }

    public ExtentType getExtentType() {
        return extentType;
    }

    public boolean isHeightRelevant() {
        return heightRelevant;
    }

    public boolean isDirectionRelevant() {
        return directionRelevant;
    }

    public Relevance getLaneRelevance() {
        return laneRelevance;
    }

    public Relevance getSidePositionRelevance() {
        return sidePositionRelevance;
    }

    public String getCoverage() {
        return coverage;
    }

    public boolean isOverlapAllowed() {
        return overlapAllowed;
    }

    public Survival getUpdateSurvival() {
        return updateSurvival;
    }

    public double getUpdateSupplementaryLength() {
        return updateSupplementaryLength;
    }

    public Divisibility getUpdateDivisibility() {
        return updateDivisibility;
    }

    public boolean isTransferableBetweenLevels() {
        return transferableBetweenLevels;
    }

    public boolean isInsideParent() {
        return insideParent;
    }

    @Override
    public boolean isInObjectList() {
        return false;
    }

    @JsonIgnore
    public boolean isCovering() {
        return "X".equalsIgnoreCase(getCoverage());
    }
}
