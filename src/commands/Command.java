package commands;

import collection.CollectionManager;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public abstract class Command {
    protected String command;
    protected String description;

    public abstract void execute(CollectionManager collection, String[] args) throws IOException, ParserConfigurationException, TransformerException;

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }
}
