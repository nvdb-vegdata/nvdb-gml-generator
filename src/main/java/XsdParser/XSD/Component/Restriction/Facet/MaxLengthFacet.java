package XsdParser.XSD.Component.Restriction.Facet;

import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

/**
 * Created by magopl on 28.09.2015.
 */
public class MaxLengthFacet extends RestrictionFacet {
    public MaxLengthFacet(ArrayList<XSDComponentAttribute> xsdComponentAttributes) {
        super(xsdComponentAttributes);
    }

    public MaxLengthFacet(XSDComponentAttribute xsdComponentAttribute) {
        super(xsdComponentAttribute);
    }

    public MaxLengthFacet(String value) {
        super(value);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.MAXLENGTH;
    }
}
