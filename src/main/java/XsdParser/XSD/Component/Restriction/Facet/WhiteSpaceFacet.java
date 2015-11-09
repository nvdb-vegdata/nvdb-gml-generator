package XsdParser.XSD.Component.Restriction.Facet;

import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

/**
 * Created by magopl on 28.09.2015.
 */
public class WhiteSpaceFacet extends RestrictionFacet{
    public WhiteSpaceFacet(ArrayList<XSDComponentAttribute> xsdComponentAttributes) {
        super(xsdComponentAttributes);
    }

    public WhiteSpaceFacet(XSDComponentAttribute xsdComponentAttribute) {
        super(xsdComponentAttribute);
    }

    public WhiteSpaceFacet(String value) {
        super(value);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.WHITESPACE;
    }
}
