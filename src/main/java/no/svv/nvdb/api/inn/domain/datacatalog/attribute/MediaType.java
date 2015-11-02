// This software is produced by Statens vegvesen. Unauthorized redistribution,
// reproduction or usage of this software in whole or in part without the
// express written consent of Statens vegvesen is strictly prohibited.
// Copyright © 2014 Statens vegvesen
// ALL RIGHTS RESERVED
package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import org.slf4j.LoggerFactory;

import java.util.Arrays;


public enum MediaType {
    UNDEFINED(0), TSF(1), TEXT(2), AUDIO(3), IMAGE(4), VIDEO(5);

    private final int nvdbValue;

    MediaType(int nvdbValue) {
        this.nvdbValue = nvdbValue;
    }

    public int getNvdbValue() {
        return nvdbValue;
    }

    public static MediaType fromNvdbValue(int nvdbValue) {
        return Arrays.stream(MediaType.values())
                .filter(type -> type.nvdbValue == nvdbValue)
                .findFirst()
                .orElseGet(() -> {
                    LoggerFactory.getLogger(MediaType.class).error("Could not map {} to a value of {}", nvdbValue, MediaType.class.getSimpleName());
                    return null;
                });
    }
}
