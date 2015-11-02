// This software is produced by Statens vegvesen. Unauthorized redistribution,
// reproduction or usage of this software in whole or in part without the
// express written consent of Statens vegvesen is strictly prohibited.
// Copyright © 2014 Statens vegvesen
// ALL RIGHTS RESERVED
package no.svv.nvdb.api.inn.domain.datacatalog;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;


/**
 * Maps feature type id to its corresponding feature type.
 *
 * @author Tore Eide Andersen (Kantega AS)
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class FeatureTypeMap extends HashMap<Integer, FeatureType> {
    private FeatureTypeMap() {
    }

    public FeatureTypeMap(Collection<FeatureType> featureTypes) {
        featureTypes.forEach(featureType -> put(featureType.getId(), featureType));
    }

    public Optional<FeatureType> getOrElse(Integer featureTypeId, Consumer<Integer> notPresentConsumer) {
        Optional<FeatureType> featureType = Optional.ofNullable(get(featureTypeId));
        if (!featureType.isPresent()) {
            notPresentConsumer.accept(featureTypeId);
        }
        return featureType;
    }
}
