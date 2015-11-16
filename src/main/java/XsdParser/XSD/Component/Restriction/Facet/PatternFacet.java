package XsdParser.XSD.Component.Restriction.Facet;

import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

/**
 * Created by magopl on 28.09.2015.
 */
public class PatternFacet extends RestrictionFacet {

    public PatternFacet(ArrayList<XSDComponentAttribute> xsdComponentAttributes, Namespace nameSpace) {
        super(xsdComponentAttributes, nameSpace);
    }

    public PatternFacet(XSDComponentAttribute xsdComponentAttribute, Namespace nameSpace) {
        super(xsdComponentAttribute, nameSpace);
    }

    public PatternFacet(Namespace nameSpace) {
        super(nameSpace);
    }

    public PatternFacet(String value, Namespace namespace) {
        super(value, namespace);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.PATTERN;
    }
}
