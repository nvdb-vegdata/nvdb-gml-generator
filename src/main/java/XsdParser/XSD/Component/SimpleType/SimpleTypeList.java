package XsdParser.XSD.Component.SimpleType;

import XsdParser.XSD.Component.AllowedAttributesContainer;
import XsdParser.XSD.Component.XSDComponent;
import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

/**
 * Created by magopl on 28.09.2015.
 */
 class SimpleTypeList extends XSDComponent {

    public SimpleTypeList(ArrayList<XSDComponentAttribute> XSDComponentAttributes, Namespace namespace) {
        super(XSDComponentAttributes, namespace);
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
