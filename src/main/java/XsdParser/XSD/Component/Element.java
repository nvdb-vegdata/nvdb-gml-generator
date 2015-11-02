package XsdParser.XSD.Component;

import XsdParser.XSD.Component.Restriction.Facet.RestrictionFacet;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;
import XsdParser.XSD.XSDTagAttribute;

import java.util.ArrayList;

public class Element extends XSDComponent {

    private XSDComponent component;


    private void setComponentParent(){
        component.setParentComponent(this);
    }

    public Element(XSDComponent component) {
        this.component = component;
        setComponentParent();
    }
    public Element(XSDComponentAttribute xsdComponentAttribute) {
        super(xsdComponentAttribute);
    }

    public Element(ArrayList<XSDComponentAttribute> xsdComponentAttributes) {
        super(xsdComponentAttributes);
    }

    public Element(XSDComponentAttribute xsdComponentAttribute, XSDComponent component) {
        super(xsdComponentAttribute);
        this.component = component;
        setComponentParent();
    }

    public Element(ArrayList<XSDComponentAttribute> xsdComponentAttributes, XSDComponent component) {
        super(xsdComponentAttributes);
        this.component = component;
        setComponentParent();
    }

    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.ELEMENT;
    }

    @Override
    protected AllowedAttributesContainer setLegalAttributes() {
        AllowedAttributesContainer legalAttributesContainer = new AllowedAttributesContainer();
        legalAttributesContainer.addLegalAttribute(XSDTagAttribute.ID);
        legalAttributesContainer.addLegalAttribute(XSDTagAttribute.NAME);
        legalAttributesContainer.addLegalAttribute(XSDTagAttribute.BASE);
        return legalAttributesContainer;
    }

    @Override
    public String unLoad() {
        String[] res = {""};
        res[0] += getStartTag();
        if (hasComponent()){
            res[0] += component.unLoad();
        }
        getChildComponents().forEach(child -> res[0] += child.unLoad());
        res[0] += getEndTag();
        return res[0];
    }

    @Override
    public void addChildComponent(XSDComponent child, XSDComponent parentComponent){
        if(child instanceof RestrictionFacet){
            component.addChildComponent(child, component);
        } else {
            super.addChildComponent(child,parentComponent);
        }
    }
    private boolean hasComponent(){
        return component != null;
    }
}
