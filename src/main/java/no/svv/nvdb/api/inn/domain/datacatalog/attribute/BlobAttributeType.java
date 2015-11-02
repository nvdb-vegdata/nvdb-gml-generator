package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import no.svv.nvdb.api.inn.domain.datacatalog.datatype.Blob;

import java.math.BigDecimal;


public class BlobAttributeType extends PrimitiveAttributeType<Blob> {
    private MediaType format;

    private BlobAttributeType() {
    }

    public MediaType getFormat() {
        return format;
    }

    private String asString(Object attribute) {
        return (String)attribute;
    }

    private Long asLong(Object attribute) {
        return attribute == null ? null : ((BigDecimal) attribute).longValue();
    }

    private Integer asInteger(Object attribute) {
        return attribute == null ? null : ((BigDecimal) attribute).intValue();
    }
}
