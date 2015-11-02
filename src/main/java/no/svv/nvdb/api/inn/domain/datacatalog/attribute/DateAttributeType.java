package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import java.time.LocalDate;


public class DateAttributeType extends PrimitiveAttributeType<LocalDate> {
    private String format;
    private LocalDate recommendedMinValue;
    private LocalDate recommendedMaxValue;

    private DateAttributeType() {
    }

    public String getFormat() {
        return format;
    }

    public LocalDate getRecommendedMinValue() {
        return recommendedMinValue;
    }

    public LocalDate getRecommendedMaxValue() {
        return recommendedMaxValue;
    }
}
