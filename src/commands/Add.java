package commands;

import app.Reader;
import collection.Flat;
import collection.CollectionManager;
import java.io.IOException;

public class Add extends Command {

    public Add() {
        command = "add";
        description = "Добавить новый элемент в коллекцию";
    }

    @Override
    public void execute(CollectionManager collection, String[] args) throws IOException {
        Flat flat = Reader.requestForFlat();
        collection.add(flat);
        System.out.println(collection.getLast());
        System.out.println("Новый элемент добавлен в коллекцию");
    }

    @Override
    public String getCommand() {
        return command;
    }
}
