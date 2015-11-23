package XsdParser.XSD.Component;

import XsdParser.XSD.Component.Restriction.Restriction;
import XsdParser.XSD.Namespace;
import XsdParser.XSD.XSDComponentAttribute;
import XsdParser.XSD.XSDTag;
import XsdParser.XSD.Namespace;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class XSDComponent {
    private final XSDTag xsdTag;
    private final ArrayList<XSDComponentAttribute> xsdComponentAttributes;
    private AllowedAttributesContainer legalAttributesContainer = new AllowedAttributesContainer();
    private final char starter = '<';
    private final char ender = '>';
    private ArrayList<XSDComponent> childComponents;
    private XSDComponent parentComponent;
    private Namespace namespace;
    private boolean deprecated = false;



    //region Constructor
    public XSDComponent(ArrayList<XSDComponentAttribute> xsdComponentAttributes, Namespace namespace) {
        this.namespace = namespace;
        legalAttributesContainer = setLegalAttributes();
        this.xsdTag = setInitialXsdTag();
        this.xsdComponentAttributes = xsdComponentAttributes;
        childComponents = new ArrayList<>();
        checkForLegalComponentAttributes();
    }

    public XSDComponent(XSDComponentAttribute xsdComponentAttribute, Namespace namespace){
        this.namespace = namespace;
        legalAttributesContainer = setLegalAttributes();
        this.xsdTag = setInitialXsdTag();
        this.xsdComponentAttributes = new ArrayList<>();
        xsdComponentAttributes.add(xsdComponentAttribute);
        childComponents = new ArrayList<>();
        checkForLegalComponentAttributes();
    }

    public XSDComponent(Namespace namespace) {
        this.namespace = namespace;
        legalAttributesContainer = setLegalAttributes();
        this.xsdTag = setInitialXsdTag();
        this.xsdComponentAttributes = new ArrayList<>();
        childComponents = new ArrayList<>();
        checkForLegalComponentAttributes();
    }
    //endregion

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    public int getDepth(){
        if(!getParentComponent().isPresent())return 0;
       return getParentComponent().get().getDepth() + 1;
    }


    public Optional<Namespace> getNamespace() {
        return Optional.of(namespace);
    }

    public void setNamespace(Namespace namespace) {
        this.namespace = namespace;
    }


    public void addXSDComponentAttribute(XSDComponentAttribute xsdComponentAttribute){
        xsdComponentAttributes.add(xsdComponentAttribute);
    }

    protected abstract XSDTag setInitialXsdTag();

    protected abstract AllowedAttributesContainer setLegalAttributes();

    public String unLoad() {
        String[] res = {""};
        res[0] += getStartTag();
        getChildComponents().forEach(child -> {
            if(!child.isDeprecated()) res[0] += child.unLoad();
        });
        res[0] += getEndTag();
        return res[0];
    }

    public String addTab(){
        int depth = getDepth();
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

    public Optional<XSDComponent> getParentComponent() {
        return Optional.ofNullable(parentComponent);
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

    public void setChildComponents(ArrayList<XSDComponent> childComponents){
        this.childComponents = childComponents;
    }

    public XSDTag getXsdTag() {
        return xsdTag;
    }

    public String getStartTag(){
        final String[] startTag = { addTab() + starter + getPrefixString() + xsdTag.toString()};
        if(xsdComponentAttributes == null || xsdComponentAttributes.isEmpty()){
            return startTag[0] + ender +"\n";
        }
        xsdComponentAttributes.forEach(tagAttribute -> startTag[0] += tagAttribute);
        return startTag[0] + ender +"\n";
    }

    private String getPrefixString(){
        if(namespace.getPrefix().isPresent()){
            return namespace.getPrefix().get() + ":";
        }
        return "";
    }
    public String getEndTag( ){
        return addTab() + starter +"/"+ getPrefixString() + xsdTag.toString() + ender +"\n";
    }

}
