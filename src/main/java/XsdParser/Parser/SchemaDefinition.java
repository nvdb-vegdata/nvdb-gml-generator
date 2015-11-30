package XsdParser.Parser;

import XsdParser.XSD.Component.*;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTagAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SchemaDefinition {
    private Schema schema;
    private XSDComponent currentXSDComponent;

    public void addSchemaTag(Schema schema){
        this.schema = schema;
        ComplexType complexType = new ComplexType(schema.getNamespace().get());
        Sequence sequence = new Sequence(schema.getNamespace().get());
        complexType.addChildComponent(sequence, complexType);
        Element mainElement = new Element(schema.getMainElementName(),complexType,schema.getNamespace().get());
        schema.addChildComponent(mainElement,schema);
        setCurrentXSDComponent(sequence);
    }

    public void addComponent(XSDComponent xsdComponent){
        currentXSDComponent.addChildComponent(xsdComponent, currentXSDComponent);
        setCurrentXSDComponent(xsdComponent);
    }

    public void exitToParentComponent(){
        if(currentXSDComponent.getParentComponent().isPresent()){
            setCurrentXSDComponent(currentXSDComponent.getParentComponent().get());
        }
    }

    private void setCurrentXSDComponent(XSDComponent xsdComponent){
        currentXSDComponent = xsdComponent;
    }

    //Test method
    public String unLoad(){
        return schema.unLoad();
    }
}
