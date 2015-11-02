package XsdParser.XSD.Component;

import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

/**
 * Created by magopl on 22.09.2015.
 */
public class ComplexType extends XSDComponent {

    public ComplexType(ArrayList<XSDComponentAttribute> xsdComponentAttributes) {
        super(xsdComponentAttributes);
    }

    public ComplexType(XSDComponentAttribute xsdComponentAttribute) {
        super(xsdComponentAttribute);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return null;
    }

    @Override
    protected AllowedAttributesContainer setLegalAttributes() {
        return null;
    }

}
