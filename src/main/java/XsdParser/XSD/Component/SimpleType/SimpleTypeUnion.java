package XsdParser.XSD.Component.SimpleType;

import XsdParser.XSD.Component.AllowedAttributesContainer;
import XsdParser.XSD.Component.XSDComponent;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

/**
 * Created by magopl on 28.09.2015.
 */
class SimpleTypeUnion extends XSDComponent {

    public SimpleTypeUnion(ArrayList<XSDComponentAttribute> XSDComponentAttributes) {
        super(XSDComponentAttributes);
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
