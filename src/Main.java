import app.InvalidInputException;
import app.NoSuchCommandException;
import app.Reader;
import app.XmlReader;
import collection.CollectionManager;
import commands.CommandsManager;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Главный класс приложения.
 */
public class Main {

    /**
     * Точка входа.
     * @param args Путь к файлу с коллекцией.
     */
    public static void main(String[] args) {
        //final String path = "flats.xml";
        final Path pathToTempFile = Paths.get("temp.xml");
        CollectionManager collection = CollectionManager.getInstance();

        File temp = new File(pathToTempFile.toAbsolutePath().toString());
        boolean hasTemp = temp.exists();


        if (hasTemp) {
            System.out.println("Обнаружены несохраненные данные с прошлой сессии. Возможно программа была завершена некорректно");
            try {
                if (CommandsManager.getInstance().confirmExecution("Желаете продолжить работу над несохраненной коллекцией? y/n")) {
                    try {
                        XmlReader.read(pathToTempFile.toAbsolutePath().toString());
                    } catch (Exception e) {
                        System.err.println("При загрузке несохраненных данных с прошлой сессии возникла ошибка." + '\n' +
                                "Будет загружена последняя сохраненная коллекция.");
                    }
                    collection.setHasUnsavedChanges(true);
                } else hasTemp = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (hasTemp == false) {
            if (args.length > 0) {
                Path pathToInitFile = Paths.get(args[0]);
                try {
                    XmlReader.read(pathToInitFile.toAbsolutePath().toString());
                } catch (ParserConfigurationException | SAXException e) {
                    System.err.println("Ошибка при считывании данных из файла");
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    System.err.println("Файл не найдет либо недостаточно прав доступа");
                } catch (IOException e) {
                    System.err.println("Ошибка ввода/вывода");
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    System.err.println("Пустая строка");
                }
            } else {
                System.err.println("Файл для инициализации коллекции не введён. Имя файла следует ввести в аргументах запуска программы.");
            }
        }

            while(true) {
            String answer = null;
            try {
                answer = Reader.request();
            } catch (IOException e) {
                System.err.println("Ошибка ввода/вывода");
                e.printStackTrace();
            }
            if (answer != null) {
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
}
