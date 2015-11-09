package XsdParser.Parser;

import XsdParser.XSD.Component.Schema;
import XsdParser.XSD.Component.XSDComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SchemaDefinition {
    private Schema schema;
    private XSDComponent currentXSDComponent;
    public void addSchemaTag(Schema schema){
        this.schema = schema;
        currentXSDComponent = schema;
    }

    public void addComponent(XSDComponent xsdComponent){
        currentXSDComponent.addChildComponent(xsdComponent, currentXSDComponent);
        currentXSDComponent = xsdComponent;
    }

    public void exitToParentComponent(){
        currentXSDComponent = currentXSDComponent.getParentComponent();
    }

    /**
     * Method to remove element components flagged with "deprecated" from the schema definition
     */
    public void clean(){
        schema.removeDeprecatedElements();
    }

    //Test method
    public String unLoad(){
        return schema.unLoad();
    }
}
