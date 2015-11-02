// This software is produced by Statens vegvesen. Unauthorized redistribution,
// reproduction or usage of this software in whole or in part without the
// express written consent of Statens vegvesen is strictly prohibited.
// Copyright © 2014 Statens vegvesen
// ALL RIGHTS RESERVED
package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import org.slf4j.LoggerFactory;

import java.util.Arrays;


/**
 * Indicates whether a feature localization can be divided/split (due to road net maintenance).
 */
public enum Divisibility {
    NO(0), YES(1), NOT_DECIDED(9);

    private final int nvdbValue;

    Divisibility(int nvdbValue) {
        this.nvdbValue = nvdbValue;
    }

    public static Divisibility fromNvdbValue(int nvdbValue) {
        return Arrays.stream(Divisibility.values())
                .filter(type -> type.nvdbValue == nvdbValue)
                .findFirst()
                .orElseGet(() -> {
                    LoggerFactory.getLogger(Divisibility.class).error("Could not map {} to a value of {}", nvdbValue, Divisibility.class.getSimpleName());
                    return null;
                });
    }
}
