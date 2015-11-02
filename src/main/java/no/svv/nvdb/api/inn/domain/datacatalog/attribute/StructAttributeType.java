package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import no.svv.nvdb.api.inn.domain.datacatalog.datatype.Struct;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StructAttributeType extends AttributeType<Struct> {
    public static int TYPE_ID_BASE = 600000;

    @JsonProperty
    private List<Integer> memberAttributeTypeIds = new ArrayList<>();

    private List<AttributeType> memberAttributeTypes = new ArrayList<>();

    private StructAttributeType() {
    }

    public Stream<AttributeType> memberAttributeTypes() {
        return memberAttributeTypes != null ? memberAttributeTypes.stream() : Stream.empty();
    }

    public Stream<Integer> memberAttributeTypeIds() {
        return memberAttributeTypeIds.stream();
    }

    public boolean hasMember(int attributeTypeId) {
        return memberAttributeTypeIds().anyMatch(Predicate.isEqual(attributeTypeId));
    }

    @JsonIgnore
    public List<AttributeType> getMemberAttributeTypes() {
        return memberAttributeTypes;
    }

    public void setMemberAttributeTypes(List<AttributeType> memberAttributeTypes) {
        this.memberAttributeTypes = memberAttributeTypes;
    }

    @Override
    public boolean isInObjectList() {
        return false;
    }

    private Set<AttributeType> getMandatoryMemberTypeIds() {
        return memberAttributeTypes()
                .filter(attributeType -> Requirement.REQUIRED.equals(attributeType.getRequirement()))
                .collect(Collectors.toSet());
    }

    private Set<AttributeType> getRecommendedMemberTypeIds() {
        return memberAttributeTypes()
                .filter(attributeType -> Requirement.RECOMMENDED.equals(attributeType.getRequirement()))
                .collect(Collectors.toSet());
    }
}
