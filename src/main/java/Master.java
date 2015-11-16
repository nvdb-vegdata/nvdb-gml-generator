import XsdParser.Parser.SchemaDefinition;
import XsdParser.DataCatalogObjectParser.ObjXsdParser;
import XsdParser.Parser.ObjectParser;
import XsdParser.Parser.XsdBuilder;
import no.svv.nvdb.api.inn.domain.datacatalog.DataCatalog;
import no.svv.nvdb.api.inn.domain.datacatalog.FeatureType;
import no.svv.nvdb.api.inn.domain.datacatalog.association.AssociationType;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.*;
import restaccess.NvdbWriteApiGateway;

import java.io.*;
import java.util.Date;


public class Master {
    private static NvdbWriteApiGateway apiGateway;

    public static void main(String[] args){
        String user, pw, writeApiHost, loginserverUrl;
        user = System.getProperty("username");
        pw = System.getProperty("password");
        writeApiHost = "https://www.utv.vegvesen.no";
        loginserverUrl = "https://www.utv.vegvesen.no/openam/UI/Login";

        //defining path for certificates
        System.setProperty("javax.net.ssl.trustStore", "cacerts-custom.jks");
        try{
            apiGateway = new NvdbWriteApiGateway(loginserverUrl,user,pw,writeApiHost);
            try{
                DataCatalog dc =  DataCatalog.fromJson((InputStream) apiGateway.getDataCatalog());
                dc.featureTypes()
                        .filter(featureType -> featureType.getName().equals("Skred"))
                        .forEach(featureType -> generateXSDFromFeatureType(featureType));

            }catch (Exception e){
                System.out.println("----- Failed when fetching the datacatalog ------");
                e.printStackTrace();
            }
        }catch (Exception e){
            System.out.println("----- Failed when creating gateway ------ ");
            System.out.print(e);
        }

    }

    private static void generateXSDFromFeatureType(FeatureType featureType){
        if(featureType == null)return;
        ObjectParser parser = new ObjXsdParser();
        XsdBuilder builder = new XsdBuilder(parser);
        SchemaDefinition schemaDefinition = builder.generateSchemaDefinition(featureType.attributeTypes().toArray());
        schemaDefinition.clean();
        System.out.println(schemaDefinition.unLoad());
        printToFile(schemaDefinition.unLoad());
        //test(featureType);

    }

    private static void printToFile(String schema){
        try {
            PrintWriter writer = new PrintWriter("Skred.xsd", "UTF-8");
            writer.println(schema);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error when printing schema to file " + e);
        }
    }

    //region Test region
    private static void test(FeatureType featureType){
        //System.out.println("" + featureType.getName() + "");
        handleAttributeTypes(featureType);
    }

    private static void handleAttributeTypes(FeatureType featureType){
        featureType.attributeTypes()
                .forEach(attributeType -> {
                    /*
                    System.out.println("ID: " + attributeType.getId());
                    System.out.println("Name: " + attributeType.getName());
                    System.out.println("Desc: " + attributeType.getDescription());
                    System.out.println("Type: " + attributeType.getType());
                    */
                    if(attributeType instanceof IntegerAttributeType || attributeType instanceof RealAttributeType
                            || attributeType instanceof StringAttributeType || attributeType instanceof BoolAttributeType
                            || attributeType instanceof DateAttributeType || attributeType instanceof ShortDateAttributeType
                            || attributeType instanceof TimeAttributeType || attributeType == null
                            || attributeType instanceof ListAttributeType
                            || attributeType instanceof AssociationType || attributeType instanceof LocationalAttributeType){

                    }else if(attributeType instanceof SpatialAttributeType){
                        SpatialAttributeType sp = (SpatialAttributeType)attributeType;
                        //System.out.println(sp.getSpatialType());
                        if(sp.getSpatialType() == SpatialType.POLYGON) {
                            System.out.println(" - - - " + featureType.getName());
                            System.out.println("Name: " + attributeType.getName());
                            System.out.println("Name: " + attributeType.getId());
                            System.out.println("Desc: " + attributeType.getDescription());
                            System.out.println("Type: " + attributeType.getType());
                        }


                    }


        });
    }
    private static void handleCorrectAttributeType(AttributeType attributeType){

        if ( attributeType instanceof StringAttributeType ){
            StringAttributeType st = (StringAttributeType)attributeType;
            if ( st.getEnumValues() != null ) {
                System.out.println(st.getName() + ", -- enum values");
                st.getEnumValues().forEach( (k,v) -> {
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
