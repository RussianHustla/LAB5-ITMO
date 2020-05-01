package commands;

import collection.CollectionManager;

import java.io.IOException;

public class Clear extends Command {

    public Clear() {
        command = "clear";
        description = "Отчистить коллекцию";
    }
    @Override
    public void execute(CollectionManager collection, String[] args) throws IOException {
        if (CommandsManager.getInstance().confirmExecution("Коллекция будет полностью очищена. Продолжить? y/n")) {
            int count = collection.size();
            collection.clear();
            System.out.println("Коллекция успешно очищенна! Элементов удалено: " + count);
        }

    }
}
