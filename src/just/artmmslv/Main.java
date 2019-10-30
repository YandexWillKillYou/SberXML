package just.artmmslv;


import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

/**
 * Решение учебной задачи. Сделано Масловым А.М. 23-30.10.2019 г.
 * <p>
 * Условие:
 * Необходимо сформировать коллекцию, содержащую все виды документов в отсортированном
 * порядке.
 * Вывести имена и значения всех атрибутов для par step="1" name="ГРАЖДАНСТВО"
 * Задача со звездочкой создать в базе таблицу справочник со значениями из первой части
 * <p>
 * Источник лежит в file.xml
 */

public class Main {
    //Использовал материалы:
    //https://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html
    //https://www.tutorialspoint.com/jdbc/jdbc-create-database.htm
    public static void main(String[] args) {
        Path path;
        if (args.length == 0) {
            path = Paths.get("file.xml");
        } else {
            path = Paths.get(args[0]);
        }
        List<String> docs = findDocumentsInXML(path);
        List<String> attrs = findAttributesInXML(path);
        printList(docs);
        System.out.println();
        printList(attrs);
        System.out.println();
        createDatabaseWithData(docs);
    }

    private static List<String> findDocumentsInXML(Path source) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            DocumentTypeFinder finder = new DocumentTypeFinder();
            xmlReader.setContentHandler(finder);
            xmlReader.parse(source.toString());
            return finder.getDocuments();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> findAttributesInXML(Path source) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            AttributesFinder finder = new AttributesFinder();
            xmlReader.setContentHandler(finder);
            xmlReader.parse(source.toString());
            return finder.getAttributes();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Создает БД mysql DATABASE_NAME, в ней таблицу TABLE_NAME с одним атрибутом Name
     * В нее складывает строки из @param data
     *
     * Для корректной работы с БД должна быть установлена и запущена база mysql
     * https://www.digitalocean.com/community/tutorials/mysql-ubuntu-18-04-ru
     *
     * Для работы с кириллическим содержимым @param data нужно изменить конфигурацию /etc/mysql/my.cnf
     * образец лежит в корне проекта
     * https://gahcep.github.io/blog/2013/01/05/mysql-utf8/
     */
    private static void createDatabaseWithData(List<String> data) {
        try {
            final String USER = "root";
            final String PASS = "password";
            final String DATABASE_NAME = "DOCUMENTS";
            final String TABLE_NAME = "DOCUMENTS";
            final String DB_URL = "jdbc:mysql://localhost/";

            Connection connection;
            Statement statement;

            //Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            String createDatabaseQuery = "CREATE DATABASE " + DATABASE_NAME;
            statement.executeUpdate(createDatabaseQuery);

            String useDatabaseQuery = "USE " + DATABASE_NAME;
            statement.executeUpdate(useDatabaseQuery);

            String createTableQuery = "CREATE TABLE " + TABLE_NAME + "(Name varchar(255));";
            statement.executeUpdate(createTableQuery);

            for (String doc : data) {
                String insertQuery = "INSERT INTO " + TABLE_NAME + " VALUES ('" + doc + "');";
                statement.executeUpdate(insertQuery);
            }

            String checkResultQuery = "SELECT * FROM " + TABLE_NAME + ";";
            ResultSet resultSet = statement.executeQuery(checkResultQuery);
            while (resultSet.next()){
                System.out.println(resultSet.getString("Name"));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> void printList(List<T> list) {
        for (T t : list) {
            System.out.println(t.toString());
        }
    }
}
