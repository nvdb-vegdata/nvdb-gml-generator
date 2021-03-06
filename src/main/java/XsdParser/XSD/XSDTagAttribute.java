package XsdParser.XSD;

/**
 * Created by magopl on 21.09.2015.
 */

public enum XSDTagAttribute {
    ID("id"),
    XMLNS("xmlns"),
    NAMESPACE("namespace"),
    ELEMENTFORMDEFAULT("elementFormDefault"),
    TARGETNAMESPACE("targetNamespace"),
    SCHEMALOCATION("schemalocation"),
    NAME("name"),
    MEMBERTYPES("membertypes"),
    TAG("tag"),
    BASE("base"),
    VALUE("value"),
    VERSION("version"),
    TYPE("type"),
    SUBSTITUTIONGROUP("subtitutiongroup"),
    SOURCE("source"),
    ABSTRACT("abstract"),
    MINOCCURS("minOccurs"),
    MAXOCCURS("maxOccurs"),
    MIXED("mixed"),
    BLOCK("block"),
    FINAL("final"),
    ;

    private final String text;

    XSDTagAttribute(final String text){
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
