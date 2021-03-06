package XsdParser.XSD.Component.SimpleType;

import XsdParser.XSD.Component.AllowedAttributesContainer;
import XsdParser.XSD.Component.Restriction.Facet.RestrictionFacet;
import XsdParser.XSD.Component.Restriction.Restriction;
import XsdParser.XSD.Component.XSDComponent;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;
import XsdParser.XSD.XSDTagAttribute;
import XsdParser.XSD.Namespace;

import java.util.ArrayList;

public class SimpleType extends XSDComponent {
    private XSDComponent component;

    private void setComponentParent(){
        component.setParentComponent(this);
    }

    public SimpleType(XSDComponent component, Namespace nameSpace) {
        super(nameSpace);
        this.component = component;
        setComponentParent();
    }

    //region Constructors
    public SimpleType(XSDComponentAttribute XSDComponentAttribute, Restriction restriction, Namespace namespace) {
        super(XSDComponentAttribute, namespace);
        this.component = restriction;
        setComponentParent();
    }

    public SimpleType(XSDComponentAttribute XSDComponentAttribute, SimpleTypeList simpleTypeList, Namespace namespace) {
        super(XSDComponentAttribute, namespace);
        this.component = simpleTypeList;
        setComponentParent();
    }

    public SimpleType(XSDComponentAttribute XSDComponentAttribute, SimpleTypeUnion simpleTypeUnion, Namespace namespace) {
        super(XSDComponentAttribute, namespace);
        this.component = simpleTypeUnion;
        setComponentParent();
    }
    public SimpleType(ArrayList<XSDComponentAttribute> XSDComponentAttributes, Restriction restriction, Namespace namespace) {
        super(XSDComponentAttributes, namespace);
        this.component = restriction;
        setComponentParent();
    }

    public SimpleType(ArrayList<XSDComponentAttribute> XSDComponentAttributes, SimpleTypeList simpleTypeList, Namespace namespace) {
        super(XSDComponentAttributes, namespace);
        this.component = simpleTypeList;
        setComponentParent();
    }

    public SimpleType(ArrayList<XSDComponentAttribute> XSDComponentAttributes, SimpleTypeUnion simpleTypeUnion, Namespace namespace) {
        super(XSDComponentAttributes, namespace);
        this.component = simpleTypeUnion;
        setComponentParent();
    }
    //endregion

    //region Protected methods for initialization of SimpleType objects
    @Override
    protected XSDTag setInitialXsdTag() {
        return XSDTag.SIMPLETYPE;
    }

    //TODO This functionality should be moved to a more dynamic solution, e.g. retrieving attributes from a xml/text document
    @Override
    protected AllowedAttributesContainer setLegalAttributes() {
        AllowedAttributesContainer legalAttributesContainer = new AllowedAttributesContainer();
        legalAttributesContainer.addLegalAttribute(XSDTagAttribute.ID);
        legalAttributesContainer.addLegalAttribute(XSDTagAttribute.NAME);
        return legalAttributesContainer;
    }
    //endregion

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
