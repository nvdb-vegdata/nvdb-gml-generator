package XsdParser.XSD.Component.Restriction;

import XsdParser.XSD.Component.AllowedAttributesContainer;
import XsdParser.XSD.Component.Element;
import XsdParser.XSD.Component.SimpleType.SimpleType;
import XsdParser.XSD.Component.XSDComponent;
import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;
import XsdParser.XSD.XSDTagAttribute;

import java.util.ArrayList;

public class Restriction extends XSDComponent {

    public Restriction(String type, Namespace namespace){
        super(new XSDComponentAttribute(XSDTagAttribute.BASE,type), namespace);
    }

    public Restriction(ArrayList<XSDComponentAttribute> xsdComponentAttributes, Namespace nameSpace) {
        super(xsdComponentAttributes, nameSpace);
    }

    public Restriction(XSDComponentAttribute xsdComponentAttribute, Namespace nameSpace) {
        super(xsdComponentAttribute, nameSpace);
    }

    public Restriction(Namespace nameSpace) {
        super(nameSpace);
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.RESTRICTION;
    }

    @Override
    protected AllowedAttributesContainer setLegalAttributes() {
        AllowedAttributesContainer legalAttributesContainer = new AllowedAttributesContainer();
        legalAttributesContainer.addLegalAttribute(XSDTagAttribute.ID);
        legalAttributesContainer.addLegalAttribute(XSDTagAttribute.BASE);
        return legalAttributesContainer;
    }

    //TODO DO this another way, jesus
    @Override
    public XSDComponent getParentComponent(){
        if(super.getParentComponent() instanceof SimpleType){

            if (super.getParentComponent().getParentComponent() instanceof Element){
                return super.getParentComponent().getParentComponent().getParentComponent();
            }else {
                return super.getParentComponent().getParentComponent();
            }
        }
        return super.getParentComponent();
    }

    @Override
    public String unLoad() {
        String[] res = {""};
        res[0] += getStartTag();
        getChildComponents().forEach(c -> res[0] += c.unLoad());
        res[0] += getEndTag();
        return res[0];
    }
}
