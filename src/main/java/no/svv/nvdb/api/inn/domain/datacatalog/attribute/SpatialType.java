// This software is produced by Statens vegvesen. Unauthorized redistribution,
// reproduction or usage of this software in whole or in part without the
// express written consent of Statens vegvesen is strictly prohibited.
// Copyright © 2014 Statens vegvesen
// ALL RIGHTS RESERVED
package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import java.util.Arrays;


public enum SpatialType {
    POINT(0),
    LINE(1),
    POLYGON(2);

    private final int nvdbValue;

    SpatialType(int nvdbValue) {
        this.nvdbValue = nvdbValue;
    }

    public static SpatialType fromNvdbValue(int nvdbValue) {
        return Arrays.stream(SpatialType.values())
                .filter(type -> type.nvdbValue == nvdbValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Spatial type " + nvdbValue + " is not supported"));
    }
}