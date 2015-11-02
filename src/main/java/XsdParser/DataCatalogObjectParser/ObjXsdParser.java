package XsdParser.DataCatalogObjectParser;

import XsdParser.Parser.ObjectParser;
import XsdParser.XSD.Component.Annotation.Documentation;
import XsdParser.XSD.Component.Element;
import XsdParser.XSD.Component.Restriction.Facet.*;
import XsdParser.XSD.Component.Restriction.Restriction;
import XsdParser.XSD.Component.Schema;
import XsdParser.XSD.Component.SimpleType.SimpleType;
import XsdParser.XSD.Component.XSDComponent;
import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTagAttribute;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.IntegerAttributeType;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.RealAttributeType;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.StringAttributeType;
import no.svv.nvdb.api.inn.domain.datacatalog.constraint.EnumStringAttribute;

import java.util.Optional;

public class ObjXsdParser implements ObjectParser {
    private Schema schema;

    @Override
    public Schema createSchemaTag() {
        Schema schema = new Schema(new XSDComponentAttribute(XSDTagAttribute.XLMNS,"http://www.w3.org/2001/XMLSchema"));
        schema.addNamespace(new Namespace(XSDTagAttribute.XLMNS,"http://www.opengis.net/gml/3.2", "gml"));
        this.schema = schema;
        return schema;
    }

    @Override
    public Optional<XSDComponent> translate(Object object) {
        XSDComponent component = null;

        if(object instanceof StringAttributeType){
            component = createStringAttribute((StringAttributeType)object);
        } else if( object instanceof EnumStringAttribute){
            component = createEnumStringAttribute((EnumStringAttribute)object);
        }  else if(object instanceof IntegerAttributeType){
            component = createIntegerAttribute(((IntegerAttributeType) object));
        } else if(object instanceof RealAttributeType){
            component = createRealAttribute((RealAttributeType)object);
        }
        return Optional.ofNullable(component);
    }

    private XSDComponent createEnumStringAttribute(EnumStringAttribute enumAttribute){
        return new EnumerationFacet(new XSDComponentAttribute(XSDTagAttribute.VALUE, enumAttribute.getValue()));
    }

    private XSDComponent createStringAttribute(StringAttributeType stringAttribute){
        Restriction restriction = new Restriction(new XSDComponentAttribute(XSDTagAttribute.BASE, stringAttribute.getType().toString()));
        SimpleType simpleType = new SimpleType(restriction);
        Documentation documentation = new Documentation(stringAttribute.getDescription());
        simpleType.addChildComponent(documentation, simpleType);
        XSDComponentAttribute nameAttribute = new XSDComponentAttribute(XSDTagAttribute.NAME, toCamelCase(stringAttribute.getName()));
        return new Element(nameAttribute, simpleType);
    }
    private XSDComponent createIntegerAttribute(IntegerAttributeType at){
        Restriction restriction = new Restriction(new XSDComponentAttribute(XSDTagAttribute.BASE, at.getType().toString()));
        XSDComponent maxInclusive = new MaxInclusiveFacet(new XSDComponentAttribute(XSDTagAttribute.VALUE, at.getAbsoluteMaxValue().toString()));
        XSDComponent minInclusive = new MinInclusiveFacet(new XSDComponentAttribute(XSDTagAttribute.VALUE, at.getAbsoluteMinValue().toString()));
        XSDComponent maxLength = new MaxLengthFacet(new XSDComponentAttribute(XSDTagAttribute.VALUE, at.getFieldLength().toString()));
        if(at.getUnit() != null){
            XSDComponent unitDocumentation = new Documentation("Enhet: " + at.getUnit());
            restriction.addChildComponent(unitDocumentation,restriction);
        }
        SimpleType simpleType = new SimpleType(restriction);
        simpleType.addChildComponent(minInclusive,restriction);
        simpleType.addChildComponent(maxInclusive,restriction);
        simpleType.addChildComponent(maxLength,restriction);
        XSDComponent attributeDocumentation = new Documentation(at.getDescription());
        simpleType.addChildComponent(attributeDocumentation, simpleType);
        return new Element(new XSDComponentAttribute(XSDTagAttribute.NAME, toCamelCase(at.getName())), simpleType);
    }

    private XSDComponent createRealAttribute(RealAttributeType at){
        Restriction restriction = new Restriction(new XSDComponentAttribute(XSDTagAttribute.BASE, at.getType().toString()));
        XSDComponent maxInclusive = new MaxInclusiveFacet(new XSDComponentAttribute(XSDTagAttribute.VALUE, at.getAbsoluteMaxValue().toString()));
        XSDComponent minInclusive = new MinInclusiveFacet(new XSDComponentAttribute(XSDTagAttribute.VALUE, at.getAbsoluteMinValue().toString()));
        XSDComponent fractionDigits = new FractionDigitsFacet(new XSDComponentAttribute(XSDTagAttribute.VALUE, at.getNumDecimals().toString()));
        XSDComponent maxLength = new MaxLengthFacet(new XSDComponentAttribute(XSDTagAttribute.VALUE, at.getFieldLength().toString()));
        if(at.getUnit() != null){
            XSDComponent unitDocumentation = new Documentation("Enhet: " + at.getUnit());
            restriction.addChildComponent(unitDocumentation,restriction);
        }

        SimpleType simpleType = new SimpleType(restriction);
        simpleType.addChildComponent(minInclusive,restriction);
        simpleType.addChildComponent(maxInclusive,restriction);
        simpleType.addChildComponent(fractionDigits,restriction);
        simpleType.addChildComponent(maxLength,restriction);

        XSDComponent attributeDocumentation = new Documentation(at.getDescription());

        simpleType.addChildComponent(attributeDocumentation, simpleType);
        return new Element(new XSDComponentAttribute(XSDTagAttribute.NAME, toCamelCase(at.getName())), simpleType);
    }

    private String toCamelCase(String text){
        String[] words = text.split("[^\\p{L}]");
        if(words.length == 0)
            return "";
        String camelCaseName = "";
        camelCaseName += words[0].substring(0,1).toLowerCase() + words[0].substring(1);
        for(int i = 1; i < words.length; i++){
            if(words[i].isEmpty())
                continue;
            camelCaseName += words[i].substring(0,1).toUpperCase() + words[i].substring(1);
        }
        return camelCaseName;
    }
}
