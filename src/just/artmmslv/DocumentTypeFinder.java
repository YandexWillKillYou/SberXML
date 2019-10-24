package just.artmmslv;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для поиска вхождений <par_list></par_list>
 * внутри тега <par name="ВИД_ДОК"></par>
 * Метод getDocuments возвращает список документов и null, если такой не найден.
 */

public class DocumentTypeFinder extends DefaultHandler {
    private List<String> result = null;
    private boolean isInListOfDocuments;

    List<String> getDocuments() {
        return result;
    }

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) {
        if (qName.equals("par") && attributes.getValue("name").equals("ВИД_ДОК")) {
            isInListOfDocuments = true;
            result = new ArrayList<>();
        }
        if (isInListOfDocuments && qName.equals("par_list")) {
            result.add(attributes.getValue("value"));
        }
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) {
        if (qName.equals("par") && isInListOfDocuments) {
            isInListOfDocuments = false;
        }
    }
}
