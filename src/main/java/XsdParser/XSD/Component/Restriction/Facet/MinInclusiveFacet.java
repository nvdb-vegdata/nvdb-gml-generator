package XsdParser.XSD.Component.Restriction.Facet;

import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

/**
 * Created by magopl on 28.09.2015.
 */
public class MinInclusiveFacet extends RestrictionFacet {
    public MinInclusiveFacet(ArrayList<XSDComponentAttribute> xsdComponentAttributes) {
        super(xsdComponentAttributes);
    }

    public MinInclusiveFacet(XSDComponentAttribute xsdComponentAttribute) {
        super(xsdComponentAttribute);
    }

    public MinInclusiveFacet(String value) {
        super(value);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.MININCLUSIVE;
    }

}
