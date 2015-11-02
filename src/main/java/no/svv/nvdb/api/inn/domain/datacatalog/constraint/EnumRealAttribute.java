package no.svv.nvdb.api.inn.domain.datacatalog.constraint;


import java.time.OffsetDateTime;

public class EnumRealAttribute extends EnumAttribute<Double> {
    public EnumRealAttribute() {
    }

    public EnumRealAttribute(int enumId, int attributeTypeId, OffsetDateTime startDate, OffsetDateTime endDate, Double value, String shortValue, int sortNum) {
        super(enumId, attributeTypeId, startDate, endDate, value, shortValue, sortNum);
    }
}
