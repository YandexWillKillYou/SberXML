package just.artmmslv;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 *  Вывести имена и значения всех атрибутов для par step="1" name="ГРАЖДАНСТВО"
 */

public class AttributesPrinter extends DefaultHandler {
    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) {
        if("par".equals(qName) && "1".equals(attributes.getValue("step")) && "ГРАЖДАНСТВО".equals(attributes.getValue("name"))) {
            for(int i = 0; i < attributes.getLength(); i++){
                System.out.println(attributes.getQName(i) + " = " + attributes.getValue(i));
            }
        }
    }
}
