package XsdParser.XSD.Component;

/**
 * Created by magopl on 09.11.2015.
 */
public class XSDStringFormatter {

    public static String createElementName(String name){
        return toCamelCase(name);
    }

    private static String toCamelCase(String text){
        text = text.trim();
        String[] words = text.split("[^\\p{L}]");
        if(words.length == 0)
            return "";

        String camelCaseName = "";
        if(!words[0].isEmpty() && words[0].length() > 1)
            camelCaseName += words[0].substring(0,1).toLowerCase() + words[0].substring(1);
        for(int i = 1; i < words.length; i++){
            if(words[i].isEmpty())
                continue;
            camelCaseName += words[i].substring(0,1).toUpperCase() + words[i].substring(1);
        }
        return camelCaseName;
    }
}
