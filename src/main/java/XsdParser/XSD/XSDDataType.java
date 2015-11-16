package XsdParser.XSD;

/**
 * Created by magopl on 16.11.2015.
 */
public enum XSDDataType {
    BOOLEAN("boolean"),
    BYTE("byte"),
    DECIMAL("decimal"),
    INT("int"),
    INTEGER("integer"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double"),
    DATETIME("dateTime"),
    DATE("date"),
    BASE64BINARY("base64Binary"),
    ANYURI("anyURI"),
    NEGATIVEINTEGER("negativeInteger"),
    NONNEGATIVEINTEGER("nonNegativeInteger"),
    NONPOSITIVEINTEGER("nonPositiviteInteger"),
    POSITIVEINTEGER("positiveInteger"),
    SHORT("short"),
    UNSIGNEDLONG("unsignedLong"),
    UNSIGNEDINT("unsignedInt"),
    UNSIGNEDSHORT("unsignedShort"),
    UNSIGNEDBYTE("unsignedByte"),
    STRING("string"),
    NORMALIZEDSTRING("normalizedString"),
    TIME("time"),
    TOKEN("token"),
    NMTOKEN("NMTOKEN"),
    NMTOKENS("NMTOKENS"),
    NCNAME("NCName"),
    Name("Name"),
    LANGUAGE("language"),
    IDREF("IDREF"),
    IDREFS("IDREFS"),
    ID("ID"),
    HEXBINARY("hexBinary"),
    GDAY("gDay"),
    GMONTH("gMonth"),
    GMONTHDAY("gMonthDay"),
    GYEAR("gYear"),
    GYEARMONTH("gYearMonth"),
    DURATION("duration");

    private final String text;

    XSDDataType(final String text){
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public String toStringWithPrefix(Namespace namespace){
        if(!namespace.getPrefix().isPresent()){
            return toString();
        }
        return namespace.getPrefix().get() +":" + toString();
    }
}
