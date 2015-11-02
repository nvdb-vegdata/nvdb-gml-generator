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
 * Maps category id to its corresponding category.
 *
 * @author Tore Eide Andersen (Kantega AS)
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class CategoryMap extends HashMap<Integer, Category> {
    private CategoryMap() {
    }

    public CategoryMap(Collection<Category> categories) {
        categories.forEach(category -> put(category.getId(), category));
    }

    public Optional<Category> getOrElse(Integer categoryId, Consumer<Integer> notPresentConsumer) {
        Optional<Category> category = Optional.ofNullable(get(categoryId));
        if (!category.isPresent()) {
            notPresentConsumer.accept(categoryId);
        }
        return category;
    }
}
