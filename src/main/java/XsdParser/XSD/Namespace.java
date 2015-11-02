package XsdParser.XSD;

public class Namespace extends XSDComponentAttribute{
    private String prefix;

    public Namespace(XSDTagAttribute xsdTagAttribute, String URI, String prefix) {
        super(xsdTagAttribute, URI);
        this.prefix = prefix;
    }

    @Override
    public String toString(){
        return " " + getXsdTagAttribute().toString()+":" + prefix + "=" + "\"" + getValue() + "\"";
    }

    public String getPrefix() {
        return prefix;
    }
}
