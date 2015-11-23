package XsdParser.Parser;

import XsdParser.Iterator.ObjectIterator;
import XsdParser.XSD.Component.XSDComponent;

public class XsdBuilder implements IteratorListener {
    private ObjectParser objectParser;
    private SchemaDefinition schemaDefinition;

    public XsdBuilder(ObjectParser objectParser) {
        this.objectParser = objectParser;
    }

    public SchemaDefinition generateSchemaDefinition(Object object, String name){
        schemaDefinition = new SchemaDefinition();
        schemaDefinition.addSchemaTag(objectParser.createSchemaTag(name));
        ObjectIterator objectIterator = new ObjectIterator(object.getClass().getName(),  this, object);
        objectIterator.iterate();
        return schemaDefinition;
    }

    @Override
    public void entered(Object object) {
        if(objectParser.translate(object).isPresent()){
            XSDComponent xsdComponent = objectParser.translate(object).get();
            schemaDefinition.addComponent(xsdComponent);
        }
    }

    @Override
    public void exited(Object object) {
        if(objectParser.translate(object).isPresent()){
            schemaDefinition.exitToParentComponent();
        }
    }
}
