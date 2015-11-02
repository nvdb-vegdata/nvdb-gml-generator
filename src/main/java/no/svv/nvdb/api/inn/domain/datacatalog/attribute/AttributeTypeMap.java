// This software is produced by Statens vegvesen. Unauthorized redistribution,
// reproduction or usage of this software in whole or in part without the
// express written consent of Statens vegvesen is strictly prohibited.
// Copyright © 2014 Statens vegvesen
// ALL RIGHTS RESERVED
package no.svv.nvdb.api.inn.domain.datacatalog.attribute;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import no.svv.nvdb.api.inn.domain.datacatalog.FeatureTypeMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Maps attribute type id to its corresponding attribute type.
 *
 * @author Tore Eide Andersen (Kantega AS)
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class AttributeTypeMap extends HashMap<Integer, AttributeType> {
    public AttributeTypeMap() {
    }

    public AttributeTypeMap(FeatureTypeMap featureTypeMap) {
        featureTypeMap.values().forEach(featureType ->
                featureType.attributeTypes().forEach(attributeType ->
                        put(attributeType.getId(), attributeType)));
    }

    public AttributeTypeMap(Collection<AttributeType> attributeTypes) {
        attributeTypes.forEach(attributeType -> put(attributeType.getId(), attributeType));
    }

    public Optional<AttributeType> getOrElse(Integer attributeTypeId, Consumer<Integer> notPresentConsumer) {
        Optional<AttributeType> attributeType = Optional.ofNullable(get(attributeTypeId));
        if (!attributeType.isPresent()) {
            notPresentConsumer.accept(attributeTypeId);
        }
        return attributeType;
    }
}
