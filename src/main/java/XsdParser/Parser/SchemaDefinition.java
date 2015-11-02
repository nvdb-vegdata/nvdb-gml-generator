package XsdParser.Parser;

import XsdParser.XSD.Component.Schema;
import XsdParser.XSD.Component.XSDComponent;

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

    //Test method
    public String unLoad(){
        return schema.unLoad();
    }
}
