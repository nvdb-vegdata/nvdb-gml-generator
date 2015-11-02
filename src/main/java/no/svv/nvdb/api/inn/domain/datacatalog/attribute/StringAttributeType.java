package no.svv.nvdb.api.inn.domain.datacatalog.attribute;


public class StringAttributeType extends PrimitiveAttributeType<String> {
    private String format;
    private Integer fieldLength;

    private StringAttributeType() {
    }

    public String getFormat() {
        return format;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }
}
