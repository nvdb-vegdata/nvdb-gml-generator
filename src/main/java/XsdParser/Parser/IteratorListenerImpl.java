package XsdParser.Parser;

import XsdParser.Iterator.ObjectIterator;
import XsdParser.XSD.Component.XSDComponent;

import java.util.Optional;

public class IteratorListenerImpl implements IteratorListener {
    private ObjectParser objectParser;
    private SchemaDefinition schemaDefinition;

    public IteratorListenerImpl(ObjectParser objectParser) {
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
        if(objectParser.translationExistFor(object)){
            Optional<XSDComponent> xsdComponent = objectParser.translate(object);
            schemaDefinition.addComponent(xsdComponent.get());
        }
    }

    @Override
    public void exited(Object object) {
        if(objectParser.translationExistFor(object)){
            schemaDefinition.exitToParentComponent();
        }
    }
}
