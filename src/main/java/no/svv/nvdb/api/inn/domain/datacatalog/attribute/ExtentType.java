// This software is produced by Statens vegvesen. Unauthorized redistribution,
// reproduction or usage of this software in whole or in part without the
// express written consent of Statens vegvesen is strictly prohibited.
// Copyright © 2014 Statens vegvesen
// ALL RIGHTS RESERVED
package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import java.util.Arrays;


public enum ExtentType {
    POINT(1), LINE(3), TURN(4);

    private final int nvdbValue;

    ExtentType(int nvdbValue) {
        this.nvdbValue = nvdbValue;
    }

    public static ExtentType fromNvdbValue(int nvdbValue) {
        return Arrays.stream(ExtentType.values())
                .filter(type -> type.nvdbValue == nvdbValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Extent type " + nvdbValue + " is not supported"));
    }
}