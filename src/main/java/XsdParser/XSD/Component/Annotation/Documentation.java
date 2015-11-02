package XsdParser.XSD.Component.Annotation;

import XsdParser.XSD.Component.AllowedAttributesContainer;
import XsdParser.XSD.Component.XSDComponent;
import XsdParser.XSD.XSDTag;

public class Documentation extends XSDComponent {
    private String text;

    public Documentation(String text){
        super();
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
