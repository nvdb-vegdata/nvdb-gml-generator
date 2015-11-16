package XsdParser.XSD.Component.Restriction.Facet;

import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

/**
 * Created by magopl on 28.09.2015.
 */
public class WhiteSpaceFacet extends RestrictionFacet{

    public WhiteSpaceFacet(ArrayList<XSDComponentAttribute> xsdComponentAttributes, Namespace nameSpace) {
        super(xsdComponentAttributes, nameSpace);
    }

    public WhiteSpaceFacet(XSDComponentAttribute xsdComponentAttribute, Namespace nameSpace) {
        super(xsdComponentAttribute, nameSpace);
    }

    public WhiteSpaceFacet(Namespace nameSpace) {
        super(nameSpace);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.WHITESPACE;
    }
}
