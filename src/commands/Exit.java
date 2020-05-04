package commands;

import collection.CollectionManager;

import java.io.IOException;

public class Exit extends Command {

    public Exit() {
        command = "exit";
        description = "Завершить программу (без сохранения в файл)";
    }

    public String getCommand() {
        return command;
    }

    @Override
    public void execute(CollectionManager collection, String[] args) throws IOException {
        if (CommandsManager.getInstance().confirmExecution("Вы действительно хотите завершить программу? Все несохраненные данные будут утеряны y/n")) {
            System.exit(0);
        }
    }
}
