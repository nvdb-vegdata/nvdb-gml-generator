package XsdParser.XSD.Component.Annotation;

import XsdParser.XSD.Component.AllowedAttributesContainer;
import XsdParser.XSD.Component.XSDComponent;
import XsdParser.XSD.XSDTag;
import XsdParser.XSD.XSDTagAttribute;

public class AppInfo extends XSDComponent {
    private String value;


    public AppInfo(String value) {
        this.value = value;
    }

    @Override
    public String unLoad(){
        return getStartTag() + addTab() + "\t" + value + "\n" + getEndTag();
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.APPINFO;
    }

    @Override
    protected AllowedAttributesContainer setLegalAttributes() {
        AllowedAttributesContainer allowedAttributesContainer = new AllowedAttributesContainer();
        allowedAttributesContainer.addLegalAttribute(XSDTagAttribute.SOURCE);
        return allowedAttributesContainer;
    }
}
