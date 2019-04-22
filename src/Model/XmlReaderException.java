package Model;

import javax.naming.directory.InvalidAttributesException;

public class XmlTagException extends RuntimeException {
    public XmlTagException(String message){
        super(message);
    }
    public XmlTagException(String message, Object values){
        super(String.format(message, values));
    }

}
