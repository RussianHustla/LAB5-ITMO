package commands;

import collection.CollectionManager;

import java.io.IOException;

public class Help extends Command {

    public Help() {
        command = "help";
        description = "Вывести справку по доступным командам";
    }

    @Override
    public void execute(CollectionManager collection, String[] args) throws IOException {
        for (Command command : CommandsManager.getInstance().getAllCommands()) {
            System.out.println(command.getCommand() + ": " + command.getDescription());
        }
    }
}
