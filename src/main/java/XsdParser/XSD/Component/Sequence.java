package XsdParser.XSD.Component;

import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;
import XsdParser.XSD.XSDTagAttribute;

import java.util.ArrayList;

/**
 * Created by magopl on 23.11.2015.
 */
public class Sequence extends XSDComponent {

    public Sequence(ArrayList<XSDComponentAttribute> xsdComponentAttributes, Namespace namespace) {
        super(xsdComponentAttributes, namespace);
    }

    public Sequence(XSDComponentAttribute xsdComponentAttribute, Namespace namespace) {
        super(xsdComponentAttribute, namespace);
    }

    public Sequence(Namespace namespace) {
        super(namespace);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.SEQUENCE;
    }

    @Override
    protected AllowedAttributesContainer setLegalAttributes() {
        AllowedAttributesContainer allowedAttributesContainer = new AllowedAttributesContainer();
        allowedAttributesContainer.addLegalAttribute(XSDTagAttribute.ID);
        allowedAttributesContainer.addLegalAttribute(XSDTagAttribute.MINOCCURS);
        allowedAttributesContainer.addLegalAttribute(XSDTagAttribute.MAXOCCURS);
        return allowedAttributesContainer;
    }
}
