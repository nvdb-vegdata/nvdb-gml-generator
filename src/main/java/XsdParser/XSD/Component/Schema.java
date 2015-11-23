package XsdParser.XSD.Component;

import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;
import XsdParser.XSD.XSDTagAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Schema extends XSDComponent {
    private String xmlDocumentTag = XSDTag.XSD.toString();
    protected XSDTag setInitialXsdTag() {
        return XSDTag.SCHEMA;
    }
    private Map<String, Namespace> namespaces = new HashMap<>();
    private String mainElementName;


    public Schema(String mainElementName,Namespace namespace) {
        super(namespace);
        this.mainElementName = mainElementName;
        namespaces.put(namespace.getName(), namespace);
        addXSDComponentAttribute(namespace);
    }

    public Schema(XSDComponentAttribute xsdComponentAttribute, Namespace namespace) {
        super(xsdComponentAttribute, namespace);
        namespaces.put(namespace.getName(), namespace);
        addXSDComponentAttribute(namespace);
    }

    public void addNamespace(Namespace namespace){
        namespaces.put(namespace.getName(), namespace);
        addXSDComponentAttribute(namespace);
    }


    public Optional<Namespace> getNamespaceWithName(String name){
        return Optional.of(namespaces.get(name));
    }

    @Override
    protected AllowedAttributesContainer setLegalAttributes() {
        AllowedAttributesContainer attributesContainer = new AllowedAttributesContainer();
        attributesContainer.addLegalAttribute(XSDTagAttribute.XMLNS);
        attributesContainer.addLegalAttribute(XSDTagAttribute.ELEMENTFORMDEFAULT);
        attributesContainer.addLegalAttribute(XSDTagAttribute.TARGETNAMESPACE);
        attributesContainer.addLegalAttribute(XSDTagAttribute.VERSION);
        return attributesContainer;
    }

    @Override
    public String unLoad() {
        String[] res = {""};
        res[0] += xmlDocumentTag;
        res[0] += getStartTag();
        getChildComponents().forEach(child -> res[0] += child.unLoad());
        res[0] += getEndTag();
        return res[0];
    }

    public String getMainElementName() {
        return mainElementName;
    }
}
