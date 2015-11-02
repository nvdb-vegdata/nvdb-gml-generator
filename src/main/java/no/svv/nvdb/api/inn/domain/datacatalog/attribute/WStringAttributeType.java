package no.svv.nvdb.api.inn.domain.datacatalog.attribute;


public class WStringAttributeType extends PrimitiveAttributeType<String> {
    private String format;
    private Integer fieldLength;

    private WStringAttributeType() {
    }

    public String getFormat() {
        return format;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }
}
