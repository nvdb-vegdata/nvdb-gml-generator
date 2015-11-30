package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import no.svv.nvdb.api.inn.domain.datacatalog.constraint.EnumAttribute;

import java.time.OffsetDateTime;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public abstract class PrimitiveAttributeType<T> extends AttributeType<T> {
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Integer directionSensitive; // Retningsfølsom
    private Integer extentSensitive; // Lengdefølsom

    protected PrimitiveAttributeType() {
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public Integer getDirectionSensitive() {
        return directionSensitive;
    }

    public Integer getExtentSensitive() {
        return extentSensitive;
    }

    public void addEnumValue(EnumAttribute enumValue) {
        enumValues.put(enumValue.getEnumId(), enumValue);
    }

    @Override
    public boolean isInObjectList() {
        return nonNull(startDate) && isNull(endDate);
    }
}
