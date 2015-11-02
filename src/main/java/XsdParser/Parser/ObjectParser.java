package XsdParser.Parser;

import XsdParser.XSD.Component.Schema;
import XsdParser.XSD.Component.XSDComponent;

import java.util.Optional;

/**
 * Created by magopl on 28.09.2015.
 */
public interface ObjectParser {

    public Schema createSchemaTag();

    public Optional<XSDComponent> translate(Object object);
}
