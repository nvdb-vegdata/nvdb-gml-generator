// This software is produced by Statens vegvesen. Unauthorized redistribution,
// reproduction or usage of this software in whole or in part without the
// express written consent of Statens vegvesen is strictly prohibited.
// Copyright © 2014 Statens vegvesen
// ALL RIGHTS RESERVED
package no.svv.nvdb.api.inn.domain.datacatalog.association;

import org.slf4j.LoggerFactory;

import java.util.Arrays;


public enum InsideParent {
    UNDEFINED(null), NO(0), YES(1), YES_WITH_DEVIATIONS(2);

    private final Integer nvdbValue;

    private InsideParent(Integer nvdbValue) {
        this.nvdbValue = nvdbValue;
    }

    public Integer getNvdbValue() {
        return nvdbValue;
    }

    public static InsideParent fromNvdbValue(Integer nvdbValue) {
        return Arrays.stream(InsideParent.values())
                .filter(type -> type.nvdbValue == nvdbValue)
                .findFirst()
                .orElseGet(() -> {
                    LoggerFactory.getLogger(InsideParent.class).warn("Could not map {} to a value of {}", nvdbValue, InsideParent.class.getSimpleName());
                    return UNDEFINED;
                });
    }
}
