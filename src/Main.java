import app.InvalidInputException;
import app.NoSuchCommandException;
import app.Reader;
import app.XmlReader;
import collection.CollectionManager;
import commands.CommandsManager;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Главный класс приложения.
 */
public class Main {

    /**
     * Точка входа.
     * @param args Путь к файлу с коллекцией.
     */
    public static void main(String[] args) {
        final String path = "flats.xml";
        CollectionManager collection = CollectionManager.getInstance();

        try {
            XmlReader.read(path);
        } catch ( IOException | ParserConfigurationException | SAXException e) {
            System.err.println("Ошибка при считывании данных из файла");
            e.printStackTrace();
        }

        while(true) {
            String answer = null;
            try {
                answer = Reader.request();
            } catch (IOException e) {
                System.err.println("Ошибка ввода/вывода");
                e.printStackTrace();
            }
            try {
                CommandsManager.getInstance().executeCommand(collection, answer);
            } catch (NoSuchCommandException e) {
                System.err.println("Неизвестная команда, используйте команду help, чтобы посмотреть список всех доступных команд.");
            } catch (ParserConfigurationException | TransformerException e ) {
                System.err.println("Проблемы с парсером");
                e.printStackTrace();
            } catch (InvalidInputException e) {
                System.err.println("Некорректный ввод команды");
            } catch (IOException e) {
                System.err.println("Ошибка ввода/вывода");
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Неизвестная ошибка");
                e.printStackTrace();
            }
        }
    }
}
