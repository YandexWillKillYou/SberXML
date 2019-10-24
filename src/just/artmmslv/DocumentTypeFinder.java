package just.artmmslv;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
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

    public List<String> getDocuments(){
        return result;
    }

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {
        System.out.println("startElement ");
        System.out.println("uri " + uri);
        System.out.println("localName " + localName);
        System.out.println("qName " + qName);
        System.out.println();
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {
        System.out.println("endElement ");
        System.out.println("uri " + uri);
        System.out.println("localName " + localName);
        System.out.println("qName " + qName);
        System.out.println();
    }
}
