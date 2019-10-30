package just.artmmslv;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 *  Вывести имена и значения всех атрибутов для par step="1" name="ГРАЖДАНСТВО"
 */

public class AttributesFinder extends DefaultHandler {
    private List<String> result = null;

    List<String> getAttributes(){
        return result;
    }
    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) {
        if("par".equals(qName) && "1".equals(attributes.getValue("step")) && "ГРАЖДАНСТВО".equals(attributes.getValue("name"))) {
            result = new ArrayList<>();
            for(int i = 0; i < attributes.getLength(); i++){
                result.add(attributes.getQName(i) + " = " + attributes.getValue(i));
            }
        }
    }
}
