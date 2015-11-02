package no.svv.nvdb.api.inn.domain.datacatalog;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.OffsetDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Category {
    private int id;
    private String name;
    private String shortName;
    private String description;
    private int sortNum;
    private OffsetDateTime startDate;

    private Category() {
    }

    public Category(int id, String name, String shortName, String description, int sortNum, OffsetDateTime startDate) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.description = description;
        this.sortNum = sortNum;
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }

    public int getSortNum() {
        return sortNum;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }
}
