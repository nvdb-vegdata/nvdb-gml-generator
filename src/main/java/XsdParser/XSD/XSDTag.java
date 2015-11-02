package XsdParser.XSD;

/**
 * Created by magopl on 21.09.2015.
 */
public enum XSDTag{
    SCHEMA("schema"),
    IMPORT("import"),
    ANNOTATION("annotation"),
    DOCUMENTATION("documentation"),
    SIMPLETYPE("simpleType"),
    COMPLEXTYPE("complexType"),
    ELEMENT("element"),
    ENUMERATION("enumeration"),
    RESTRICTION("restriction"),
    UNION("union"),
    FRACTIONDIGITS("fractionDigits"),
    LENGTH("length"),
    MINLENGTH("minLength"),
    MAXLENGTH("maxLength"),
    MAXEXCLUSIVE("maxExclusive"),
    MINEXCLUSIVE("minExclusive"),
    MAXINCLUSIVE("maxInclusive"),
    MININCLUSIVE("minInclusive"),
    PATTERN("pattern"),
    TOTALDIGITS("totalDigits"),
    WHITESPACE("whiteSpace"),
    XSD("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

    private final String text;

    XSDTag(final String text){
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
