package XsdParser.XSD.Component.Annotation;

import XsdParser.XSD.Component.AllowedAttributesContainer;
import XsdParser.XSD.Component.XSDComponent;
import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;

public class Documentation extends XSDComponent {
    private String text;

    public Documentation(ArrayList<XSDComponentAttribute> xsdComponentAttributes, Namespace nameSpace) {
        super(xsdComponentAttributes, nameSpace);
    }

    public Documentation(XSDComponentAttribute xsdComponentAttribute, Namespace nameSpace) {
        super(xsdComponentAttribute, nameSpace);
    }

    public Documentation(String text, Namespace nameSpace) {
        super(nameSpace);
        this.text = text;
    }




    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.DOCUMENTATION;
    }

    @Override
    protected AllowedAttributesContainer setLegalAttributes() {
        return null;
    }

    @Override
    public String unLoad() {
        String res = "";
        res += getStartTag() + addTab() + "\t" + text + "\n" + getEndTag();
        return res;
    }
}
