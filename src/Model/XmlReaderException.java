package Model;

import javax.naming.directory.InvalidAttributesException;
import javax.xml.parsers.ParserConfigurationException;

public class XmlReaderException extends ParserConfigurationException {
    public XmlReaderException(String message){
        super(message);
    }
    public XmlReaderException(String message, Object values){
        super(String.format(message, values));
    }
}
