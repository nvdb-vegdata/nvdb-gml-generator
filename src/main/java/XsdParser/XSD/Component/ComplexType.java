package XsdParser.XSD.Component;

import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;
import com.sun.org.apache.xml.internal.utils.NameSpace;

import javax.lang.model.element.Name;
import java.util.ArrayList;

/**
 * Created by magopl on 22.09.2015.
 */
public class ComplexType extends XSDComponent {

    public ComplexType(ArrayList<XSDComponentAttribute> xsdComponentAttributes, Namespace nameSpace) {
        super(xsdComponentAttributes, nameSpace);
    }

    public ComplexType(XSDComponentAttribute xsdComponentAttribute, Namespace nameSpace) {
        super(xsdComponentAttribute, nameSpace);
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
