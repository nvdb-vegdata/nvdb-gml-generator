package XsdParser.DataCatalogObjectParser;

import XsdParser.Parser.ObjectParser;
import XsdParser.XSD.Component.Annotation.Annotation;
import XsdParser.XSD.Component.Annotation.AppInfo;
import XsdParser.XSD.Component.Annotation.Documentation;
import XsdParser.XSD.Component.Element;
import XsdParser.XSD.Component.Restriction.Facet.*;
import XsdParser.XSD.Component.Restriction.Restriction;
import XsdParser.XSD.Component.Schema;
import XsdParser.XSD.Component.SimpleType.SimpleType;
import XsdParser.XSD.Component.XSDComponent;
import XsdParser.XSD.Component.XSDStringFormatter;
import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDDataType;
import XsdParser.XSD.XSDTagAttribute;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.*;
import no.svv.nvdb.api.inn.domain.datacatalog.constraint.EnumStringAttribute;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Optional;

public class ObjXsdParser implements ObjectParser {
    private Schema schema;
    private Namespace globalNamespace;

    @Override
    public Schema createSchemaTag(String name) {
        globalNamespace = new Namespace(XSDTagAttribute.XMLNS, "http://www.w3.org/2001/XMLSchema","xsd", "");
        Schema schema = new Schema(name, globalNamespace);
        schema.addNamespace(new Namespace(XSDTagAttribute.XMLNS, "http://www.opengis.net/gml/3.2", "gml", "gml"));
        schema.addXSDComponentAttribute(new XSDComponentAttribute(XSDTagAttribute.TARGETNAMESPACE, "myNameSpace"));
        this.schema = schema;
        return schema;
    }

    @Override
    public Optional<XSDComponent> translate(Object object) {
        XSDComponent component = null;
        if(object instanceof StringAttributeType){
            XSDDataType dataType = DataCatalogXSDHelper.getXSDDatatype((AttributeType)object);
            component = createStringAttribute((StringAttributeType) object, dataType);
        } else if( object instanceof EnumStringAttribute){
            component = createEnumStringAttribute((EnumStringAttribute)object);
        }  else if(object instanceof IntegerAttributeType){
            XSDDataType dataType = DataCatalogXSDHelper.getXSDDatatype((AttributeType)object);
            component = createIntegerAttribute(((IntegerAttributeType) object), dataType);
        } else if(object instanceof RealAttributeType){
            XSDDataType dataType = DataCatalogXSDHelper.getXSDDatatype((AttributeType)object);
            component = createRealAttribute((RealAttributeType)object, dataType);
        } else if(object instanceof TimeAttributeType){
            XSDDataType dataType = DataCatalogXSDHelper.getXSDDatatype((AttributeType)object);
            component = createTimeAttribute((TimeAttributeType)object, dataType);
        } else if(object instanceof DateAttributeType){
            XSDDataType dataType = DataCatalogXSDHelper.getXSDDatatype((AttributeType)object);
            component = createDateAttribute((DateAttributeType)object, dataType);
        } else if(object instanceof ShortDateAttributeType){
            XSDDataType dataType = DataCatalogXSDHelper.getXSDDatatype((AttributeType)object);
            component = createShortDateAttribute((ShortDateAttributeType)object,dataType);
        } else if(object instanceof BoolAttributeType) {
            XSDDataType dataType = DataCatalogXSDHelper.getXSDDatatype((AttributeType)object);
            component = createBooleanAttribute((BoolAttributeType)object, dataType);
        } else if(object instanceof SpatialAttributeType){
            component = createSpatialAttribute((SpatialAttributeType)object);
        }


        return Optional.ofNullable(component);
    }

    private XSDComponent createSpatialAttribute(SpatialAttributeType at){

        ArrayList<XSDComponentAttribute> componentAttributes = new ArrayList<>();
        String elementName = XSDStringFormatter.createElementName(at.getName());

        XSDComponentAttribute nameAttribute = new XSDComponentAttribute(XSDTagAttribute.NAME,elementName);
        Optional<String> typeValue = DataCatalogXSDHelper.getGmlTypeFromSpatial(at.getSpatialType());
        componentAttributes.add(nameAttribute);
        if(typeValue.isPresent()){
            String typeWithPrefix = schema.getNamespaceWithName("gml").get().getTypeNameWithPrefix(typeValue.get());
            XSDComponentAttribute typeAttribute = new XSDComponentAttribute(XSDTagAttribute.TYPE, typeWithPrefix);
            componentAttributes.add(typeAttribute);
        }
        return createElement(at, componentAttributes);
    }



    private XSDComponent createBooleanAttribute(BoolAttributeType at, XSDDataType dataType){
        Restriction restriction = new Restriction(dataType, globalNamespace);
        SimpleType simpleType = new SimpleType(restriction, globalNamespace);
        return createElement(at,simpleType);
    }

    private XSDComponent createTimeAttribute(TimeAttributeType at, XSDDataType dataType){
        Restriction restriction = new Restriction(dataType, globalNamespace);
        SimpleType simpleType = new SimpleType(restriction, globalNamespace);

        Optional<String> pattern = DataCatalogXSDHelper.getRegexFromTimeFormat(at.getFormat());
        if(!pattern.isPresent()){
            throw new InvalidParameterException("[" + this.getClass().getSimpleName() + "] " + at.getFormat() + " timeFormat not supported");
        }
        XSDComponent patternFacet = new PatternFacet(pattern.get(), globalNamespace);
        simpleType.addChildComponent(patternFacet, simpleType);

        return createElement(at, simpleType);
    }

