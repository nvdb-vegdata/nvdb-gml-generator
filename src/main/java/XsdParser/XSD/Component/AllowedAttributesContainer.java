package XsdParser.XSD.Component;

import XsdParser.XSD.XSDTagAttribute;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by magopl on 28.09.2015.
 */
public class AllowedAttributesContainer {
    private Map<XSDTagAttribute, XSDTagAttribute> allowedAttributes;

    public AllowedAttributesContainer() {
        this.allowedAttributes = new HashMap<>();
    }
    public void addLegalAttribute(XSDTagAttribute tagAttribute){
        allowedAttributes.put(tagAttribute, tagAttribute);
    }

    public Map<XSDTagAttribute, XSDTagAttribute> getAllowedAttributes() {
        return allowedAttributes;
    }

    public boolean contains(XSDTagAttribute tagAttribute){
        return allowedAttributes.containsKey(tagAttribute);
    }

    public String getLegalAttributesString(){
        if(allowedAttributes.isEmpty()){
            return "Attributes are not allowed for this class!";
        }
        String[] res = {""};
        res[0] += "{";
        allowedAttributes.forEach((k, v) -> res[0] += v + ", ");
        res[0] = res[0].substring(0, res[0].length() - 2);
        res[0] += "}";
        return res[0];
    }
}
