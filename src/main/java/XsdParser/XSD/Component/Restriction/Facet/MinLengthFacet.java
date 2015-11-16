package XsdParser.XSD.Component.Restriction.Facet;

import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

/**
 * Created by magopl on 28.09.2015.
 */
public class MinLengthFacet extends RestrictionFacet {

    public MinLengthFacet(ArrayList<XSDComponentAttribute> xsdComponentAttributes, Namespace nameSpace) {
        super(xsdComponentAttributes, nameSpace);
    }

    public MinLengthFacet(XSDComponentAttribute xsdComponentAttribute, Namespace nameSpace) {
        super(xsdComponentAttribute, nameSpace);
    }

    public MinLengthFacet(Namespace nameSpace) {
        super(nameSpace);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.MINLENGTH;
    }

}
