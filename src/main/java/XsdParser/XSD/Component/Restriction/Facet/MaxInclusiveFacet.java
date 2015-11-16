package XsdParser.XSD.Component.Restriction.Facet;

import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

/**
 * Created by magopl on 28.09.2015.
 */
public class MaxInclusiveFacet extends RestrictionFacet {

    public MaxInclusiveFacet(ArrayList<XSDComponentAttribute> xsdComponentAttributes, Namespace nameSpace) {
        super(xsdComponentAttributes, nameSpace);
    }

    public MaxInclusiveFacet(XSDComponentAttribute xsdComponentAttribute, Namespace nameSpace) {
        super(xsdComponentAttribute, nameSpace);
    }

    public MaxInclusiveFacet(String value, Namespace nameSpace) {
        super(value, nameSpace);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.MAXINCLUSIVE;
    }
}
