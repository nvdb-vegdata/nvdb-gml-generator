package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class ListAttributeType extends AttributeType {
    private int contentAttributeTypeId;
    private Integer minNumber;
    private Integer maxNumber;

    @JsonIgnore
    private AttributeType contentType;

    private ListAttributeType() {
    }

    public int getContentAttributeTypeId() {
        return contentAttributeTypeId;
    }

    @Override
    public Integer getMinNumber() {
        return minNumber;
    }

    @Override
    public Integer getMaxNumber() {
        return maxNumber;
    }

    public AttributeType getContentType() {
        return contentType;
    }

    public void setContentType(AttributeType contentType) {
        this.contentType = contentType;
    }

    @JsonIgnore
    public boolean isAssociationList() {
        return (getId() >= 220000) && (getId() <= 229999);
    }

    @Override
    public boolean isInObjectList() {
        return getContentType().isInObjectList();
    }
}
