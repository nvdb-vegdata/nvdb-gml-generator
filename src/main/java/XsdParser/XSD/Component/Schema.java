package XsdParser.XSD.Component;

import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;
import XsdParser.XSD.XSDTagAttribute;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Schema extends XSDComponent {
    private String xmlDocumentTag = XSDTag.XSD.toString();
    protected XSDTag setInitialXsdTag() {
        return XSDTag.SCHEMA;
    }
    private Map<String, Namespace> namespaces = new HashMap<>();


    public Schema(XSDComponentAttribute xsdComponentAttribute) {
        super(xsdComponentAttribute);
    }

    public void addNamespace(Namespace namespace){
        namespaces.put(namespace.getPrefix(), namespace);
        addXSDComponentAttribute(namespace);
    }


    public Optional<Namespace> getNamespace(int prefix){
        return Optional.of(namespaces.get(prefix));
    }

    @Override
    protected AllowedAttributesContainer setLegalAttributes() {
        AllowedAttributesContainer attributesContainer = new AllowedAttributesContainer();
        attributesContainer.addLegalAttribute(XSDTagAttribute.XLMNS);
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
}
