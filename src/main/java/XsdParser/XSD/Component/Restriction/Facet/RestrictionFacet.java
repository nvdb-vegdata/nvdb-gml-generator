package XsdParser.XSD.Component.Restriction.Facet;

import XsdParser.XSD.Component.AllowedAttributesContainer;
import XsdParser.XSD.Component.XSDComponent;
import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;
import XsdParser.XSD.XSDTagAttribute;

import java.util.ArrayList;

/**
 * Created by magopl on 28.09.2015.
 */
public class RestrictionFacet extends XSDComponent {
    public RestrictionFacet(ArrayList<XSDComponentAttribute> xsdComponentAttributes, Namespace nameSpace) {
        super(xsdComponentAttributes, nameSpace);
    }

    public RestrictionFacet(XSDComponentAttribute xsdComponentAttribute, Namespace nameSpace) {
        super(xsdComponentAttribute, nameSpace);
    }

    public RestrictionFacet(Namespace nameSpace) {
        super(nameSpace);
    }

    public RestrictionFacet(String value, Namespace namespace) {
        super(new XSDComponentAttribute(XSDTagAttribute.VALUE, value), namespace);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return null;
    }

    @Override
    protected AllowedAttributesContainer setLegalAttributes() {
        AllowedAttributesContainer legalAttributesContainer = new AllowedAttributesContainer();
        legalAttributesContainer.addLegalAttribute(XSDTagAttribute.VALUE);
        return legalAttributesContainer;
    }

    @Override
    public String unLoad() {
        String res = "";
        res += getStartTag() + getEndTag();
        return res;
    }
}
