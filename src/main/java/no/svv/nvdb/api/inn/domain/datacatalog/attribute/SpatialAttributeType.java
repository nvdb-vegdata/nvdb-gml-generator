package no.svv.nvdb.api.inn.domain.datacatalog.attribute;


import no.svv.nvdb.api.inn.domain.datacatalog.datatype.Geometry;

public class SpatialAttributeType extends AttributeType<Geometry> {
    private SpatialType spatialType;
    private int directPositionDimension;
    private boolean insideParent;

    private SpatialAttributeType() {
    }

    public SpatialType getSpatialType() {
        return spatialType;
    }

    public int getDirectPositionDimension() {
        return directPositionDimension;
    }

    public boolean isInsideParent() {
        return insideParent;
    }

    @Override
    public boolean isInObjectList() {
        return false;
    }
}
