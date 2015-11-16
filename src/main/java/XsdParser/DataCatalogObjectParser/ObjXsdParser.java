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
import XsdParser.XSD.XSDTagAttribute;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.*;
import no.svv.nvdb.api.inn.domain.datacatalog.constraint.EnumStringAttribute;

import java.security.InvalidParameterException;
import java.util.Optional;

public class ObjXsdParser implements ObjectParser {
    private Schema schema;

    @Override
    public Schema createSchemaTag() {
        Schema schema = new Schema(new XSDComponentAttribute(XSDTagAttribute.XMLNS,"http://www.w3.org/2001/XMLSchema"));
        schema.addNamespace(new Namespace(XSDTagAttribute.XMLNS,"http://www.opengis.net/gml/3.2", "gml"));
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
        } else if(object instanceof TimeAttributeType){
            component = createTimeAttribute((TimeAttributeType)object);
        } else if(object instanceof DateAttributeType){
            component = createDateAttribute((DateAttributeType)object);
        } else if(object instanceof ShortDateAttributeType){
            component = createShortDateAttribute((ShortDateAttributeType)object);
        } else if(object instanceof BoolAttributeType) {
            component = createBooleanAttribute((BoolAttributeType)object);
        }
        return Optional.ofNullable(component);
    }

    private XSDComponent createBooleanAttribute(BoolAttributeType at){
        Restriction restriction = new Restriction("boolean");
        SimpleType simpleType = new SimpleType(restriction);
        return createElement(at,simpleType);
    }

    private XSDComponent createTimeAttribute(TimeAttributeType at){
        Restriction restriction = new Restriction("string");
        SimpleType simpleType = new SimpleType(restriction);

        Optional<String> pattern = getRegexFromTimeFormat(at.getFormat());
        if(!pattern.isPresent()){
            throw new InvalidParameterException("[" + this.getClass().getSimpleName() + "] " + at.getFormat() + " timeFormat not supported");
        }
        XSDComponent patternFacet = new PatternFacet(pattern.get());
        simpleType.addChildComponent(patternFacet, simpleType);

        return createElement(at, simpleType);
    }

    private XSDComponent createDateAttribute(DateAttributeType at){
        Restriction restriction = new Restriction("string");
        SimpleType simpleType = new SimpleType(restriction);

        Optional<String> regexPattern = getRegexFromDateFormat(at.getFormat());
        if(!regexPattern.isPresent()){
            throw new InvalidParameterException(at.getFormat() + " dateFormat not supported");
        }

        XSDComponent patternFacet = new PatternFacet(regexPattern.get());
        simpleType.addChildComponent(patternFacet, simpleType);
        return createElement(at, simpleType);
    }

    private XSDComponent createShortDateAttribute(ShortDateAttributeType at){
        Restriction restriction = new Restriction("string");
        SimpleType simpleType = new SimpleType(restriction);

        Optional<String> regexPattern = getRegexFromDateFormat(at.getFormat());
        if(!regexPattern.isPresent()){
            throw new InvalidParameterException(at.getFormat() + " dateFormat not supported");
        }

        XSDComponent patternFacet = new PatternFacet(regexPattern.get());
        simpleType.addChildComponent(patternFacet, simpleType);

        return createElement(at, simpleType);
    }





    private XSDComponent createEnumStringAttribute(EnumStringAttribute enumAttribute){
        return new EnumerationFacet(new XSDComponentAttribute(XSDTagAttribute.VALUE, enumAttribute.getValue()));
    }

    private XSDComponent createStringAttribute(StringAttributeType at){
        Restriction restriction = new Restriction(at.getType().toString().toLowerCase());
        SimpleType simpleType = new SimpleType(restriction);
        return createElement(at, simpleType);
    }
    private XSDComponent createIntegerAttribute(IntegerAttributeType at){
        Restriction restriction = new Restriction("integer");
        XSDComponent maxInclusive = new MaxInclusiveFacet( at.getAbsoluteMaxValue().toString());
        XSDComponent minInclusive = new MinInclusiveFacet(at.getAbsoluteMinValue().toString());

        if(at.getUnit() != null){
            Documentation unitDocumentation = new Documentation("Enhet: " + at.getUnit());
            Annotation annotation = new Annotation();
            annotation.addChildComponent(unitDocumentation, annotation);
            restriction.addChildComponent(annotation,restriction);
        }
        SimpleType simpleType = new SimpleType(restriction);
        simpleType.addChildComponent(minInclusive,restriction);
        simpleType.addChildComponent(maxInclusive,restriction);

        return createElement(at, simpleType);
    }

    private XSDComponent createRealAttribute(RealAttributeType at){
        Restriction restriction = new Restriction("decimal");

        if( at.getAbsoluteMaxValue() != null && at.getAbsoluteMinValue() != null){
            XSDComponent maxInclusive = new MaxInclusiveFacet(at.getAbsoluteMaxValue().toString());
            XSDComponent minInclusive = new MinInclusiveFacet(at.getAbsoluteMinValue().toString());
            restriction.addChildComponent(minInclusive,restriction);
            restriction.addChildComponent(maxInclusive,restriction);
        }

        if(at.getNumDecimals() != null){
            XSDComponent fractionDigits = new FractionDigitsFacet(at.getNumDecimals().toString());
            restriction.addChildComponent(fractionDigits,restriction);
        }


        if(at.getUnit() != null ){
            Documentation unitDocumentation = new Documentation("Enhet: " + at.getUnit());
            Annotation annotation = new Annotation();
            annotation.addChildComponent(unitDocumentation,annotation);
            restriction.addChildComponent(annotation,restriction);
        }

        SimpleType simpleType = new SimpleType(restriction);


        return createElement(at, simpleType);
    }

    private Annotation createGeneralAnnotation(AttributeType at) {
        Annotation annotation = new Annotation();
        Documentation documentation = new Documentation(at.getDescription());
        annotation.addChildComponent(documentation,annotation);
        AppInfo appinfo = new AppInfo(at.getSosiName() + " : " + at.getId());
        annotation.addChildComponent(appinfo, annotation);
        return  annotation;

    }


    private Element createElement(AttributeType at, XSDComponent component){
        Annotation annotation = createGeneralAnnotation(at);
        String elementName = XSDStringFormatter.createElementName(at.getName());
        XSDComponentAttribute nameAttribute = new XSDComponentAttribute(XSDTagAttribute.NAME,elementName);
        Element element = new Element(nameAttribute, component);
        element.addChildComponent(annotation, element);
        if(isDeprecated(at.getName())){
            element.setDeprecated(true);
        }
        return element;
    }

    private boolean isDeprecated(String name){
        return name.toLowerCase().contains("utgår");
    }

    private Optional<String> getRegexFromTimeFormat(String timeFormat){
        String regex = null;
        switch (timeFormat){
            case "hhmm":
                regex = "^([01][0-9]|2[0-3])[0-5][0-9]$";
                break;
            case "hh:mm":
                regex = "^([01][0-9]|2[0-3]):[0-5][0-9]$";
                break;
            case "hh:mm:ss":
                regex = "^([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
                break;
        }
        return Optional.ofNullable(regex);
    }

    private Optional<String> getRegexFromDateFormat(String dateFormat){
        String regex = null;

        switch (dateFormat){
            case "åååå-mm-dd":
                regex = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
                break;
            case "ååååmmdd":
                regex = "^[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$";
                break;
            case "mm-dd":
                regex = "^(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
                break;
            case "mmdd":
                regex = "^(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$";
                break;
        }
        return Optional.ofNullable(regex);
    }

}
