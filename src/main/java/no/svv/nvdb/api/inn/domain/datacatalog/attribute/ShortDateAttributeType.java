package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import java.time.MonthDay;


public class ShortDateAttributeType extends PrimitiveAttributeType<MonthDay> {
    private MonthDay defaultValue;
    private String format;
    private MonthDay recommendedMinValue;
    private MonthDay recommendedMaxValue;

    private ShortDateAttributeType() {
    }

    public String getFormat() {
        return format;
    }

    public MonthDay getRecommendedMinValue() {
        return recommendedMinValue;
    }

    public MonthDay getRecommendedMaxValue() {
        return recommendedMaxValue;
    }
}
