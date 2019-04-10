package Model;

import javax.naming.directory.InvalidAttributesException;

public class XmlTagException extends InvalidAttributesException {
    public XmlTagException(String tag){
        super(tag);
    }
}
