package XsdParser.XSD;

import java.util.Optional;

public class Namespace extends XSDComponentAttribute{
    private String prefix;
    private String name;

    public Namespace(XSDTagAttribute xsdTagAttribute, String URI, String name, String prefix) {
        super(xsdTagAttribute, URI);
        this.prefix = prefix;
        this.name = name;
    }

    public String getTypeNameWithPrefix(String typeName){
        return prefix + ":" + typeName;
    }

    @Override
    public String toString(){
        return " " + getXsdTagAttribute().toString()+":" + prefix + "=" + "\"" + getValue() + "\"";
    }

    public Optional<String> getPrefix() {
        if(prefix.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(prefix);
    }

    public String getName() {
        return name;
    }
}
