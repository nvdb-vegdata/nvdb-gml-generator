// This software is produced by Statens vegvesen. Unauthorized redistribution,
// reproduction or usage of this software in whole or in part without the
// express written consent of Statens vegvesen is strictly prohibited.
// Copyright © 2014 Statens vegvesen
// ALL RIGHTS RESERVED
package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import org.slf4j.LoggerFactory;

import java.util.Arrays;


/**
 * Indicates whether a feature localization survives road net maintenance or not.
 */
public enum Survival {
    NO(0), YES(1), NOT_DECIDED(9);

    private final int nvdbValue;

    Survival(int nvdbValue) {
        this.nvdbValue = nvdbValue;
    }

    public static Survival fromNvdbValue(int nvdbValue) {
        return Arrays.stream(Survival.values())
                .filter(type -> type.nvdbValue == nvdbValue)
                .findFirst()
                .orElseGet(() -> {
                    LoggerFactory.getLogger(Survival.class).error("Could not map {} to a value of {}", nvdbValue, Survival.class.getSimpleName());
                    return null;
                });
    }
}
