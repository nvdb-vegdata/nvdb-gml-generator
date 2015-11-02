package no.svv.nvdb.api.inn.domain.datacatalog.constraint;


import java.time.OffsetDateTime;

public class EnumStringAttribute extends EnumAttribute<String> {
    public EnumStringAttribute() {
    }

    public EnumStringAttribute(int enumId, int attributeTypeId, OffsetDateTime startDate, OffsetDateTime endDate, String value, String shortValue, int sortNum) {
        super(enumId, attributeTypeId, startDate, endDate, value, shortValue, sortNum);
    }
}