    private XSDComponent createDateAttribute(DateAttributeType at, XSDDataType dataType){
        Restriction restriction = new Restriction(dataType, globalNamespace);
        SimpleType simpleType = new SimpleType(restriction, globalNamespace);

        Optional<String> regexPattern = DataCatalogXSDHelper.getRegexFromDateFormat(at.getFormat());
        if(!regexPattern.isPresent()){
            throw new InvalidParameterException(at.getFormat() + " dateFormat not supported");
        }

        XSDComponent patternFacet = new PatternFacet(regexPattern.get(), globalNamespace);
        simpleType.addChildComponent(patternFacet, simpleType);
        return createElement(at, simpleType);
    }

    private XSDComponent createShortDateAttribute(ShortDateAttributeType at, XSDDataType dataType){
        Restriction restriction = new Restriction(dataType, globalNamespace);
        SimpleType simpleType = new SimpleType(restriction, globalNamespace);

        Optional<String> regexPattern = DataCatalogXSDHelper.getRegexFromDateFormat(at.getFormat());
        if(!regexPattern.isPresent()){
            throw new InvalidParameterException(at.getFormat() + " dateFormat not supported");
        }

        XSDComponent patternFacet = new PatternFacet(regexPattern.get(), globalNamespace);
        simpleType.addChildComponent(patternFacet, simpleType);

        return createElement(at, simpleType);
    }

    private XSDComponent createEnumStringAttribute(EnumStringAttribute enumAttribute){
        String value = DataCatalogXSDHelper.excapeIllegalChars(enumAttribute.getValue());
        return new EnumerationFacet(new XSDComponentAttribute(XSDTagAttribute.VALUE, value), globalNamespace);
    }

    private XSDComponent createStringAttribute(StringAttributeType at, XSDDataType dataType){
        Restriction restriction = new Restriction(dataType, globalNamespace);
        SimpleType simpleType = new SimpleType(restriction, globalNamespace);
        return createElement(at, simpleType);
    }
    private XSDComponent createIntegerAttribute(IntegerAttributeType at, XSDDataType dataType){
        Restriction restriction = new Restriction(dataType, globalNamespace);
        XSDComponent maxInclusive = new MaxInclusiveFacet( at.getAbsoluteMaxValue().toString(), globalNamespace);
        XSDComponent minInclusive = new MinInclusiveFacet(at.getAbsoluteMinValue().toString(), globalNamespace);

        if(at.getUnit() != null){
            Documentation unitDocumentation = new Documentation("Enhet: " + at.getUnit(), globalNamespace);
            Annotation annotation = new Annotation(globalNamespace);
            annotation.addChildComponent(unitDocumentation, annotation);
            restriction.addChildComponent(annotation,restriction);
        }
        SimpleType simpleType = new SimpleType(restriction, globalNamespace);
        simpleType.addChildComponent(minInclusive,restriction);
        simpleType.addChildComponent(maxInclusive,restriction);

        return createElement(at, simpleType);
    }

    private XSDComponent createRealAttribute(RealAttributeType at, XSDDataType dataType){
        Restriction restriction = new Restriction(dataType, globalNamespace);
        SimpleType simpleType = new SimpleType(restriction, globalNamespace);
        if(at.getUnit() != null ){
            Documentation unitDocumentation = new Documentation("Enhet: " + at.getUnit(),globalNamespace);
            Annotation annotation = new Annotation(globalNamespace);
            annotation.addChildComponent(unitDocumentation,annotation);
            simpleType.addChildComponent(annotation,simpleType);
        }

        if( at.getAbsoluteMaxValue() != null && at.getAbsoluteMinValue() != null){
            XSDComponent maxInclusive = new MaxInclusiveFacet(at.getAbsoluteMaxValue().toString(),globalNamespace);
            XSDComponent minInclusive = new MinInclusiveFacet(at.getAbsoluteMinValue().toString(), globalNamespace);
            restriction.addChildComponent(minInclusive,restriction);
            restriction.addChildComponent(maxInclusive,restriction);
        }

        if(at.getNumDecimals() != null){
            XSDComponent fractionDigits = new FractionDigitsFacet(at.getNumDecimals().toString(),globalNamespace);
            restriction.addChildComponent(fractionDigits, restriction);
        }
        return createElement(at, simpleType);
    }

    private Annotation createGeneralAnnotation(AttributeType at) {
        Annotation annotation = new Annotation(globalNamespace);
        Documentation documentation = new Documentation(at.getDescription(),globalNamespace);
        annotation.addChildComponent(documentation,annotation);
        AppInfo appinfo = new AppInfo(at.getSosiName() + " : " + at.getId(), globalNamespace);
        annotation.addChildComponent(appinfo, annotation);
        return  annotation;

    }

    private Element createElement(AttributeType at, ArrayList<XSDComponentAttribute> attributes){
        Annotation annotation = createGeneralAnnotation(at);
        Element element = new Element(attributes, globalNamespace);
        element.addChildComponent(annotation, element);
        element.setDeprecated(isDeprecated(at));
        return element;
    }

    private Element createElement(AttributeType at, XSDComponent component){
        Annotation annotation = createGeneralAnnotation(at);
        String elementName = XSDStringFormatter.createElementName(at.getName());
        XSDComponentAttribute nameAttribute = new XSDComponentAttribute(XSDTagAttribute.NAME,elementName);
        Element element = new Element(nameAttribute, component, globalNamespace);
        element.addChildComponent(annotation, element);
        element.setDeprecated(isDeprecated(at));
        return element;
    }

    private boolean isDeprecated(AttributeType at){
        return at.getName().toLowerCase().contains("utgår");
    }


}
