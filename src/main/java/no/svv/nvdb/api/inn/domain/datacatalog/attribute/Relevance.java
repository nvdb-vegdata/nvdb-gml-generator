// This software is produced by Statens vegvesen. Unauthorized redistribution,
// reproduction or usage of this software in whole or in part without the
// express written consent of Statens vegvesen is strictly prohibited.
// Copyright © 2014 Statens vegvesen
// ALL RIGHTS RESERVED
package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import org.slf4j.LoggerFactory;

import java.util.Arrays;


public enum Relevance {
    CANNOT(0), CAN(1), MUST(2);

    private final int nvdbValue;

    Relevance(int nvdbValue) {
        this.nvdbValue = nvdbValue;
    }

    public static Relevance fromNvdbValue(int nvdbValue) {
        return Arrays.stream(Relevance.values())
                .filter(type -> type.nvdbValue == nvdbValue)
                .findFirst()
                .orElseGet(() -> {
                    LoggerFactory.getLogger(Relevance.class).error("Could not map {} to a value of {}", nvdbValue, Relevance.class.getSimpleName());
                    return null;
                });
    }
}
