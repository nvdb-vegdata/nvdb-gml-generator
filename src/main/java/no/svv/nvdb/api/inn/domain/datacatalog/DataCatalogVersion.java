package no.svv.nvdb.api.inn.domain.datacatalog;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.OffsetDateTime;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class DataCatalogVersion {
    private int id;
    private String version;
    private OffsetDateTime versionDate;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

    private DataCatalogVersion() {
    }

    public DataCatalogVersion(int id, String version, OffsetDateTime versionDate, OffsetDateTime startDate, OffsetDateTime endDate) {
        this.id = id;
        this.version = version;
        this.versionDate = versionDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public OffsetDateTime getVersionDate() {
        return versionDate;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return version + " (" + id + ")";
    }
}
