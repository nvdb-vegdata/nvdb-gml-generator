import XsdParser.Parser.SchemaDefinition;
import XsdParser.DataCatalogObjectParser.ObjXsdParser;
import XsdParser.Parser.ObjectParser;
import XsdParser.Parser.XsdBuilder;
import no.svv.nvdb.api.inn.domain.datacatalog.DataCatalog;
import no.svv.nvdb.api.inn.domain.datacatalog.FeatureType;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.*;
import restaccess.NvdbWriteApiGateway;

import java.io.*;


public class Master {
    private static NvdbWriteApiGateway apiGateway;

    public static void main(String[] args){
        String user, pw, writeApiHost, loginserverUrl;
        user = System.getProperty("username");
        pw = System.getProperty("password");
        writeApiHost = "https://www.utv.vegvesen.no";
        loginserverUrl = "https://www.utv.vegvesen.no/openam/UI/Login";

        //defining path for certificates Mega hardcoded Path
        System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\magopl\\MyWorkspace\\nvdb-gml-generator\\cacerts-custom.jks");
        try{
            apiGateway = new NvdbWriteApiGateway(loginserverUrl,user,pw,writeApiHost);
            try{
                DataCatalog dc =  DataCatalog.fromJson((InputStream) apiGateway.getDataCatalog());
                dc.featureTypes()
                        .filter(featureType -> featureType.getName().contains("Stikkrenne"))
                        .forEach(featureType -> generateXSDFromFeatureType(featureType));


            }catch (Exception e){
                System.out.println("----- Failed when fetching the datacatalog ------");
                System.out.print(e);
            }
        }catch (Exception e){
            System.out.println("----- Failed when creating gateway ------ ");
            System.out.print(e);
        }

    }

    private static void generateXSDFromFeatureType(FeatureType featureType){
        ObjectParser parser = new ObjXsdParser();
        XsdBuilder builder = new XsdBuilder(parser);
        SchemaDefinition schemaDefinition = builder.generateSchemaDefinition(featureType.attributeTypes().toArray());

        System.out.println(schemaDefinition.unLoad());
        //test(featureType);
        try {
            PrintWriter writer = new PrintWriter("Stikkrenne.xsd", "UTF-8");
            writer.println(schemaDefinition.unLoad());
            writer.close();
        } catch (Exception e) {
            System.out.println("what " + e);
        }

    }

    //region Test region
    private static void test(FeatureType featureType){
        System.out.println(featureType.getName());
        System.out.println(featureType.getDescription());
        handleAttributeTypes(featureType);
    }

    private static void handleAttributeTypes(FeatureType featureType){
        featureType.attributeTypes()
                .forEach(attributeType -> {
                    if(attributeType instanceof IntegerAttributeType || attributeType instanceof StringAttributeType || attributeType instanceof RealAttributeType){

                    }else{

                        if(attributeType instanceof ListAttributeType){
                            System.out.println("     " + attributeType.getType() + " --- " + attributeType.getClass().getSimpleName());
                            ListAttributeType la = (ListAttributeType)attributeType;
                            handleCorrectAttributeType(attributeType);
                            System.out.println("         " + la.getRequirement());
                            System.out.println("         " + la.getContentType().getType());
                            AttributeType at = la.getContentType();
                            System.out.println("         " + at.toString());

                        }





                    }


        });
    }
    private static void handleCorrectAttributeType(AttributeType attributeType){

        if ( attributeType instanceof ListAttributeType ){

            if ( attributeType.getEnumValues() != null ) {
                attributeType.getEnumValues().forEach( (k,v) -> {
                    System.out.println("                " + v.toString() + " of type: ");


                });
            }
        }
    }
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
    //endregion

}
