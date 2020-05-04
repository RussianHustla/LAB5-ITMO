import app.InvalidInputException;
import app.NoSuchCommandException;
import app.Reader;
import app.XmlReader;
import collection.CollectionManager;
import collection.Flat;
import commands.Add;
import commands.CommandsManager;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        //ArrayList<Flat> flats = new ArrayList<>();
        CollectionManager collection = CollectionManager.getInstance();

        try {
            XmlReader.read();
        } catch ( IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        while(true) {
            String answer = null;
            try {
                answer = Reader.request();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                CommandsManager.getInstance().executeCommand(collection, answer);
            } catch (NoSuchCommandException e) {
                System.err.println("Неизвестная команда, используйте команду help, чтобы посмотреть список всех доступных команд.");
            } catch (ParserConfigurationException | TransformerException | IOException e) {
                System.err.println("Видимо виноват парсер??");
                e.printStackTrace();
            } catch (InvalidInputException e) {
                System.err.println("Некорректный ввод команды");
            } //catch (NumberFormatException e) {
//                System.err.println("Введено некорректное число");
//            }
        }
    }
}
