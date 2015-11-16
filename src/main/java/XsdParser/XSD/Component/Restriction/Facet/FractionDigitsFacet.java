package XsdParser.XSD.Component.Restriction.Facet;

import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

/**
 * Created by magopl on 28.09.2015.
 */
public class FractionDigitsFacet extends RestrictionFacet {


    public FractionDigitsFacet(ArrayList<XSDComponentAttribute> xsdComponentAttributes, Namespace nameSpace) {
        super(xsdComponentAttributes, nameSpace);
    }

    public FractionDigitsFacet(XSDComponentAttribute xsdComponentAttribute, Namespace nameSpace) {
        super(xsdComponentAttribute, nameSpace);
    }

    public FractionDigitsFacet(String value, Namespace nameSpace) {
        super(value, nameSpace);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.FRACTIONDIGITS;
    }

}
