package no.svv.nvdb.api.inn.domain.datacatalog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import no.svv.nvdb.api.inn.domain.datacatalog.association.AssociationType;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.AttributeType;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.AttributeTypeMap;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.ListAttributeType;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.StructAttributeType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static no.svv.nvdb.api.inn.domain.datacatalog.attribute.AttributeType.Type.ASSOCIATION;
import static no.svv.nvdb.api.inn.domain.datacatalog.attribute.AttributeType.Type.STRUCT;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class DataCatalog {
    public static final int FTID_NVDB_DOCUMENTATION = 793;

    private DataCatalogVersion version;

    @JsonProperty
    private CategoryMap categoryMap;
    @JsonProperty
    private FeatureTypeMap featureTypeMap;
    private AttributeTypeMap attributeTypeMap;

    public Stream<Category> categories() {
        return categoryMap.values().stream();
    }

    public Stream<FeatureType> featureTypes() {
        return featureTypeMap.values().stream();
    }

    public DataCatalogVersion getVersion() {
        return version;
    }

    public Category getCategory(int categoryId) {
        return categoryMap.get(categoryId);
    }

    public FeatureType getFeatureType(int featureTypeId) {
        return featureTypeMap.get(featureTypeId);
    }

    public FeatureType getFeatureType(Integer featureTypeId) {
        return featureTypeId == null ? null : featureTypeMap.get(featureTypeId);
    }

    public <T extends AttributeType> T getAttributeType(Integer attributeTypeId) {
        if (attributeTypeId == null) {
            return null;
        }
        return (T) attributeTypeMap.get(attributeTypeId);
    }

    public <T extends AttributeType> T getContentType(Integer attributeTypeId) {
        AttributeType attributeType = getAttributeType(attributeTypeId);
        if (attributeType instanceof ListAttributeType) {
            return (T) ((ListAttributeType) attributeType).getContentType();
        }
        return (T) attributeType;
    }

    public AttributeType getAttributeType(int attributeTypeId) {
        return attributeTypeMap.get(attributeTypeId);
    }

    @JsonIgnore
    public int getNumFeatureTypes() {
        return featureTypeMap.size();
    }

    @JsonIgnore
    public int getNumAttributeTypes() {
        return attributeTypeMap.size();
    }

    public void toJson(OutputStream os) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());

        mapper
                .writerWithDefaultPrettyPrinter()
                .without(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .writeValue(os, this);
    }

    public static DataCatalog fromJson(InputStream is) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());

        DataCatalog dataCatalog = mapper
                .reader(DataCatalog.class)
                .readValue(is);

        reconnectDeserializedObjects(dataCatalog);
        return dataCatalog;
    }

    /**
     * When deserialized from JSON the feature types contain all its attribute types within the attribute type map.
     * However, none of the cross references are assigned. This method:
     * <p/>
     * - Sets the contentType in ListAttributeType objects
     * - Sets the listAttributeType in AttributeType objects
     * - Sets the memberAttributeTypes in StructAttributeType objects
     * - Adds all AssociationType objects to relevant incoming and outgoing association lists of feature tyoes.
     */
    private static void reconnectDeserializedObjects(DataCatalog dataCatalog) throws IOException {
        dataCatalog.featureTypes().forEach(featureType ->
        {
            featureType.attributeTypes()
                    .filter(AttributeType::isListContent)
                    .forEach(contentAttributeType ->
                    {
                        ListAttributeType listAttributeType = (ListAttributeType) featureType.getAttributeType(contentAttributeType.getListAttributeTypeId());
                        listAttributeType.setContentType(contentAttributeType);
                        contentAttributeType.setListAttributeType(listAttributeType);
                    });

            featureType.attributeTypes()
                    .filter(attributeType -> attributeType.isType(ASSOCIATION))
                    .map(attributeType -> (AssociationType) attributeType)
                    .forEach(associationType ->
                    {
                        featureType.addOutgoingAssociation(associationType);
                        dataCatalog.getFeatureType(associationType.getChildFeatureTypeId())
                                .addIncomingAssociation(associationType);
                    });

            featureType.attributeTypes()
                    .filter(attributeType -> attributeType.isType(STRUCT))
                    .map(attributeType -> (StructAttributeType) attributeType)
                    .forEach(structAttributeType ->
                    {
                        List<AttributeType> memberAttributeTypes = new ArrayList<>();
                        structAttributeType.memberAttributeTypeIds().forEach(memberAttributeTypeId ->
                        {
                            AttributeType memberAttributeType = featureType.getAttributeType(memberAttributeTypeId);
                            memberAttributeTypes.add(memberAttributeType);
                        });

                        structAttributeType.setMemberAttributeTypes(memberAttributeTypes);
                    });
        });
    }
}
