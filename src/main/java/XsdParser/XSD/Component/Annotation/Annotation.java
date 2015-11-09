package XsdParser.XSD.Component.Annotation;

import XsdParser.XSD.Component.AllowedAttributesContainer;
import XsdParser.XSD.Component.XSDComponent;
import XsdParser.XSD.XSDTag;

public class Annotation extends XSDComponent {


    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.ANNOTATION;
    }

    @Override
    protected AllowedAttributesContainer setLegalAttributes() {
        return null;
    }
}
