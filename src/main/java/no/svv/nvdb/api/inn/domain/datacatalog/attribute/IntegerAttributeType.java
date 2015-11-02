package no.svv.nvdb.api.inn.domain.datacatalog.attribute;


import no.svv.nvdb.api.inn.domain.datacatalog.Unit;

public class IntegerAttributeType extends PrimitiveAttributeType<Long> {
    private Integer fieldLength;
    private Long recommendedMinValue;
    private Long recommendedMaxValue;
    private Long absoluteMinValue;
    private Long absoluteMaxValue;
    private Integer complementarySign;
    private Unit unit;

    private IntegerAttributeType() {

    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public Long getRecommendedMinValue() {
        return recommendedMinValue;
    }

    public Long getRecommendedMaxValue() {
        return recommendedMaxValue;
    }

    public Long getAbsoluteMinValue() {
        return absoluteMinValue;
    }

    public Long getAbsoluteMaxValue() {
        return absoluteMaxValue;
    }

    public Integer getComplementarySign() {
        return complementarySign;
    }

    public Unit getUnit() {
        return unit;
    }
}