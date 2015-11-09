package XsdParser.XSD.Component.Restriction.Facet;

import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

/**
 * Created by magopl on 28.09.2015.
 */
public class TotalDigitsFacet extends RestrictionFacet {
    public TotalDigitsFacet(ArrayList<XSDComponentAttribute> xsdComponentAttributes) {
        super(xsdComponentAttributes);
    }

    public TotalDigitsFacet(XSDComponentAttribute xsdComponentAttribute) {
        super(xsdComponentAttribute);
    }

    public TotalDigitsFacet(String value) {
        super(value);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.TOTALDIGITS;
    }
}
