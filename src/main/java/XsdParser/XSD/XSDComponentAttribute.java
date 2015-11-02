package XsdParser.XSD;

public class XSDComponentAttribute {
    private final XSDTagAttribute xsdTagAttribute;
    private final String value;

    public XSDComponentAttribute(XSDTagAttribute xsdTagAttribute, String value) {
        this.xsdTagAttribute = xsdTagAttribute;
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public XSDTagAttribute getXsdTagAttribute() {
        return xsdTagAttribute;
    }

    @Override
    public String toString(){
        return " " + xsdTagAttribute.toString() + "=" + "\"" + value + "\"";
    }
}
