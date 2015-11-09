package XsdParser.XSD.Component.Restriction.Facet;

import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

/**
 * Created by magopl on 28.09.2015.
 */
public class MaxExclusiveFacet extends RestrictionFacet {
    public MaxExclusiveFacet(ArrayList<XSDComponentAttribute> xsdComponentAttributes) {
        super(xsdComponentAttributes);
    }

    public MaxExclusiveFacet(XSDComponentAttribute xsdComponentAttribute) {
        super(xsdComponentAttribute);
    }

    public MaxExclusiveFacet(String value) {
        super(value);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.MAXEXCLUSIVE;
    }

}
