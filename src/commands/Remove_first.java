package commands;

import collection.CollectionManager;

import java.io.IOException;

public class Remove_first extends Command {

    public Remove_first() {
        command = "remove_first";
        description = "Удалить первый элемент из коллекции";
    }
    @Override
    public void execute(CollectionManager collection, String[] args) throws IOException {
        if (CommandsManager.getInstance().confirmExecution("Вы действительно хотите удалить первый элемент из коллекции? y/n")) {
            try {
                collection.removeByIndex(0);
                System.out.println("Первый элемент удален из коллекции");
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Коллекция пуста");
            }
        }

    }
}
