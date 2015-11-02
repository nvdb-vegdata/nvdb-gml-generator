package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import java.time.LocalTime;


public class TimeAttributeType extends PrimitiveAttributeType<LocalTime> {
    private String format;
    private LocalTime recommendedMinValue;
    private LocalTime recommendedMaxValue;

    private TimeAttributeType() {
    }

    public String getFormat() {
        return format;
    }

    public LocalTime getRecommendedMinValue() {
        return recommendedMinValue;
    }

    public LocalTime getRecommendedMaxValue() {
        return recommendedMaxValue;
    }
}
