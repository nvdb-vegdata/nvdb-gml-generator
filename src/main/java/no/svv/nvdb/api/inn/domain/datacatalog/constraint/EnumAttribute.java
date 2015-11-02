package no.svv.nvdb.api.inn.domain.datacatalog.constraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.OffsetDateTime;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class EnumAttribute<T> {
    private int enumId;
    private int attributeTypeId;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

    private T value;
    private String shortValue;
    private int sortNum;

    public EnumAttribute() {
    }

    public EnumAttribute(int enumId, int attributeTypeId, OffsetDateTime startDate, OffsetDateTime endDate, T value, String shortValue, int sortNum) {
        this.enumId = enumId;
        this.attributeTypeId = attributeTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.shortValue = shortValue;
        this.value = value;
        this.sortNum = sortNum;
    }

    public int getEnumId() {
        return enumId;
    }

    public void setEnumId(int enumId) {
        this.enumId = enumId;
    }

    public int getAttributeTypeId() {
        return attributeTypeId;
    }

    public void setAttributeTypeId(int attributeTypeId) {
        this.attributeTypeId = attributeTypeId;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public String getShortValue() {
        return shortValue;
    }

    public void setShortValue(String shortValue) {
        this.shortValue = shortValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @JsonIgnore
    public boolean isInObjectList() {
        return nonNull(startDate) && isNull(endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnumAttribute)) return false;

        EnumAttribute that = (EnumAttribute) o;

        if (attributeTypeId != that.attributeTypeId) return false;
        if (enumId != that.enumId) return false;
        if (shortValue != null ? !shortValue.equals(that.shortValue) : that.shortValue != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        // no hashcode implementation as class is not immutable
        return super.hashCode();
    }

    @Override
    public String toString() {
        return value + " (" + enumId + ")";
    }
}
