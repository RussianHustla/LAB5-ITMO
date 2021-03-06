package commands;

import app.Reader;
import collection.Flat;
import collection.CollectionManager;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Add extends Command {

    public Add() {
        command = "add";
        description = "Добавить новый элемент в коллекцию";
    }

    @Override
    public void execute(CollectionManager collection, String[] args) throws IOException, ParserConfigurationException {
        Flat flat = Reader.requestForFlat();
        collection.add(flat);
        System.out.println(collection.getLast());
        System.out.println("Новый элемент добавлен в коллекцию");
        collection.HasUnsavedChanges();
    }

    @Override
    public String getCommand() {
        return command;
    }
}
