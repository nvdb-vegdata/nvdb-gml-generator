package XsdParser.XSD.Component.Restriction;

import XsdParser.XSD.*;
import XsdParser.XSD.Component.AllowedAttributesContainer;
import XsdParser.XSD.Component.Element;
import XsdParser.XSD.Component.SimpleType.SimpleType;
import XsdParser.XSD.Component.XSDComponent;

import java.util.ArrayList;
import java.util.Optional;

public class Restriction extends XSDComponent {
    private final int  EXTRA_RESTRICTION_DEPTH = 2;

    public Restriction(XSDDataType dataType, Namespace namespace){
        super(new XSDComponentAttribute(XSDTagAttribute.BASE, dataType.toStringWithPrefix(namespace)), namespace);
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

    @Override
    public Optional<XSDComponent> getParentComponent(){
        return super.getParentComponent().get().getParentComponent().get().getParentComponent();
    }

    @Override
    public int getDepth(){
        return super.getDepth() + EXTRA_RESTRICTION_DEPTH;
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
