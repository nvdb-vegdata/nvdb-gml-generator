// This software is produced by Statens vegvesen. Unauthorized redistribution,
// reproduction or usage of this software in whole or in part without the
// express written consent of Statens vegvesen is strictly prohibited.
// Copyright © 2014 Statens vegvesen
// ALL RIGHTS RESERVED
package no.svv.nvdb.api.inn.domain.datacatalog.association;

import org.slf4j.LoggerFactory;

import java.util.Arrays;


public enum Affiliation {
    UNDEFINED(0),
    ASSOCIATION(1), // Child provides necessary supplementary information about the parent object. Child survives parent.
    AGGREGATION(2), // Assembly of several features making a natural unit. Child survives parent.
    COMPOSITION(3), // Child belongs to and dies together with the parent object.
    TOPOLOGY(4);    // TBD

    private final Integer nvdbValue;

    Affiliation(Integer nvdbValue) {
        this.nvdbValue = nvdbValue;
    }

    public Integer getNvdbValue() {
        return nvdbValue;
    }

    public static Affiliation fromNvdbValue(Integer nvdbValue) {
        return Arrays.stream(Affiliation.values())
                .filter(type -> type.nvdbValue == nvdbValue)
                .findFirst()
                .orElseGet(() -> {
                    LoggerFactory.getLogger(Affiliation.class).warn("Could not map {} to a value of {}", nvdbValue, Affiliation.class.getSimpleName());
                    return UNDEFINED;
                });
    }
}
