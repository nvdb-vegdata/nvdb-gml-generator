package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import no.svv.nvdb.api.inn.domain.datacatalog.association.AssociationType;
import no.svv.nvdb.api.inn.domain.datacatalog.constraint.EnumAttribute;

import java.util.*;
import java.util.stream.Stream;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class AttributeType<T> {
    private int id;
    private Integer featureTypeId;
    private Integer listAttributeTypeId;
    private String name;
    private String description;
    private boolean mandatory;
    private boolean derived;

    private Integer sensitiveLevel;
    private Integer sortNum;
    private Requirement requirement;
    private String requirementComment;
    private String sosiName;

    protected boolean enumerated;

    protected Map<Integer, EnumAttribute<T>> enumValues = new HashMap<>();
    protected List<ConditionalRequirement> conditionalRequirements = new ArrayList<>();

    private ListAttributeType listAttributeType;

    private AttributeType structAttributeType;

    protected AttributeType() {
    }

    public int getId() {
        return id;
    }

    public Integer getFeatureTypeId() {
        return featureTypeId;
    }

    public Integer getListAttributeTypeId() {
        return listAttributeTypeId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public boolean isDerived() {
        return derived;
    }

    public Integer getSensitiveLevel() {
        return sensitiveLevel;
    }

    @JsonIgnore
    public boolean isSensitive() {
        return sensitiveLevel != null && sensitiveLevel > 0;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public String getRequirementComment() {
        return requirementComment;
    }

    public String getSosiName() {
        return sosiName;
    }

    public boolean isEnumerated() {
        return enumerated;
    }

    public <T> boolean hasEnumValue(T value) {
        for (EnumAttribute enumAttribute : enumValues.values()) {
            if (enumAttribute.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public Map<Integer, EnumAttribute<T>> getEnumValues() {
        return enumValues;
    }

    @JsonIgnore
    public Map<Integer, EnumAttribute<T>> getEnumValuesInObjectList() {
        Map<Integer, EnumAttribute<T>> enumValuesInObjectList = new HashMap<>();
        enumValues.forEach((k, v) -> {
            if (v.isInObjectList()) {
                enumValuesInObjectList.put(k, v);
            }
        });
        return enumValuesInObjectList;
    }

    public Stream<EnumAttribute<T>> enumValues() {
        return enumValues.values().stream();
    }

    public boolean hasConditionalRequirements() {
        return !conditionalRequirements.isEmpty();
    }

    public List<ConditionalRequirement> getConditionalRequirements() {
        return conditionalRequirements;
    }

    public Stream<ConditionalRequirement> conditionalRequirements() {
        return conditionalRequirements.stream();
    }

    public void addConditionalRequirement(ConditionalRequirement conditionalRequirement) {
        this.conditionalRequirements.add(conditionalRequirement);
    }

    @JsonIgnore
    public abstract boolean isInObjectList();

    @JsonIgnore
    public ListAttributeType getListAttributeType() {
        return listAttributeType;
    }

    public void setListAttributeType(ListAttributeType listAttributeType) {
        this.listAttributeType = listAttributeType;
    }

    @JsonIgnore
    public AttributeType getStructAttributeType() {
        return structAttributeType;
    }

    public void setStructAttributeType(AttributeType structAttributeType) {
        this.structAttributeType = structAttributeType;
    }

    public boolean isType(Type type) {
        return getType() == type;
    }

    @JsonIgnore
    public Type getType() {
        return Type.from(this);
    }

    @JsonIgnore
    public Integer getMaxNumber() {
        return this.isListContent() && this.getListAttributeType() != null ? this.getListAttributeType().getMaxNumber() : Integer.valueOf(1);
    }

    @JsonIgnore
    public Integer getMinNumber() {
        return this.isListContent() && this.getListAttributeType() != null ? this.getListAttributeType().getMinNumber() : Integer.valueOf(0);
    }

    @JsonIgnore
    public boolean isListContent() {
        return listAttributeTypeId != null;
    }

    @JsonIgnore
    public boolean isStructMember() {
        return Objects.nonNull(structAttributeType);
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }

    public enum Type {
        BOOL(BoolAttributeType.class),
        BLOB(BlobAttributeType.class),
        CHAR(CharAttributeType.class),
        DATE(DateAttributeType.class),
        INTEGER(IntegerAttributeType.class),
        LIST(ListAttributeType.class),
        REAL(RealAttributeType.class),
        SHORT_DATE(ShortDateAttributeType.class),
        SPATIAL(SpatialAttributeType.class),
        STRING(StringAttributeType.class),
        STRUCT(StructAttributeType.class),
        TIME(TimeAttributeType.class),
        W_STRING(WStringAttributeType.class),
        LOCATIONAL(LocationalAttributeType.class),
        ASSOCIATION(AssociationType.class);

        private static final Map<Class<? extends AttributeType>, Type> attributeTypes = new HashMap<>();

        static {
            for (Type type : values()) {
                attributeTypes.put(type.attributeTypeClass, type);
            }
        }

        private final Class<? extends AttributeType> attributeTypeClass;

        Type(Class<? extends AttributeType> attributeTypeClass) {
            this.attributeTypeClass = attributeTypeClass;
        }

        public static Type from(AttributeType attributeType) {
            attributeType = Objects.requireNonNull(attributeType, "First argument may not be null");
            return attributeTypes.get(attributeType.getClass());
        }
    }
}
