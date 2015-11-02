package XsdParser.XSD.Component;

import XsdParser.XSD.Component.Restriction.Restriction;
import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;

import java.util.ArrayList;
import java.util.Optional;

public abstract class XSDComponent {
    private final XSDTag xsdTag;
    private final ArrayList<XSDComponentAttribute> xsdComponentAttributes;
    private AllowedAttributesContainer legalAttributesContainer = new AllowedAttributesContainer();
    private final char starter = '<';
    private final char ender = '>';
    private ArrayList<XSDComponent> childComponents;
    private XSDComponent parentComponent;
    private Namespace namespace;

    private int getDepth(int depth, XSDComponent component){
        if(component.getParentComponent() == null){
            return depth;
        }
        if(component instanceof Restriction){
            return depth + 3;
        }
        return getDepth(depth +1, component.getParentComponent());
    }

    public Optional<Namespace> getNamespace() {
        return Optional.of(namespace);
    }

    public void setNamespace(Namespace namespace) {
        this.namespace = namespace;
    }

    public XSDComponent(ArrayList<XSDComponentAttribute> xsdComponentAttributes) {
        this.xsdTag = setInitialXsdTag();
        this.xsdComponentAttributes = xsdComponentAttributes;
        childComponents = new ArrayList<>();
        checkForLegalComponentAttributes();
        legalAttributesContainer = setLegalAttributes();
    }

    public XSDComponent(XSDComponentAttribute xsdComponentAttribute){
        this.xsdTag = setInitialXsdTag();
        this.xsdComponentAttributes = new ArrayList<>();
        xsdComponentAttributes.add(xsdComponentAttribute);
        childComponents = new ArrayList<>();
        legalAttributesContainer = setLegalAttributes();
        checkForLegalComponentAttributes();
    }

    public XSDComponent() {
        this.xsdTag = setInitialXsdTag();
        this.xsdComponentAttributes = new ArrayList<>();
        childComponents = new ArrayList<>();
        legalAttributesContainer = setLegalAttributes();
        checkForLegalComponentAttributes();
    }
    public void addXSDComponentAttribute(XSDComponentAttribute xsdComponentAttribute){
        xsdComponentAttributes.add(xsdComponentAttribute);
    }

    protected abstract XSDTag setInitialXsdTag();

    protected abstract AllowedAttributesContainer setLegalAttributes();

    public String unLoad() {
        String[] res = {""};
        res[0] += getStartTag();
        getChildComponents().forEach(child -> res[0] += child.unLoad());
        res[0] += addTab() + getEndTag();
        return res[0];
    }

    public String addTab(){
        int depth = getDepth(0, this);
        String tabString = "";
        for(int i = 1; i <= depth; i++){
            tabString += "\t";
        }
        return tabString;
    }

    private void checkForLegalComponentAttributes() throws IllegalArgumentException {
        if(xsdComponentAttributes == null || xsdComponentAttributes.isEmpty()){
            return;
        }
        Optional<XSDComponentAttribute> xsdComponentAttributeOptional = findIllegalAttribute(xsdComponentAttributes);
        if(xsdComponentAttributeOptional.isPresent()) {
            String exceptionText = "XSDTagAttribute for class ["+this.getClass().getSimpleName()+"] must be one of the following:\n";
            exceptionText += legalAttributesContainer.getLegalAttributesString();
            exceptionText += "\n" + "[" + xsdComponentAttributeOptional.get().getXsdTagAttribute() + "] found!";
            throw new IllegalArgumentException(exceptionText);
        }
    }
    private Optional<XSDComponentAttribute> findIllegalAttribute(ArrayList<XSDComponentAttribute> XSDComponentAttributes){
        return XSDComponentAttributes.stream().filter(c -> !legalAttributesContainer.contains(c.getXsdTagAttribute())).findFirst();
    }


    public void setParentComponent(XSDComponent xsdComponent){
        this.parentComponent = xsdComponent;
    }

    public XSDComponent getParentComponent() {
        return parentComponent;
    }

    public void addChildComponent(XSDComponent child, XSDComponent parentComponent){
        child.setParentComponent(parentComponent);
        childComponents.add(child);
    }

    public ArrayList<XSDComponent> getChildComponents() {
        return childComponents;
    }

    public boolean hasChildComponents(){
        return childComponents.size() > 0;
    }

    public boolean hasParent(){
        return parentComponent != null;
    }



    public XSDTag getXsdTag() {
        return xsdTag;
    }

    public String getStartTag(){
        final String[] startTag = { addTab() + starter + xsdTag.toString()};
        if(xsdComponentAttributes == null || xsdComponentAttributes.isEmpty()){
            return startTag[0] + ender +"\n";
        }
        xsdComponentAttributes.forEach(tagAttribute -> startTag[0] += tagAttribute);
        return startTag[0] + ender +"\n";
    }
    public String getEndTag( ){
        return addTab() + starter +"/" + xsdTag.toString() + ender +"\n";
    }

}