package just.artmmslv;


import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Решение учебной задачи. Сделано Масловым А.М. 23-24.10.2019 г.
 *
 * Условие:
 * Необходимо сформировать коллекцию, содержащую все виды документов в отсортированном
 * порядке.
 * Вывести имена и значения всех атрибутов для par step="1" name="ГРАЖДАНСТВО"
 * Задача со звездочкой создать в базе таблицу справочник со значениями из первой части
 *
 * Источник лежит в file.xml
 */

public class Main {
    //Использовал материал https://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html
    public static void main(String[] args) {
        if(args.length == 0){
            findDocumentsInXML(Paths.get("file.xml"));
        } else {
            for(String arg : args){
                findDocumentsInXML(Paths.get(arg));
            }
        }

    }

    private static List<String> findDocumentsInXML(Path source){
        System.out.println("Processing " + source.toString());
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            DocumentTypeFinder finder = new DocumentTypeFinder();
            xmlReader.setContentHandler(finder);
            xmlReader.parse(source.toString());
            return finder.getDocuments();
        }catch (ParserConfigurationException | SAXException | IOException e ){
            throw new RuntimeException(e);
        }
    }
}