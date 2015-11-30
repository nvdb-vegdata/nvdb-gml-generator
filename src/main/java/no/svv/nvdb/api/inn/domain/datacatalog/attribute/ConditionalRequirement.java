package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Arrays;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class ConditionalRequirement {
    public enum Condition {

        // The attribute value should be calculated using the value of the specified spatial attribute given by the attributeTypeId field.
        VALUE_DERIVED_FROM_SPATIAL_ATTRIBUTE(3);

        private final int nvdbValue;

        Condition(int nvdbValue) {
            this.nvdbValue = nvdbValue;
        }

        public static Condition fromNvdbValue(int nvdbValue) {
            return Arrays.stream(Condition.values())
                    .filter(type -> type.nvdbValue == nvdbValue)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Condition " + nvdbValue + " is not supported"));
        }
    };

    private int owningAttributeTypeId;
    private Condition condition;
    private int attributeTypeId;
    private Integer attributeValueId;

    private ConditionalRequirement() {

    }

    public ConditionalRequirement(int owningAttributeTypeId, Condition condition, int attributeTypeId, Integer attributeValueId) {
        this.owningAttributeTypeId = owningAttributeTypeId;
        this.condition = condition;
        this.attributeTypeId = attributeTypeId;
        this.attributeValueId = attributeValueId;
    }

    public int getOwningAttributeTypeId() {
        return owningAttributeTypeId;
    }

    public Condition getCondition() {
        return condition;
    }

    public int getAttributeTypeId() {
        return attributeTypeId;
    }

    public Integer getAttributeValueId() {
        return attributeValueId;
    }

    private static final String ATTRIBUTE_TYPE_ID = "ATID";
    private static final String CONDREQ_CONDITION = "CR_CONDITION";
    private static final String CONDREQ_ATTR_TYPE_ID = "CR_ATTRTYPEID";
    private static final String CONDREQ_ATTR_VALUE_ID = "CR_ATTRVALUEID";

    @Override
    public String toString() {
        return "ConditionalRequirement{" +
                "owningAttributeTypeId=" + owningAttributeTypeId +
                ", condition=" + condition +
                ", attributeTypeId=" + attributeTypeId +
                ", attributeValueId=" + attributeValueId +
                '}';
    }
}
