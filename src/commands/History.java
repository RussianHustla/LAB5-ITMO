package commands;

import collection.CollectionManager;

public class History extends Command {

    public History() {
        command = "history";
        description = "Вывести последние 13 команд (без их аргументов)";
    }

    @Override
    public void execute(CollectionManager collection, String[] args) {
        for (Command command : CommandsManager.getInstance().getHistory(13)) {
            System.out.println(command.getCommand());
        }
    }
}
