package commands;

import app.InvalidInputException;
import app.Reader;
import collection.CollectionManager;
import collection.Flat;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Update extends Command {

    public Update() {
        command = "update";
        description = "Обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public void execute(CollectionManager collection, String[] args) throws IOException, ParserConfigurationException {
        if (args.length < 1) {
            throw new InvalidInputException("У вызываемой команды отсутствует аргумент");
        }
        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("У вызываемой команды некорректный аргумент (требуется число)");
        }

        Flat flat = Reader.requestForFlat();
        flat.setId(id);

        if (CommandsManager.getInstance().confirmExecution("Элемент коллекции будет перезаписан, старая версия будет утрачена. Продолжить? y/n")) {
            collection.removeById(id);
            collection.add(flat);
            System.out.println("Элемент с id = " + id + " обновлен");
            collection.HasUnsavedChanges();
        }
    }
}
