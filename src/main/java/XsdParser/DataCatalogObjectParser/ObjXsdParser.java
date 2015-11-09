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
import com.sun.javafx.binding.StringFormatter;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.*;
import no.svv.nvdb.api.inn.domain.datacatalog.constraint.EnumStringAttribute;

import javax.print.Doc;
import java.security.InvalidParameterException;
import java.sql.Time;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Optional;
import java.util.OptionalLong;

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
        }
        return Optional.ofNullable(component);
    }

    private XSDComponent createTimeAttribute(TimeAttributeType at){
        Restriction restriction = new Restriction("string");
        Annotation annotation = createGeneralAnnotation(at);
        SimpleType simpleType = new SimpleType(restriction);

        Optional<String> pattern = getRegexFromTimeFormat(at.getFormat());
        if(!pattern.isPresent()){
            throw new InvalidParameterException(at.getFormat() + " timeformat not supported");
        }
        XSDComponent patternFacet = new PatternFacet(pattern.get());
        simpleType.addChildComponent(patternFacet, simpleType);

        ArrayList<XSDComponent> children = new ArrayList<>();
        children.add(annotation);
        return createElement(at, simpleType, children);
    }



    private XSDComponent createEnumStringAttribute(EnumStringAttribute enumAttribute){
        return new EnumerationFacet(new XSDComponentAttribute(XSDTagAttribute.VALUE, enumAttribute.getValue()));
    }

    private XSDComponent createStringAttribute(StringAttributeType at){
        Restriction restriction = new Restriction(at.getType().toString().toLowerCase());
        SimpleType simpleType = new SimpleType(restriction);
        Annotation annotation =  createGeneralAnnotation(at);

        ArrayList<XSDComponent> children = new ArrayList<>();
        children.add(annotation);
        return createElement(at, simpleType, children);
    }
    private XSDComponent createIntegerAttribute(IntegerAttributeType at){
        Restriction restriction = new Restriction(at.getType().toString().toLowerCase());
        XSDComponent maxInclusive = new MaxInclusiveFacet( at.getAbsoluteMaxValue().toString());
        XSDComponent minInclusive = new MinInclusiveFacet(at.getAbsoluteMinValue().toString());
        XSDComponent maxLength = new MaxLengthFacet(at.getFieldLength().toString());

        if(at.getUnit() != null){
            Documentation unitDocumentation = new Documentation("Enhet: " + at.getUnit());
            Annotation annotation = new Annotation();
            annotation.addChildComponent(unitDocumentation, annotation);
            restriction.addChildComponent(annotation,restriction);
        }
        SimpleType simpleType = new SimpleType(restriction);
        simpleType.addChildComponent(minInclusive,restriction);
        simpleType.addChildComponent(maxInclusive,restriction);
        simpleType.addChildComponent(maxLength,restriction);

        Annotation annotation = createGeneralAnnotation(at);
        ArrayList<XSDComponent> children = new ArrayList<>();
        children.add(annotation);
        return createElement(at, simpleType, children);
    }

    private XSDComponent createRealAttribute(RealAttributeType at){
        Restriction restriction = new Restriction("decimal");
        XSDComponent maxInclusive = new MaxInclusiveFacet(at.getAbsoluteMaxValue().toString());
        XSDComponent minInclusive = new MinInclusiveFacet(at.getAbsoluteMinValue().toString());
        XSDComponent fractionDigits = new FractionDigitsFacet(at.getNumDecimals().toString());
        if(at.getUnit() != null){
            Documentation unitDocumentation = new Documentation("Enhet: " + at.getUnit());
            Annotation annotation = new Annotation();
            annotation.addChildComponent(unitDocumentation,annotation);
            restriction.addChildComponent(annotation,restriction);
        }

        SimpleType simpleType = new SimpleType(restriction);
        restriction.addChildComponent(minInclusive,restriction);
        restriction.addChildComponent(maxInclusive,restriction);
        restriction.addChildComponent(fractionDigits,restriction);

        Annotation annotation = createGeneralAnnotation(at);

        ArrayList<XSDComponent> children = new ArrayList<>();
        children.add(annotation);
        return createElement(at, simpleType, children);
    }

    private Annotation createGeneralAnnotation(AttributeType at) {
        Annotation annotation = new Annotation();
        Documentation documentation = new Documentation(at.getDescription());
        annotation.addChildComponent(documentation,annotation);
        AppInfo appinfo = new AppInfo(at.getSosiName() + " : " + at.getId());
        annotation.addChildComponent(appinfo, annotation);
        return  annotation;

    }

    private Element createElement(AttributeType at, SimpleType simpleType, ArrayList<XSDComponent> childrenComponents){
        if(isDeprecated(at.getName())){
            return null;
        }
        String elementName = XSDStringFormatter.createElementName(at.getName());
        XSDComponentAttribute nameAttribute = new XSDComponentAttribute(XSDTagAttribute.NAME,elementName);
        Element element = new Element(nameAttribute, simpleType);
        childrenComponents.forEach(child -> element.addChildComponent(child, element));
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

}
