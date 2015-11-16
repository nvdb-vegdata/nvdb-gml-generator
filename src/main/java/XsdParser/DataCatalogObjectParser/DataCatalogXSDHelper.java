package XsdParser.DataCatalogObjectParser;

import XsdParser.XSD.XSDDataType;
import no.svv.nvdb.api.inn.domain.datacatalog.attribute.*;

import java.util.InvalidPropertiesFormatException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Created by magopl on 16.11.2015.
 */
public class DataCatalogXSDHelper {
    public static Optional<String> getRegexFromTimeFormat(String timeFormat){
        String regex = null;
        switch (timeFormat){
            case "hhmm":
                regex = "^([01][0-9]|2[0-3])[0-5][0-9]$";
                break;
            case "hh:mm":
                regex = "^([01][0-9]|2[0-3]):[0-5][0-9]$";
                break;
            case "hh:mm:ss":
                regex = "^([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
                break;
        }
        return Optional.ofNullable(regex);
    }

    public static Optional<String> getRegexFromDateFormat(String dateFormat){
        String regex = null;

        switch (dateFormat){
            case "åååå-mm-dd":
                regex = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
                break;
            case "ååååmmdd":
                regex = "^[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$";
                break;
            case "mm-dd":
                regex = "^(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
                break;
            case "mmdd":
                regex = "^(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$";
                break;
        }
        return Optional.ofNullable(regex);
    }

    public static XSDDataType getXSDDatatype(AttributeType attributeType) {
        XSDDataType dataType = null;
        if(attributeType instanceof StringAttributeType){
            dataType = XSDDataType.STRING;
        }  else if(attributeType instanceof IntegerAttributeType){
            dataType = XSDDataType.INTEGER;
        } else if(attributeType instanceof RealAttributeType){
            dataType = XSDDataType.DECIMAL;
        } else if(attributeType instanceof TimeAttributeType){
            dataType = XSDDataType.STRING;
        } else if(attributeType instanceof DateAttributeType){
            dataType = XSDDataType.STRING;
        } else if(attributeType instanceof ShortDateAttributeType){
            dataType = XSDDataType.STRING;
        } else if(attributeType instanceof BoolAttributeType) {
            dataType = XSDDataType.BOOLEAN;
        }

        if(dataType == null){
            try {
                throw new InvalidPropertiesFormatException("No datatype registered in XSDHelper for class: " + attributeType.getClass().getSimpleName());
            } catch (InvalidPropertiesFormatException e) {
                e.printStackTrace();
            }
        }
        return dataType;

    }
    public static Optional<String> getGmlTypeFromSpatial(SpatialType type){
        String res = null;
        switch (type){
            case POINT:
                res = "PointPropertyType";
                break;
            case POLYGON:
                res = "SurfacePropertyType";
                break;
            case LINE:
                res = "CurvePropertyType";
                break;

        }
        return Optional.ofNullable(res);

    }
}
