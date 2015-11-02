package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import java.util.Arrays;


public enum Requirement {
    UNDECIDED(0),
    REQUIRED(1),
    RECOMMENDED(2),
    CONDITIONAL_REQUIRED(3),
    OPTIONAL(4),
    LESS_IMPORTANT(7),
    NOT_IN_USE(9);

    private final int nvdbValue;

    Requirement(int nvdbValue) {
        this.nvdbValue = nvdbValue;
    }

    public int getNvdbValue() {
        return nvdbValue;
    }

    public static Requirement fromNvdbValue(Integer nvdbValue) {
        if (nvdbValue == null) {
            return UNDECIDED;
        }

        return Arrays.stream(Requirement.values())
                .filter(type -> type.nvdbValue == nvdbValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Requirement type " + nvdbValue + " is not supported"));
    }
}
