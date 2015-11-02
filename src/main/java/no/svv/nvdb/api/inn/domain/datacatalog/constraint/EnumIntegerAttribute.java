package no.svv.nvdb.api.inn.domain.datacatalog.constraint;


import java.time.OffsetDateTime;

public class EnumIntegerAttribute extends EnumAttribute<Long> {
    public EnumIntegerAttribute() {
    }

    public EnumIntegerAttribute(int enumId, int attributeTypeId, OffsetDateTime startDate, OffsetDateTime endDate, Long value, String shortValue, int sortNum) {
        super(enumId, attributeTypeId, startDate, endDate, value, shortValue, sortNum);
    }
}
