package XsdParser.XSD.Component;

import XsdParser.XSD.Component.Restriction.Facet.RestrictionFacet;
import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;
import XsdParser.XSD.XSDTagAttribute;
import com.sun.org.apache.xml.internal.utils.NameSpace;

import java.util.ArrayList;

public class Element extends XSDComponent {

    private XSDComponent component;


    private void setComponentParent(){
        component.setParentComponent(this);
    }

    //region Constructor
    public Element(XSDComponent component, Namespace nameSpace) {
        super(nameSpace);
        this.component = component;
        setComponentParent();
    }
    public Element(XSDComponentAttribute xsdComponentAttribute, Namespace nameSpace) {
        super(xsdComponentAttribute, nameSpace);
    }

    public Element(ArrayList<XSDComponentAttribute> xsdComponentAttributes, Namespace nameSpace) {
        super(xsdComponentAttributes, nameSpace);
    }

    public Element(XSDComponentAttribute xsdComponentAttribute, XSDComponent component, Namespace nameSpace) {
        super(xsdComponentAttribute, nameSpace);
        this.component = component;
        setComponentParent();
    }

    public Element(ArrayList<XSDComponentAttribute> xsdComponentAttributes, XSDComponent component, Namespace nameSpace) {
        super(xsdComponentAttributes, nameSpace);
        this.component = component;
        setComponentParent();
    }
    //endregion

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
        legalAttributesContainer.addLegalAttribute(XSDTagAttribute.TYPE);
        return legalAttributesContainer;
    }

    @Override
    public String unLoad() {
        String[] res = {""};
        res[0] += getStartTag();
        getChildComponents().forEach(child -> res[0] += child.unLoad());
        if (hasComponent()){
            res[0] += component.unLoad();
        }
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
