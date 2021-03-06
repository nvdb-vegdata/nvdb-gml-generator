package XsdParser.XSD.Component.Restriction.Facet;

import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

/**
 * Created by magopl on 28.09.2015.
 */
public class TotalDigitsFacet extends RestrictionFacet {


    public TotalDigitsFacet(ArrayList<XSDComponentAttribute> xsdComponentAttributes, Namespace nameSpace) {
        super(xsdComponentAttributes, nameSpace);
    }

    public TotalDigitsFacet(XSDComponentAttribute xsdComponentAttribute, Namespace nameSpace) {
        super(xsdComponentAttribute, nameSpace);
    }

    public TotalDigitsFacet(Namespace nameSpace) {
        super(nameSpace);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.TOTALDIGITS;
    }
}
