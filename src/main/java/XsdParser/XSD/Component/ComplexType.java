package XsdParser.XSD.Component;

import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;
import XsdParser.XSD.XSDTagAttribute;
import com.sun.org.apache.xml.internal.utils.NameSpace;

import javax.lang.model.element.Name;
import java.util.ArrayList;

/**
 * Created by magopl on 22.09.2015.
 */
public class ComplexType extends XSDComponent {

    public ComplexType(ArrayList<XSDComponentAttribute> xsdComponentAttributes, Namespace nameSpace) {
        super(xsdComponentAttributes, nameSpace);
    }

    public ComplexType(XSDComponentAttribute xsdComponentAttribute, Namespace nameSpace) {
        super(xsdComponentAttribute, nameSpace);
    }

    public ComplexType(Namespace namespace) {
        super(namespace);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.COMPLEXTYPE;
    }

    @Override
    protected AllowedAttributesContainer setLegalAttributes() {
        AllowedAttributesContainer allowedAttributesContainer = new AllowedAttributesContainer();
        allowedAttributesContainer.addLegalAttribute(XSDTagAttribute.ID);
        allowedAttributesContainer.addLegalAttribute(XSDTagAttribute.NAME);
        allowedAttributesContainer.addLegalAttribute(XSDTagAttribute.ABSTRACT);
        allowedAttributesContainer.addLegalAttribute(XSDTagAttribute.MIXED);
        allowedAttributesContainer.addLegalAttribute(XSDTagAttribute.BLOCK);
        allowedAttributesContainer.addLegalAttribute(XSDTagAttribute.FINAL);
        return allowedAttributesContainer;
    }

}
