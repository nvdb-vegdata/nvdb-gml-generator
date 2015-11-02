package no.svv.nvdb.api.inn.domain.datacatalog.attribute;


import no.svv.nvdb.api.inn.domain.datacatalog.Unit;

public class RealAttributeType extends PrimitiveAttributeType<Double> {
    private Double recommendedMinValue;
    private Double recommendedMaxValue;
    private Double absoluteMinValue;
    private Double absoluteMaxValue;
    private Integer fieldLength;
    private Integer numDecimals;
    private Integer complementarySign;
    private Unit unit;

    private RealAttributeType() {
    }

    public Double getRecommendedMinValue() {
        return recommendedMinValue;
    }

    public Double getRecommendedMaxValue() {
        return recommendedMaxValue;
    }

    public Double getAbsoluteMinValue() {
        return absoluteMinValue;
    }

    public Double getAbsoluteMaxValue() {
        return absoluteMaxValue;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public Integer getNumDecimals() {
        return numDecimals;
    }

    public Integer getComplementarySign() {
        return complementarySign;
    }

    public Unit getUnit() {
        return unit;
    }
}
